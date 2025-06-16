package com.cbf.framework.web.service;

import com.cbf.common.constant.CacheConstants;
import com.cbf.common.constant.Constants;
import com.cbf.common.core.domain.entity.SysUser;
import com.cbf.common.core.redis.RedisCache;
import com.cbf.common.exception.user.UserPasswordNotMatchException;
import com.cbf.common.exception.user.UserPasswordRetryLimitExceedException;
import com.cbf.common.utils.MessageUtils;
import com.cbf.common.utils.SecurityUtils;
import com.cbf.framework.manager.AsyncManager;
import com.cbf.framework.manager.factory.AsyncFactory;
import com.cbf.framework.security.context.AuthenticationContextHolder;
import com.cbf.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 登录密码方法
 *
 * @author Frank
 */
@Component
public class SysPasswordService {
    @Autowired
    private RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    /**
     * 密码错误次数记录，超过限定次数，锁定一定时间
     */
    public void validate(SysUser user,String password) {
        String userName = user.getUserName();
        Integer retryCount = redisCache.getCacheObject(getCacheKey(userName));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount) {
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if (!matches(user, password)) {
            retryCount = retryCount + 1;
            redisCache.setCacheObject(getCacheKey(userName), retryCount, lockTime, TimeUnit.MINUTES);
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(userName);
        }
    }

    public boolean matches(SysUser user, String rawPassword) {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName) {
        if (redisCache.hasKey(getCacheKey(loginName))) {
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }
}
