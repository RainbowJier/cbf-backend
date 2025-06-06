package com.cbf.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户登录对象
 *
 * @author Frank
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBody implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;
}
