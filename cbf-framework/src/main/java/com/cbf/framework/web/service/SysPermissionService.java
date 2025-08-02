package com.cbf.framework.web.service;

import com.cbf.common.constant.UserConstants;
import com.cbf.common.entity.SysRole;
import com.cbf.common.entity.SysUser;
import com.cbf.common.utils.StringUtils;
import com.cbf.system.service.ISysMenuService;
import com.cbf.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author Frank
 */
@Component
public class SysPermissionService {
    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(sysRoleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            List<SysRole> roles = user.getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRole role : roles) {
                    if (StringUtils.equals(role.getStatus(), UserConstants.ROLE_NORMAL) && !role.isAdmin()) {
                        Set<String> rolePerms = sysMenuService.selectMenuPermsByRoleId(role.getRoleId());
                        role.setPermissions(rolePerms);
                        perms.addAll(rolePerms);
                    }
                }
            }
            // 当前用户未分配角色
            else {
                perms.addAll(sysMenuService.selectMenuPermsByUserId(user.getUserId()));
            }
        }
        return perms;
    }
}
