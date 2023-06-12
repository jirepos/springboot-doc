package com.kyoofus.security.config;

public class SecurityConstatns {


    /** 정적자원 리소스 URL 패턴 */
    protected  static final String[] STATIC_RESOURCES_WHITELIST = {
            "/static/**",
            "/resources/**",
            "/public/**"
    };

    protected static final String[] CUSTOM_AUTH_WHITELIST = {
            // --- this app
            "/app/public/**"
    };

    /** Swagger URL  */
    private static final String[] SWAGGER_LIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/webjars/**"
    };

    // -- Form login
    public static final String FORM_LOGIN_URL = "/formlogin";
    public static final String SPRING_DEFAULT_FORM_LOGIN_URL = "/login";
    public static final String SPRING_DEFAULT_FORM_LOGIN_PROC_URL = "/login_proc";
    public static final String SPRING_DEFAULT_LOGOUT_URL = "/logout";

    public static final String DEFAULT_SUCCESS_URL = "/home";
    public static final String JSESSIONID = "JSESSIONID";
    public static final String LOGIN_FAIL_URL = "/loginfail";

    public static final String ROOT_URL = "/";
    public static final String API_NO_AUTH_URL = "/api/v1/**";


    // /** static 자원이외의 허용할 url */
    // private static final String[] AUTH_WHITELIST = {
    //         // -- this app
    //         ROOT_URL,            // "/" index page
    //         // API_NO_AUTH_URL,     // "/api/v1/**", // 인증없이 접근가능한 API
    //         // -- Spring Security
    //
    //         "/login/**"   , // /login/google, /login/azure
    //         "/oauth2/**",   // OAuth2 로그인  /oauth2/authorization/{providerType}
    //         SPRING_DEFAULT_FORM_LOGIN_PROC_URL,  // "/login_proc" , // 로그인 처리
    //         SPRING_DEFAULT_LOGOUT_URL       // "/logout"        // 로그아웃
    // };
    //
    // public static final String[] FORM_AUTH_WHITELIST = {
    //         SPRING_DEFAULT_FORM_LOGIN_URL, //  "/login",      // 로그인
    //         FORM_LOGIN_URL,      // "formlogin",  // 폼로그인
    //         LOGIN_FAIL_URL,      // "/loginfail", // 로그인 실패 페이지
    // };

}///~
