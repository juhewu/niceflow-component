package com.niceflow.component.spring.plus.exception;

import com.mongodb.MongoException;
import com.niceflow.component.common.utils.R;
import com.niceflow.component.spring.plus.util.IpAddrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

/**
 * 统一异常拦截
 *
 * @author duanjw
 * @date 2023/2/15
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数校验异常
     *
     * @param req 请求
     * @param e   参数错误信息
     * @return 客户端响应
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public R<Object> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage()).append(";");
        }

        log(req, errorMessage.toString(), e);
        return R.failed(errorMessage.substring(0, errorMessage.length() - 1));
    }

    /**
     * 业务异常
     *
     * @param req 请求
     * @param e   参数错误信息
     * @return 客户端响应
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public R<Object> handleException(HttpServletRequest req, BusinessException e) {
        log(req, e);
        return R.failed(e.getErrorCode(), e.getMessage());
    }

    /**
     * 请求参数异常
     *
     * @param req 请求
     * @param e   参数错误信息
     * @return 客户端响应
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public R<Object> handleException(HttpServletRequest req, HttpMessageNotReadableException e) {
        log(req, e);
        return R.failed("传递的参数异常，请检查请求地址或请求体中的参数");
    }

    /**
     * 资源未找到异常
     *
     * @param req 请求
     * @param e   参数错误信息
     * @return 客户端响应
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public R<Object> handleException(HttpServletRequest req, NoHandlerFoundException e) {
        log(req, e);
        return R.failed("没有找到您要访问的资源，请检查请求地址是否正确，请求地址：" + req.getRequestURI());
    }

    /**
     * 数据库异常
     *
     * @param req 请求
     * @param e   参数错误信息
     * @return 客户端响应
     */
    @ExceptionHandler({MongoException.class, SQLException.class, DataAccessException.class, CannotCreateTransactionException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Object> handleDbException(HttpServletRequest req, Exception e) {
        log(req, e);
        return R.failed("访问数据库出现异常，程序猿小哥哥正在努力修复中");
    }

    /**
     * 无权访问该资源
     *
     * @param req 请求
     * @param e   参数错误信息
     * @return 客户端响应
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public R<Object> handleException(HttpServletRequest req, AccessDeniedException e) {
        log(req, e);
        return R.failed("无权访问该资源").setCode(HttpStatus.FORBIDDEN.value());
    }

    /**
     * 全局异常
     *
     * @param req 请求
     * @param e   参数错误信息
     * @return 客户端响应
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public R<Object> handleException(HttpServletRequest req, Exception e) {
        log(req, e);
        return R.failed(e.getMessage());
    }

    /**
     * 记录日志
     *
     * @param req 请求
     * @param e   异常
     */
    private void log(HttpServletRequest req, Exception e) {
        log.error("业务异常，请求地址：{}，错误信息：{}", req.getMethod() + " " + req.getRequestURI(), e.getMessage(), e);
    }

    /**
     * 记录日志
     *
     * @param req     请求
     * @param message 错误消息
     * @param e       异常
     */
    private void log(HttpServletRequest req, String message, Exception e) {
        log.error("业务异常，请求地址：{}，错误信息：{}，User-Agent：{}，用户 IP：{}", req.getMethod() + " " + req.getRequestURI(), message,
                IpAddrUtil.getRemoteAddr(req), req.getHeader("User-Agent"), e);
    }
}