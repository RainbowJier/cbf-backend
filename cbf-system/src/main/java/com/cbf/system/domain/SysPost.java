package com.cbf.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbf.common.annotation.Excel;
import com.cbf.common.annotation.Excel.ColumnType;
import com.cbf.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 岗位表 sys_post
 *
 * @author Frank
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value ="sys_post")
public class SysPost extends BaseEntity {
    /**
     * 岗位序号
     */
    @Excel(name = "岗位序号", cellType = ColumnType.NUMERIC)
    @TableId(value = "post_id")
    private Long postId;

    /**
     * 岗位编码
     */
    @Excel(name = "岗位编码")
    @NotBlank(message = "岗位编码不能为空")
    @Size(min = 0, max = 64, message = "岗位编码长度不能超过64个字符")
    @TableField(value = "post_code")
    private String postCode;

    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    @Size(min = 0, max = 50, message = "岗位名称长度不能超过50个字符")
    @TableField(value = "post_name")
    private String postName;

    /**
     * 岗位排序
     */
    @Excel(name = "岗位排序")
    @NotNull(message = "显示顺序不能为空")
    @TableField(value = "post_sort")
    private Integer postSort;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    @TableField(value = "status")
    private String status;

    /**
     * 用户是否存在此岗位标识 默认不存在
     */
    private boolean flag = false;
}
