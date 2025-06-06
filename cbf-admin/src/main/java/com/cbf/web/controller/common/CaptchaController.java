package com.cbf.web.controller.common;

import com.cbf.common.config.CBFConfig;
import com.cbf.common.constant.CacheConstants;
import com.cbf.common.constant.Constants;
import com.cbf.common.core.domain.ResponseResult;
import com.cbf.common.core.redis.RedisCache;
import com.cbf.common.utils.sign.Base64;
import com.cbf.common.utils.uuid.IdUtils;
import com.cbf.common.vo.common.CaptchaVo;
import com.cbf.system.service.ISysConfigService;
import com.google.code.kaptcha.Producer;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author Frank
 */
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private RedisCache redisCache;

    @Resource
    private ISysConfigService configService;

    @Resource
    private CBFConfig cbfConfig;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public ResponseResult<CaptchaVo> getCode() {
        CaptchaVo captchaVo = new CaptchaVo();

        // 校验是否需要验证码
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        captchaVo.setCaptchaEnabled(captchaEnabled);
        if (!captchaEnabled) {
            return ResponseResult.success(captchaVo);
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr;
        String code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = cbfConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        // Store captcha in Redis.
        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        // Convert to output stream.
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            if (image != null) {
                ImageIO.write(image, "jpg", os);
            }
        } catch (IOException e) {
            return ResponseResult.error(e.getMessage());
        }

        captchaVo.setUuid(uuid);
        captchaVo.setImg(Base64.encode(os.toByteArray()));
        return ResponseResult.success(captchaVo);
    }
}
