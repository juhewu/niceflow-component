package com.niceflow.component.security.resource.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niceflow.component.common.utils.DefaultResponse;
import com.niceflow.component.common.utils.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * @author duanjw
 * @date 2023/7/9
 */
public class AbstractAuthenticationResponseJson {
    protected void responseSuccess(HttpServletRequest request, HttpServletResponse response, Object data, Integer code) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter writer = response.getWriter()) {
            R<Object> objectR = R.ok();
            objectR.setCode(code);
            Optional.ofNullable(data).map(DefaultResponse::ok).ifPresent(objectR::setData);
            writer.write(new ObjectMapper().writeValueAsString(objectR));
            writer.flush();
        }
    }

    protected void responseFailed(HttpServletRequest request, HttpServletResponse response, String message, Integer code) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(response.getStatus());
        try (PrintWriter writer = response.getWriter()) {
            writer.write(new ObjectMapper().writeValueAsString(R.failed(message).setCode(code)));
            writer.flush();
        }
    }
}
