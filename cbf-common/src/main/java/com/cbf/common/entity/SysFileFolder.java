package com.cbf.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 统一文件夹管理表
 * @TableName SYS_FILE_FOLDER
 */
@TableName(value = "SYS_FILE_FOLDER")
@Data
public class SysFileFolder implements Serializable {
    /**
     * 文件夹ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 文件夹名称
     */
    @TableField(value = "NAME")
    private String name;

    /**
     * 父级ID
     */
    @TableField(value = "PARENT_ID")
    private Long parentId;

    /**
     * 祖先节点
     */
    @TableField(value = "ANCESTORS")
    private String ancestors;

    /**
     * 文件夹类型：0-单个文件，1-自定义，2-行政区划
     */
    @TableField(value = "FILE_TYPE")
    private Integer fileType;

    /**
     * 本地存储路径
     */
    @TableField(value = "PATH")
    private String path;

    /**
     * 是否有效，1-有效，0-无效
     */
    @TableField(value = "IS_VALID")
    private Integer isValid;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysFileFolder other = (SysFileFolder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
                && (this.getAncestors() == null ? other.getAncestors() == null : this.getAncestors().equals(other.getAncestors()))
                && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
                && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
                && (this.getIsValid() == null ? other.getIsValid() == null : this.getIsValid().equals(other.getIsValid()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getAncestors() == null) ? 0 : getAncestors().hashCode());
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getIsValid() == null) ? 0 : getIsValid().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", parentId=").append(parentId);
        sb.append(", ancestors=").append(ancestors);
        sb.append(", fileType=").append(fileType);
        sb.append(", path=").append(path);
        sb.append(", isValid=").append(isValid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}