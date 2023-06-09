package com.kyoofus.security.config;

public class MultipleSecurityBaseConfig {

    /** 정적자원 리소스 URL 패턴 */
    protected  static final String[] STATIC_RESOURCES_WHITELIST = {
            "/static/**",
            "/resources/**",
            "/public/**"
    };

}///~
