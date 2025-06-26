package com.cbf.web.controller.monitor;

import com.cbf.common.annotation.Log;
import com.cbf.common.constant.CacheConstants;
import com.cbf.common.core.controller.BaseController;
import com.cbf.common.core.domain.AjaxResult;
import com.cbf.common.core.domain.model.LoginUser;
import com.cbf.common.core.page.TableDataInfo;
import com.cbf.common.core.redis.RedisCache;
import com.cbf.common.enums.BusinessType;
import com.cbf.common.utils.StringUtils;
import com.cbf.system.domain.SysUserOnline;
import com.cbf.system.service.ISysUserOnlineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Monitor users online.
 *
 * @author Frank
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {

    @Resource
    private ISysUserOnlineService userOnlineService;

    @Resource
    private RedisCache redisCache;

    /**
     * Query users list online.
     *
     * @param ipaddr   login ip(optional)
     * @param userName login userName
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName) {
        Collection<String> keys = redisCache.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");

        List<SysUserOnline> userOnlineList = new ArrayList<>();
        for (String key : keys) {
            LoginUser user = redisCache.getCacheObject(key);

            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }

        Collections.reverse(userOnlineList);

        // For users who are forcibly exited, the object will be null.
        userOnlineList.removeAll(Collections.singleton(null));

        return getDataTable(userOnlineList);
    }

    /**
     * Force users to exit.
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        return success();
    }
}
