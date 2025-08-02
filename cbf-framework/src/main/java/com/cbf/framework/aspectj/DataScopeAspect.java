package com.cbf.framework.aspectj;

import com.cbf.common.annotation.DataScope;
import com.cbf.common.constant.UserConstants;
import com.cbf.common.core.domain.BaseEntity;
import com.cbf.common.core.domain.model.LoginUser;
import com.cbf.common.core.text.Convert;
import com.cbf.common.entity.SysRole;
import com.cbf.common.entity.SysUser;
import com.cbf.common.utils.SecurityUtils;
import com.cbf.common.utils.StringUtils;
import com.cbf.framework.security.context.PermissionContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据过滤处理
 *
 * @author Frank
 */
@Aspect
@Component
public class DataScopeAspect {
    /**
     * All data scope.
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * Custom data scope.
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * Department data scope.
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     * current user.
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScope controllerDataScope) {
        // clean filter condition to avoid the sql injection.
        clearDataScope(point);
        // set data scope filter condition.
        handleDataScope(point, controllerDataScope);
    }

    /**
     * Clean filter condition to avoid the sql injection.
     * clean the value of  ${params.dataScope}
     */
    private void clearDataScope(final JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(DATA_SCOPE, "");
        }
    }

    protected void handleDataScope(final JoinPoint joinPoint, DataScope controllerDataScope) {
        // Current login user.
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();

            // Don't filter data, if admin.
            if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {
                // Get the permission characters of interface, such as "system:user:list"
                String permission = StringUtils.defaultIfEmpty(controllerDataScope.permission(), PermissionContextHolder.getContext());
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias(), permission);
            }
        }
    }

    /**
     * Data Scope Filter
     *
     * @param joinPoint  aspect point
     * @param user       user
     * @param deptAlias  alias of department table.
     * @param userAlias  alias of user table.
     * @param permission Permission characters.
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String deptAlias, String userAlias, String permission) {
        StringBuilder sqlString = new StringBuilder();
        List<String> conditions = new ArrayList<>();

        // 角色id
        List<String> scopeCustomIds = new ArrayList<>();
        user.getRoles().forEach(role -> {
            if (DATA_SCOPE_CUSTOM.equals(role.getDataScope()) && StringUtils.equals(role.getStatus(), UserConstants.ROLE_NORMAL) && StringUtils.containsAny(role.getPermissions(), Convert.toStrArray(permission))) {
                scopeCustomIds.add(Convert.toStr(role.getRoleId()));
            }
        });

        for (SysRole role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (conditions.contains(dataScope) || StringUtils.equals(role.getStatus(), UserConstants.ROLE_DISABLE)) {
                continue;
            }
            // Check the current user has permission or not, if not them skip.
            if (!StringUtils.containsAny(role.getPermissions(), Convert.toStrArray(permission))) {
                continue;
            }
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString = new StringBuilder();
                conditions.add(dataScope);
                break;
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                if (scopeCustomIds.size() > 1) {
                    // A user with multi roles.
                    sqlString.append(StringUtils.format(" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id in ({}) ) ", deptAlias, String.join(",", scopeCustomIds)));
                } else {
                    sqlString.append(StringUtils.format(" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias, role.getRoleId()));
                }
            } else if (DATA_SCOPE_DEPT.equals(dataScope)) {
                sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                sqlString.append(StringUtils.format(" OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )", deptAlias, user.getDeptId(), user.getDeptId()));
            } else if (DATA_SCOPE_SELF.equals(dataScope)) {
                if (StringUtils.isNotBlank(userAlias)) {
                    sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
                } else {
                    sqlString.append(StringUtils.format(" OR {}.dept_id = 0 ", deptAlias));
                }
            }
            conditions.add(dataScope);
        }

        // 角色都不包含传递过来的权限字符，这个时候sqlString也会为空，所以要限制一下,不查询任何数据
        if (StringUtils.isEmpty(conditions)) {
            sqlString.append(StringUtils.format(" OR {}.dept_id = 0 ", deptAlias));
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            Object params = joinPoint.getArgs()[0];
            if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
            }
        }
    }
}
