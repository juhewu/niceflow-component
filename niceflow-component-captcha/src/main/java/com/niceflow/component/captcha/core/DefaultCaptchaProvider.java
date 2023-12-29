package com.niceflow.component.captcha.core;

import com.niceflow.component.captcha.config.CaptchaProperties;
import com.niceflow.component.captcha.exception.*;
import com.niceflow.component.captcha.utils.VariableUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * 默认验证码提供值
 * <p>
 * 1. 校验是否可以生成验证码
 * 2. 生成验证码
 * 3. 存储验证码
 * 4. 发送验证码
 * 5. 返回验证码
 *
 * @author duanjw
 * @date 2023/6/13
 */
@Slf4j
@AllArgsConstructor
public class DefaultCaptchaProvider implements CaptchaProvider {

    private CaptchaPreprocessor captchaPreprocessor;
    private final CaptchaGenerator captchaGenerator;
    private final CaptchaRepository captchaRepository;
    private CaptchaSender captchaSender;

    private final CaptchaProperties captchaProperties;

    private CaptchaFetchSuccessHandler captchaFetchSuccessHandler = new CaptchaFetchSuccessResponseJsonHandler();
    private CaptchaVerifyCompleteHandler captchaVerifyCompleteHandler = new CaptchaVerifyCompleteResponseJsonHandler();
    private CaptchaVerifyFailHandler captchaVerifyFailHandler = new CaptchaVerifyFailResponseJsonHandler();

    public DefaultCaptchaProvider(CaptchaGenerator captchaGenerator, CaptchaRepository captchaRepository, CaptchaProperties captchaProperties) {
        this.captchaGenerator = captchaGenerator;
        this.captchaRepository = captchaRepository;
        this.captchaProperties = captchaProperties;
    }

    public DefaultCaptchaProvider(CaptchaGenerator captchaGenerator, CaptchaRepository captchaRepository, CaptchaSender captchaSender, CaptchaProperties captchaProperties) {
        this.captchaGenerator = captchaGenerator;
        this.captchaRepository = captchaRepository;
        this.captchaSender = captchaSender;
        this.captchaProperties = captchaProperties;
    }

    /**
     * 获取验证码
     *
     * @param request  request
     * @param response response
     */
    @Override
    public void fetch(ServletRequest request, ServletResponse response) {
        try {
            if (Objects.nonNull(this.captchaPreprocessor) && !this.captchaPreprocessor.check()) {
                throw new RuntimeException("不能发送验证码");
            }

            String moduleId = obtainModuleId(request);
            CaptchaProperties.CaptchaConfig captchaConfig = Optional.ofNullable(moduleId).map(item -> captchaProperties.getCaptcha().get(item)).orElseThrow();
            Generator generatorConfig = captchaConfig.getGenerator();
            // 获取验证码 key
            String key = this.captchaGenerator.getKey(generatorConfig, request);

            // 校验验证码是否失效
            if (Objects.nonNull(this.captchaRepository.get(key))) {
                throw new CaptchaNotExpired();
            }

            // 生成验证码
            Captcha captcha = this.captchaGenerator.generate(generatorConfig);
            captcha.setKey(key);

            // 保存验证码
            this.captchaRepository.save(key, captcha.getSecret());

            // 发送验证码
            Sender sender = captchaConfig.getSender();

            String senderType = VariableUtil.replace(sender.getType(), request);
            String receiver = VariableUtil.replace(sender.getReceiver(), request);
            sender.setType(senderType);
            sender.setCaptcha(captcha.getSecret());
            sender.setReceiver(receiver);

            Optional.ofNullable(this.captchaSender).ifPresent(item -> item.send(sender));

            this.captchaFetchSuccessHandler.onSuccess(request, response, captcha);
        } catch (CaptchaException e) {
            log.error("验证码获取失败", e);
        }
    }

    @Override
    public boolean verify(String moduleId, ServletRequest request, ServletResponse response) {
        try {
            CaptchaProperties.CaptchaConfig captchaConfig = Optional.ofNullable(moduleId).map(item -> captchaProperties.getCaptcha().get(item)).orElseThrow(CaptchaSettingException::new);

            Verify verify = captchaConfig.getVerify();

            String captchaKey = obtainCaptchaKey(request);
            String captchaValue = obtainCaptchaValue(request);

            String secret = captchaRepository.get(captchaKey);

            if (Objects.isNull(secret)) {
                throw new CaptchaNotFoundException();
            }
            boolean verified = this.captchaGenerator.verify(captchaConfig.getGenerator().getType(), captchaValue, secret);

            if (!verified) {
                throw new CaptchaMismatchException();
            }

            // 删除验证码 todo 滑块验证码二次验证
            captchaRepository.remove(captchaKey);

            this.captchaVerifyCompleteHandler.onComplete(request, response, true);
            return true;
        } catch (CaptchaSettingException e) {
            log.error("验证码配置未找到", e);
            this.captchaVerifyFailHandler.verifyFail(request, response, e);
        } catch (CaptchaException e) {
            this.captchaVerifyFailHandler.verifyFail(request, response, e);
        }
        return false;
    }


    public void setCaptchaPreprocessor(CaptchaPreprocessor captchaPreprocessor) {
        this.captchaPreprocessor = captchaPreprocessor;
    }

    public void setCaptchaSender(CaptchaSender captchaSender) {
        this.captchaSender = captchaSender;
    }

    public void setCaptchaFetchSuccessHandler(CaptchaFetchSuccessHandler captchaFetchSuccessHandler) {
        this.captchaFetchSuccessHandler = captchaFetchSuccessHandler;
    }

    public void setCaptchaVerifyCompleteHandler(CaptchaVerifyCompleteHandler captchaVerifyCompleteHandler) {
        this.captchaVerifyCompleteHandler = captchaVerifyCompleteHandler;
    }

    protected String obtainCaptchaValue(ServletRequest request) {
        return request.getParameter("captchaValue");
    }

    protected String obtainCaptchaKey(ServletRequest request) {
        return request.getParameter("captchaKey");
    }

    protected String obtainModuleId(ServletRequest request) {
        return request.getParameter("moduleId");
    }
}
