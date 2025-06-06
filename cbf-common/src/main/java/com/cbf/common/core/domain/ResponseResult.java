package com.cbf.common.core.domain;


import com.cbf.common.constant.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : Liuqijie
 * @Date: 2025/6/6 15:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseResult<T> implements Serializable {
    /**
     * 状态码
     */
    private int code;

    /**
     * 返回内容
     */
    private String msg;

    /**
     * 数据对象
     */
    private T data;

    // ====================== 静态构建方法 ======================

    /**
     * 返回成功消息
     */
    public static <T> ResponseResult<T> success() {
        return success("操作成功", null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> ResponseResult<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> ResponseResult<T> success(String msg, T data) {
        return new ResponseResult<>(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     */
    public static <T> ResponseResult<T> warn(String msg) {
        return warn(msg, null);
    }

    public static <T> ResponseResult<T> warn(String msg, T data) {
        return new ResponseResult<>(HttpStatus.WARN, msg, data);
    }

    /**
     * 返回错误消息
     */
    public static <T> ResponseResult<T> error() {
        return error("操作失败", null);
    }

    public static <T> ResponseResult<T> error(String msg) {
        return error(msg, null);
    }

    public static <T> ResponseResult<T> error(String msg, T data) {
        return new ResponseResult<>(HttpStatus.ERROR, msg, data);
    }

    public static <T> ResponseResult<T> error(int code, String msg) {
        return new ResponseResult<>(code, msg, null);
    }
}