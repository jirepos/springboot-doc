package com.kyoofus.core.filter;

import org.springframework.stereotype.Component;

/** GenerFilterBean을 테스트하깅 위한 로그 클래스이다. */
@Component
public class TestCustomLogger {
    public void log(String message) {
        System.out.println(message);
    }
}