package com.cbf.common.annotation;

import com.cbf.common.enums.BusinessType;
import com.cbf.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author Frank
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * module
     */
    String title() default "";

    /**
     * function
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * operator type.
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * store request parameters or not.
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     * store response data or not.
     */
    boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     * exclude specified request parameters
     */
    String[] excludeParamNames() default {};
}
