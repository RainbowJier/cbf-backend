package com.cbf.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cbf.common.annotation.Excel;
import com.cbf.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 课程信息对象 course
 *
 * @author Frank
 * @date 2025-06-18
 */

@Data
@AllArgsConstructor
@TableName("course")
public class Course extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    private Long id;

    /**
     * 课程编码
     */
    @Excel(name = "课程编码")
    private String code;

    /**
     * 课程学科
     */
    @Excel(name = "课程学科")
    private String subject;

    /**
     * 课程名称
     */
    @Excel(name = "课程名称")
    private String name;

    /**
     * 价格（元）
     */
    @Excel(name = "价格", readConverterExp = "元=")
    private BigDecimal price;

    /**
     * 适用人群
     */
    @Excel(name = "适用人群")
    private String targetGroup;

    /**
     * 课程介绍
     */
    @Excel(name = "课程介绍")
    private String description;

}
