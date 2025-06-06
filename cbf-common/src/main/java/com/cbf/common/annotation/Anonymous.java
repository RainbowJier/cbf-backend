package com.cbf.common.annotation;

import java.lang.annotation.*;

/**
 * 匿名访问不鉴权注解
 *
 * @author Frank
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous
{
}
