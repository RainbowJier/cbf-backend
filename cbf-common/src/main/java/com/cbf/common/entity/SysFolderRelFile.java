package com.cbf.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件夹与文件关联表
 * @TableName SYS_FOLDER_REL_FILE
 */
@TableName(value = "SYS_FOLDER_REL_FILE")
@Data
public class SysFolderRelFile implements Serializable {
    /**
     * 所属文件夹ID
     */
    @TableField(value = "FOLDER_ID")
    private Long folderId;

    /**
     * 文件ID
     */
    @TableField(value = "FILE_ID")
    private String fileId;

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
        SysFolderRelFile other = (SysFolderRelFile) that;
        return (this.getFolderId() == null ? other.getFolderId() == null : this.getFolderId().equals(other.getFolderId()))
                && (this.getFileId() == null ? other.getFileId() == null : this.getFileId().equals(other.getFileId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFolderId() == null) ? 0 : getFolderId().hashCode());
        result = prime * result + ((getFileId() == null) ? 0 : getFileId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", folderId=").append(folderId);
        sb.append(", fileId=").append(fileId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}