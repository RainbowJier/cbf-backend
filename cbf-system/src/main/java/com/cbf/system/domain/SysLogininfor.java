package com.cbf.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbf.common.annotation.Excel;
import com.cbf.common.annotation.Excel.ColumnType;
import com.cbf.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author Frank
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_logininfor")
public class SysLogininfor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "序号", cellType = ColumnType.NUMERIC)
    @TableId(value = "info_id")
    private Long infoId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    @TableField(value = "user_name")
    private String userName;

    /**
     * 登录状态 0成功 1失败
     */
    @Excel(name = "登录状态", readConverterExp = "0=成功,1=失败")
    @TableField(value = "status")
    private String status;

    /**
     * 登录IP地址
     */
    @Excel(name = "登录地址")
    @TableField(value = "ipaddr")
    private String ipaddr;

    /**
     * 登录地点
     */
    @Excel(name = "登录地点")
    @TableField(value = "login_location")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Excel(name = "浏览器")
    @TableField(value = "browser")
    private String browser;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统")
    @TableField(value = "os")
    private String os;

    /**
     * 提示消息
     */
    @Excel(name = "提示消息")
    @TableField(value = "msg")
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "login_time")
    private Date loginTime;
}
