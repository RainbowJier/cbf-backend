package com.cbf.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统文件表
 * @TableName SYS_FILE
 */
@TableName(value = "SYS_FILE")
@Data
public class SysFile implements Serializable {
    /**
     * 文件ID
     */
    @TableId(value = "ID")
    private String id;

    /**
     * 文件名称
     */
    @TableField(value = "NAME")
    private String name;

    /**
     * 文件类型（MIME类型）
     */
    @TableField(value = "FILE_TYPE")
    private String fileType;

    /**
     * 文件存储路径
     */
    @TableField(value = "STORAGE_PATH")
    private String storagePath;

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
        SysFile other = (SysFile) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
                && (this.getStoragePath() == null ? other.getStoragePath() == null : this.getStoragePath().equals(other.getStoragePath()))
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
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getStoragePath() == null) ? 0 : getStoragePath().hashCode());
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
        sb.append(", fileType=").append(fileType);
        sb.append(", storagePath=").append(storagePath);
        sb.append(", isValid=").append(isValid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}