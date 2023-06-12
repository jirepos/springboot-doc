package com.kyoofus.security.config;

import com.kyoofus.security.handlers.AuthFailHandler;
import com.kyoofus.security.handlers.AuthSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

public class ConfigFormAuthentication {


    /** 디폴트 폼로그인 로그아아웃 */
    public static void defaultFormLoginLogout(HttpSecurity http) throws Exception {
        // 로그인
        http.formLogin(withDefaults()); // SpringSecurity의 default login form이 표시된다.
        http.logout(withDefaults()); // SpringSecurity default logout, logout url은
    }

    /** 폼로그인 비활성화  */
    public static void disalbeFormLogin(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable);
        // http.formLogin(customizer -> customizer.disable());
    }

    /** 커스텀 폼 로그인 */
    private static void customFormLogin(HttpSecurity http, AuthSuccessHandler authSuccessHandler,  AuthFailHandler authFailHandler) throws Exception {
        // 커스텀 폼 로그인
        http.formLogin(formLoginCustomizer -> formLoginCustomizer
                .loginPage(SecurityConstatns.FORM_LOGIN_URL).permitAll(true) // default loing url과 동일한 url 사용하면 안된다.
                // 로그인 처리 URL, default URL은 /login, loginPage()를 수정했으면 이것을 지정해야 함
                // username, password가 전달될 url
                .loginProcessingUrl(SecurityConstatns.SPRING_DEFAULT_FORM_LOGIN_PROC_URL).permitAll(true)
                // .defaultSuccessUrl( DEFAULT_SUCCESS_URL, true).permitAll(true) // 로그인 성공 후에 랜딩 페이지
                // .failureUrl(LOGIN_FAIL_URL) // 인증 실패 시 url.   "/login-fail"
                // 로그인 성공 시 추가적인 처리를 위해 AuthenticationSuccessHandler를 구현
                // .successHandler(
                //     new AuthenticationSuccessHandler() {
                //       @Override
                //       public void onAuthenticationSuccess(HttpServletRequest request,
                //           HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                //         System.out.println("authentication : " + authentication.getName());
                //         response.sendRedirect(DEFAULT_SUCCESS_URL);  // 인증이 성공한 후에는 /home으로 이동
                //       }
                //     })
                .successHandler(authSuccessHandler)
                // .successHandler(new AuthSuccessHandler())
                // 로그인 실패 시 추가적인 처리를 위해 AuthenticationFailureHandler 구현
                // .failureHandler(new AuthenticationFailureHandler() {
                //   @Override
                //   public void onAuthenticationFailure(HttpServletRequest request,
                //       HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                //     System.out.println("exception : " + exception.getMessage());
                //     response.sendRedirect(LOGIN_FAIL_URL); // 인증이 실패한 후에는 /loginfail로 이동
                //   }
                // })
                .failureHandler(authFailHandler)
        );
    }
}
