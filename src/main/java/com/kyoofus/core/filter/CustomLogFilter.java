package com.kyoofus.core.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * GenerFilterBean을 테스트하깅 위한 필터 클래스이다.
 */
@Component
@RequiredArgsConstructor
public class CustomLogFilter extends GenericFilterBean {
    // GenericFilterBean을 상속 받으면 Spring Bean을 주입받아 쓸 수 있다.
    private final TestCustomLogger testCustomLogger;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 주입 받은 Bean을 사용한다.
        testCustomLogger.log("CustomLogFilter=====>");
        chain.doFilter(request, response);
    }
}