package com.cbf.common.vo.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : Liuqijie
 * @Date: 2025/6/6 17:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CaptchaVo {
    private boolean captchaEnabled;

    private String uuid;

    private String code;

    private String img;

}
