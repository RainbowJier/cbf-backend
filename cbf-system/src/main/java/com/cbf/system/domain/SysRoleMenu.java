package com.cbf.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色和菜单关联 sys_role_menu
 *
 * @author Frank
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu {
    /**
     * 角色ID
     */
    @TableId(value = "role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id")
    private Long menuId;
}
