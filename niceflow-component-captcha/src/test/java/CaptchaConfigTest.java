import com.niceflow.component.captcha.config.CaptchaProperties;
import com.niceflow.component.captcha.core.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author duanjw
 * @date 2023/4/18
 */
public class CaptchaConfigTest {

    private HttpServletRequest request;

    private HttpServletResponse response;
    private PrintWriter writer;
    private InMemoryCaptchaRepository captchaRepository;


    private CaptchaProvider captchaProvider;
    private CaptchaPreprocessor captchaPreprocessor;
    private CaptchaGenerator captchaGenerator;

    @BeforeEach
    @SneakyThrows
    public void init() {
        // 创建Mock HttpServletRequest 对象
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);

        // 设置Mock HttpServletRequest 对象的参数
        when(request.getParameter("username")).thenReturn("18630070626");
        when(request.getParameter("moduleId")).thenReturn("login_email");
        when(response.getWriter()).thenReturn(writer);
        captchaRepository = new InMemoryCaptchaRepository();
        captchaPreprocessor = new CaptchaPreprocessorManager(new ArrayList<>());
        captchaGenerator = new CaptchaGeneratorManager(Arrays.asList(new NumberCaptchaGenerator(), new ImageCaptchaGenerator()));
        CaptchaProperties captchaProperties = new CaptchaProperties();
        HashMap<String, CaptchaProperties.CaptchaConfig> captcha = new HashMap<>();
        CaptchaProperties.CaptchaConfig captchaConfig = new CaptchaProperties.CaptchaConfig();

        Generator generator = new Generator();
        generator.setType("1");
        generator.setKeyPattern("${moduleId}_${username}_${system.uuid}");
        generator.setLength(6);

        captchaConfig.setGenerator(generator);

        Sender sender = new Sender();
        sender.setType("1");
        sender.setReceiver("${username}");
        sender.setTemplateCode("login_email");

        captchaConfig.setSender(sender);

        captcha.put("login_email", captchaConfig);
        captchaProperties.setCaptcha(captcha);

        captchaProvider = new DefaultCaptchaProvider(captchaGenerator, captchaRepository, captchaProperties);
    }

    @Test
    @SneakyThrows
    public void testGetCaptcha() {
        captchaProvider.fetch(request, response);
//        getCaptchaFilter.doFilter(request, response, null);
    }

    @Test
    public void testVerify() {
//        Assertions.assertTrue(captchaEndpoint.verifyCaptcha("image", "aaa", "1234", request));
    }

    @Test
    public void testGetSliderCaptcha() {
//        Object captcha = captchaEndpoint.getCaptcha("slider", request);
//        System.out.println("获取到的验证码：" + captcha);
    }

}
