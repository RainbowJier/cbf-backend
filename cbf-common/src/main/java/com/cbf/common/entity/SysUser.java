package com.cbf.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbf.common.annotation.Excel;
import com.cbf.common.annotation.Excel.ColumnType;
import com.cbf.common.annotation.Excel.Type;
import com.cbf.common.annotation.Excels;
import com.cbf.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class SysUser extends BaseEntity implements Serializable {
    /**
     * 用户ID
     */
    @Excel(name = "用户序号", type = Type.EXPORT, cellType = ColumnType.NUMERIC, prompt = "用户编号")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 部门ID
     */
    @Excel(name = "部门编号", type = Type.IMPORT)
    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 用户账号
     */
    @Excel(name = "登录名称")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户名称")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @TableField(value = "user_type")
    private String userType;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    @TableField(value = "email")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码", cellType = ColumnType.TEXT)
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    @TableField(value = "sex")
    private String sex;

    /**
     * 用户头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 账号状态（0正常 1停用）
     */
    @Excel(name = "账号状态", readConverterExp = "0=正常,1=停用")
    @TableField(value = "status")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableField(value = "del_flag")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP", type = Type.EXPORT)
    @TableField(value = "login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    @TableField(value = "login_date")
    private Date loginDate;

    /**
     * 密码最后更新时间
     */
    @TableField(value = "pwd_update_date")
    private Date pwdUpdateDate;

    /**
     * 部门对象
     */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;

    /**
     * 角色对象
     */
    private List<SysRole> roles;

    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 岗位组
     */
    private Long[] postIds;

    /**
     * 角色ID
     */
    private Long roleId;

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }
}
