package com.cbf.framework.web.service;

import com.cbf.common.constant.CacheConstants;
import com.cbf.common.constant.Constants;
import com.cbf.common.constant.UserConstants;
import com.cbf.common.core.domain.entity.SysUser;
import com.cbf.common.core.domain.model.LoginUser;
import com.cbf.common.core.redis.RedisCache;
import com.cbf.common.enums.UserStatus;
import com.cbf.common.exception.ServiceException;
import com.cbf.common.exception.user.*;
import com.cbf.common.utils.DateUtils;
import com.cbf.common.utils.MessageUtils;
import com.cbf.common.utils.StringUtils;
import com.cbf.common.utils.ip.IpUtils;
import com.cbf.framework.manager.AsyncManager;
import com.cbf.framework.manager.factory.AsyncFactory;
import com.cbf.framework.security.context.AuthenticationContextHolder;
import com.cbf.system.mapper.SysUserMapper;
import com.cbf.system.service.ISysConfigService;
import com.cbf.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 登录校验方法
 *
 * @author Frank
 */

@Slf4j
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    private SysPasswordService passwordService;

    /**
     * 登录验证
     *
     * @param userName 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String userName, String password, String code, String uuid) {
        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(userName, code, uuid);
        }

        // 登录参数前置校验
        loginPreCheck(userName, password);

        // 校验账号合法性
        SysUser sysUser = userService.selectUserByUserName(userName);
        if (StringUtils.isNull(sysUser)) {
            log.info("登录用户：{} 不存在.", userName);
            throw new ServiceException(MessageUtils.message("user.not.exists"));
        } else if (UserStatus.DELETED.getCode().equals(sysUser.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", userName);
            throw new ServiceException(MessageUtils.message("user.password.delete"));
        } else if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
            log.info("登录用户：{} 已被停用.", userName);
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }

        // 账号登录
        passwordService.validate(sysUser,password);
        LoginUser loginUser = createLoginUser(sysUser);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new UserPasswordNotMatchException();
        }

        // 日志记录
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(sysUser.getUserId());

        // 生成token
        return tokenService.createToken(loginUser);
    }

    public LoginUser createLoginUser(SysUser sysUser) {
        Set<String> menuPermission = permissionService.getMenuPermission(sysUser);
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(sysUser)
                .setUserId(sysUser.getUserId())
                .setDeptId(sysUser.getDeptId())
                .setPermissions(menuPermission);
        return loginUser;
    }


    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            if (captcha == null) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            redisCache.deleteObject(verifyKey);
            if (!code.equalsIgnoreCase(captcha)) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
    }

    /**
     * 登录前置校验
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // IP黑名单校验
        String blackStr = configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("login.blocked")));
            throw new BlackListException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
