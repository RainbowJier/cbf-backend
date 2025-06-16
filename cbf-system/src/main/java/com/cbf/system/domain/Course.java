package com.cbf.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbf.common.annotation.Excel;
import com.cbf.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 课程管理对象 course
 *
 * @author Frank
 * @date 2025-05-31
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value ="course")
public class Course extends BaseEntity {
    /**
     * 课程ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 课程编码
     */
    @Excel(name = "课程编码")
    @TableField(value = "code")
    private String code;

    /**
     * 课程学科
     */
    @Excel(name = "课程学科")
    @TableField(value = "subject")
    private String subject;

    /**
     * 课程名称
     */
    @Excel(name = "课程名称")
    @TableField(value = "name")
    private String name;

    /**
     * 价格（元）
     */
    @Excel(name = "价格", readConverterExp = "元=")
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 适用人群
     */
    @Excel(name = "适用人群")
    @TableField(value = "target_group")
    private String targetGroup;

    /**
     * 课程介绍
     */
    @Excel(name = "课程介绍")
    @TableField(value = "description")
    private String description;

}
