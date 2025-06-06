package com.cbf.system.domain;

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
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
