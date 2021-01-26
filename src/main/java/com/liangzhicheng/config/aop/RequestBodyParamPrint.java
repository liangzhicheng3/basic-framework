package com.liangzhicheng.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @description 请求参数打印
 * @author liangzhicheng
 * @since 2021-01-26
 */
@Component
@Slf4j
public class RequestBodyParamPrint extends OncePerRequestFilter implements Ordered {

    private int order = Ordered.LOWEST_PRECEDENCE - 8;
    public static final String SPLIT_STRING_M = "=";
    public static final String SPLIT_STRING_DOT = ", ";

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //日志trade
        MDC.clear();
        MDC.put("trade_id", UUID.randomUUID().toString().replaceAll("-",""));
        ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
        String urlParams = getRequestParams(request);
        filterChain.doFilter(wrapperRequest, wrapperResponse);
        String requestBodyStr = getRequestBody(wrapperRequest);
        wrapperResponse.copyBodyToResponse();
        log.info("json参数:{}", requestBodyStr);
    }

    /**
     * 打印请求参数
     * @param request
     */
    private String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if(wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if(buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                return payload.replaceAll("\\n","");
            }
        }
        return "";
    }

    /**
     * 打印请求参数
     * @param response
     */
    /*private String getResponseBody(ContentCachingResponseWrapper response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if(wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if(buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                return payload;
            }
        }
        return "";
    }*/

    /**
     * 获取请求地址上的参数
     * @param request
     * @return
     */
    public static String getRequestParams(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> enu = request.getParameterNames();
        //获取请求参数
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            sb.append(name + SPLIT_STRING_M).append(request.getParameter(name));
            if(enu.hasMoreElements()) {
                sb.append(SPLIT_STRING_DOT);
            }
        }
        return sb.toString();
    }
}