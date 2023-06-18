package com.niceflow.component.captcha.utils;

import jakarta.servlet.ServletRequest;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author duanjw
 * @date 2023/4/24
 */
@UtilityClass
public class VariableUtil {
    public static Pattern dynamicLimitCount = Pattern.compile("\\$\\{([a-zA-Z.]+)\\}");

    public static Set<String> getVariable(String content) {
        Set<String> variable = null;
        if (null != content) {
            variable = new HashSet<>();
            Matcher matcher = dynamicLimitCount.matcher(content);

            while (matcher.find()) {
                variable.add(matcher.group(1));
            }
        }

        return variable;
    }

    public static String replace(String content, ServletRequest request) {
        Set<String> variables = VariableUtil.getVariable(content);
        for (String variable : variables) {
            String out;
            switch (variable) {
                case "system.uuid":
                    out = UUID.randomUUID().toString();
                    break;
                default:
                    out = request.getParameter(variable);
                    break;
            }
            content = content.replace("${" + variable + "}", out);
        }
        return content;
    }
}
