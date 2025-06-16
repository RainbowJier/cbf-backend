package com.cbf.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author Frank
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole {
    /**
     * 用户ID
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @TableId(value = "role_id")
    private Long roleId;

}
