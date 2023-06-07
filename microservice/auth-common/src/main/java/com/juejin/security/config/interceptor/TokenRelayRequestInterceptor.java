package com.juejin.security.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenRelayRequestInterceptor implements RequestInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        // 获取该次请求得token 将token传递
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.isEmpty(token)) {
            template.header(AUTHORIZATION_HEADER, token);
        }
    }
}