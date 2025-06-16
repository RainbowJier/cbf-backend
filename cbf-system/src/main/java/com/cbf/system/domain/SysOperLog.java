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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 操作日志记录表 oper_log
 *
 * @author Frank
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oper_log")
public class SysOperLog extends BaseEntity {

    /**
     * 日志主键
     */
    @Excel(name = "操作序号", cellType = ColumnType.NUMERIC)
    @TableId(value = "oper_id")
    private Long operId;

    /**
     * 操作模块
     */
    @Excel(name = "操作模块")
    @TableField(value = "title")
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    @TableField(value = "business_type")
    private Integer businessType;

    /**
     * 业务类型数组
     */
    private Integer[] businessTypes;

    /**
     * 请求方法
     */
    @Excel(name = "请求方法")
    @TableField(value = "method")
    private String method;

    /**
     * 请求方式
     */
    @Excel(name = "请求方式")
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    @TableField(value = "operator_type")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @Excel(name = "操作人员")
    @TableField(value = "oper_name")
    private String operName;

    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    @TableField(value = "dept_name")
    private String deptName;

    /**
     * 请求url
     */
    @Excel(name = "请求地址")
    @TableField(value = "oper_url")
    private String operUrl;

    /**
     * 操作地址
     */
    @Excel(name = "操作地址")
    @TableField(value = "oper_ip")
    private String operIp;

    /**
     * 操作地点
     */
    @Excel(name = "操作地点")
    @TableField(value = "oper_location")
    private String operLocation;

    /**
     * 请求参数
     */
    @Excel(name = "请求参数")
    @TableField(value = "oper_param")
    private String operParam;

    /**
     * 返回参数
     */
    @Excel(name = "返回参数")
    @TableField(value = "json_result")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    @TableField(value = "status")
    private Integer status;

    /**
     * 错误消息
     */
    @Excel(name = "错误消息")
    @TableField(value = "error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "oper_time")
    private Date operTime;

    /**
     * 消耗时间
     */
    @Excel(name = "消耗时间", suffix = "毫秒")
    @TableField(value = "cost_time")
    private Long costTime;
}
