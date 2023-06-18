package com.niceflow.component.spring.plus.util;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取请求者ip
 *
 * @author duanjw
 */
@Slf4j
@UtilityClass
public class IpAddrUtil {

    /**
     * k8s 容器中，会把真实 ip 放到 X-Original-Forwarded-For
     */
    private static final String[] ADDR_HEADER = { "X-Original-Forwarded-For", "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "X-Real-IP" };
    private static final String NUKNOWN = "unknown";
    private static final String LOCAL_IP4 = "127.0.0.1";
    private static final String LOCAL_IP6 = "0:0:0:0:0:0:0:1";

    /**
     * 获得请求者 IP 地址。
     * 在使用了反向代理时，直接用 HttpServletRequest.getRemoteAddr() 无法获取客户真实的 IP 地址。
     *
     * @param request request
     * @return 请求者 ip
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String addr = null;
        for (String header : ADDR_HEADER) {
            if (StringUtils.isBlank(addr) || NUKNOWN.equalsIgnoreCase(addr)) {
                addr = request.getHeader(header);
            } else {
                break;
            }
        }
        if (StringUtils.isBlank(addr) || NUKNOWN.equalsIgnoreCase(addr)) {
            addr = request.getRemoteAddr();
            addr = convertLocalHost(addr);
        } else {
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按','分割
            int i = addr.indexOf(",");
            if (i > 0) {
                addr = addr.substring(0, i);
            }

        }
        return addr;
    }

    /**
     * 如果传入的 ip 是本机 ip，获取本机网卡对应的 ip
     *
     * @param addr 传入的 ip
     * @return
     */
    private static String convertLocalHost(String addr) {
        if (LOCAL_IP4.equals(addr) || LOCAL_IP6.equals(addr)) {
            // 根据网卡取本机配置的 IP
            try {
                InetAddress inet = InetAddress.getLocalHost();
                addr = inet.getHostAddress();
            } catch (UnknownHostException e) {
                log.error("获取本地网卡 ip 异常", e);
            }
        }
        return addr;
    }
}

