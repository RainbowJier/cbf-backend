package com.cbf.quartz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbf.common.annotation.Excel;
import com.cbf.common.annotation.Excel.ColumnType;
import com.cbf.common.constant.ScheduleConstants;
import com.cbf.common.core.domain.BaseEntity;
import com.cbf.common.utils.StringUtils;
import com.cbf.quartz.util.CronUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 定时任务调度表 sys_job
 *
 * @author Frank
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="sys_job")
public class SysJob extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @Excel(name = "任务序号", cellType = ColumnType.NUMERIC)
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;

    /**
     * 任务名称
     */
    @Getter
    @NotBlank(message = "任务名称不能为空")
    @Size(min = 0, max = 64, message = "任务名称不能超过64个字符")
    @Excel(name = "任务名称")
    @TableId(value = "job_name")
    private String jobName;

    /**
     * 任务组名
     */
    @Excel(name = "任务组名")
    @TableId(value = "job_group")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @NotBlank(message = "调用目标字符串不能为空")
    @Size(min = 0, max = 500, message = "调用目标字符串长度不能超过500个字符")
    @Excel(name = "调用目标字符串")
    @TableField(value = "invoke_target")
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @NotBlank(message = "Cron执行表达式不能为空")
    @Size(min = 0, max = 255, message = "Cron执行表达式不能超过255个字符")
    @Excel(name = "执行表达式 ")
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * cron计划策略
     */
    @Excel(name = "计划策略 ", readConverterExp = "0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行")
    @TableField(value = "misfire_policy")
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    @Excel(name = "并发执行", readConverterExp = "0=允许,1=禁止")
    @TableField(value = "concurrent")
    private String concurrent;

    /**
     * 任务状态（0正常 1暂停）
     */
    @Excel(name = "任务状态", readConverterExp = "0=正常,1=暂停")
    @TableField(value = "status")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getNextValidTime() {
        if (StringUtils.isNotEmpty(cronExpression)) {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }
}
