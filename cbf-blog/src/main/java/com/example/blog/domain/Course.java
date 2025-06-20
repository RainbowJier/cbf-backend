package com.example.blog.domain;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbf.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    @TableId(value = "id")
    private Long id;

    /**
     * 课程编码
     */
    @ExcelProperty("课程编码")
    private String code;

    /**
     * 课程学科
     */
    @ExcelProperty("课程学科")
    private String subject;

    /**
     * 课程名称
     */
    @ExcelProperty("课程名称")
    private String name;

    /**
     * 价格（元）
     */
    @ExcelProperty("价格")
    private BigDecimal price;

    /**
     * 适用人群
     */
    @ExcelProperty("适用人群")
    private String targetGroup;

    /**
     * 课程介绍
     */
    @ExcelProperty("课程介绍")
    private String description;

}
