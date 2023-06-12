package com.kyoofus.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class ConfigCsrf {
    public static void defautlCsrf(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable()); // csrf 사용하지 않음
    }
}///~
