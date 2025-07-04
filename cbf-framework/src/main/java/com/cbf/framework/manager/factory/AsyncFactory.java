package com.cbf.framework.manager.factory;

import com.cbf.common.constant.Constants;
import com.cbf.common.utils.LogUtils;
import com.cbf.common.utils.ServletUtils;
import com.cbf.common.utils.StringUtils;
import com.cbf.common.utils.ip.AddressUtils;
import com.cbf.common.utils.ip.IpUtils;
import com.cbf.common.utils.spring.SpringUtils;
import com.cbf.system.domain.SysLogininfor;
import com.cbf.system.domain.SysOperLog;
import com.cbf.system.service.ISysLogininforService;
import com.cbf.system.service.ISysOperLogService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * Asynchronous Factory to produce tasks.
 *
 * @author Frank
 */
@Slf4j
public class AsyncFactory {

    /**
     * TimerTask for logging
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(String username, String status, String message, Object... args) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr();
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                log.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setUserName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // 日志状态
                if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
                    logininfor.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    logininfor.setStatus(Constants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(logininfor);
            }
        };
    }

    /**
     * TimerTask for operation.
     */
    public static TimerTask recordOper(SysOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // Check the location by remote query.
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
