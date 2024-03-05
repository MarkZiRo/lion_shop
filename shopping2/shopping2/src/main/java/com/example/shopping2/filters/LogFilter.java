package com.example.shopping2.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
@Component
public class LogFilter implements Filter{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        log.info("start request : {} {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());

        ContentCachingRequestWrapper requestWrapper
                = new ContentCachingRequestWrapper(httpServletRequest);

        log.info(new String(
                requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));

        chain.doFilter(request,response);

        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        log.info("send response : {}", httpServletResponse.getStatus());
    }
}