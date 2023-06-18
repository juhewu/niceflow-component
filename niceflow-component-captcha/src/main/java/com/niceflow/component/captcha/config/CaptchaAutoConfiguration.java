package com.niceflow.component.captcha.config;

import com.niceflow.component.captcha.core.*;
import com.niceflow.component.captcha.filter.CaptchaFetchFilter;
import com.niceflow.component.captcha.filter.CaptchaVerifyFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author duanjw
 * @date 2023/4/18
 */
@Import(CaptchaProperties.class)
@Configuration
public class CaptchaAutoConfiguration {

    @Bean
    public CaptchaGenerator imageCaptchaGenerator() {
        return new ImageCaptchaGenerator();
    }

    @Bean
    public CaptchaGenerator numberCaptchaGenerator() {
        return new NumberCaptchaGenerator();
    }

    @Bean
    public CaptchaGenerator sliderCaptchaGenerator() {
        return new SliderCaptchaGenerator();
    }

    @Bean
    public CaptchaGenerator captchaGenerator(List<CaptchaGenerator> captchaGenerators) {
        return new CaptchaGeneratorManager(captchaGenerators);
    }

    @Bean
    @ConditionalOnMissingBean
    public InMemoryCaptchaRepository inMemoryCaptchaRepository() {
        return new InMemoryCaptchaRepository();
    }

    @Bean
    public CaptchaProvider captchaProvider(CaptchaGenerator captchaGenerator, CaptchaRepository captchaRepository, CaptchaProperties captchaProperties, List<CaptchaSender> captchaSenders) {
        return new DefaultCaptchaProvider(captchaGenerator, captchaRepository, new CaptchaSenderManager(captchaSenders), captchaProperties);
    }


    @Bean
    public FilterRegistrationBean<CaptchaFetchFilter> fetchFilterFilterRegistrationBean(CaptchaProvider captchaProvider) {
        CaptchaFetchFilter captchaFetchFilter = new CaptchaFetchFilter(captchaProvider);
        FilterRegistrationBean<CaptchaFetchFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(captchaFetchFilter);
        filterRegistration.addUrlPatterns("/public/captcha");
        return filterRegistration;
    }

    @Bean
    public FilterRegistrationBean<CaptchaVerifyFilter> captchaVerifyFilterFilterRegistrationBean(CaptchaProperties captchaProperties, CaptchaProvider captchaProvider) {
        Map<String, CaptchaProperties.CaptchaConfig> captcha = captchaProperties.getCaptcha();
        List<VerifyUrlAndModule> verifyUrlAndModules = new ArrayList<>();
        captcha.forEach((key, value) -> verifyUrlAndModules.add(new VerifyUrlAndModule(key, new AntPathRequestMatcher(value.getVerify().getUrl(), value.getVerify().getHttpMethod()))));
        CaptchaVerifyFilter filter = new CaptchaVerifyFilter(verifyUrlAndModules, captchaProvider);
        FilterRegistrationBean<CaptchaVerifyFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(filter);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(-2147483597);
        return filterRegistration;
    }


//    @Bean
//    public GenerateCaptchaCompleteHandler generateCaptchaCompleteHandler() {
//        return new CaptchaCompleteResponseJsonHandler();
//    }


//    @Bean
//    public CaptchaGenerator captchaGenerator(CaptchaProperties captchaProperties, CaptchaRepository captchaRepository, List<CaptchaProvider> captchaProviders) {
//        return new CaptchaGeneratorHandle(null, captchaProperties, captchaRepository, captchaProviders);
//    }

//    @Bean
//    public List<CaptchaProvider> captchaProviders(){
//        return Arrays.asList(new SmsCaptchaProvider(), new SliderCaptchaProvider());
//    }


//    @Bean
//    public VerifyCaptchaCompleteHandler verifyCaptchaCompleteHandler() {
//        return new CaptchaCompleteResponseJsonHandler();
//    }


//    @Bean
//    public CaptchaVerifier captchaVerifier(CaptchaProperties captchaProperties, CaptchaRepository captchaRepository, List<CaptchaProvider> captchaProviders) {
//        return new CaptchaVerifierHandle(captchaProperties, captchaRepository, captchaProviders);
//    }


//
//    @Bean
//    public FilterRegistrationBean<ValidCaptchaFilter> validCaptchaFilter(CaptchaVerifier captchaVerifier, VerifyCaptchaCompleteHandler verifyCaptchaCompleteHandler) {
//        ValidCaptchaFilter filter = new ValidCaptchaFilter(captchaVerifier, verifyCaptchaCompleteHandler);
//        FilterRegistrationBean<ValidCaptchaFilter> filterRegistration = new FilterRegistrationBean<>();
//        filterRegistration.setFilter(filter);
//        filterRegistration.addUrlPatterns("/public/captcha/valid");
//        return filterRegistration;
//    }
//
//    @Bean
//    public FilterRegistrationBean<ValidBusCaptchaFilter> validCaptchaFilterFilterRegistrationBean(CaptchaVerifier captchaVerifier, VerifyCaptchaCompleteHandler verifyCaptchaCompleteHandler) {
//        ValidBusCaptchaFilter filter = new ValidBusCaptchaFilter(Arrays.asList(new AntPathRequestMatcher("/login1", "POST")), captchaVerifier, verifyCaptchaCompleteHandler);
//        FilterRegistrationBean<ValidBusCaptchaFilter> filterRegistration = new FilterRegistrationBean<>();
//        filterRegistration.setFilter(filter);
//        return filterRegistration;
//    }

}
