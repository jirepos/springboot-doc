package com.kyoofus.custom;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** Request Header에  "Authroization"과 같은 헤더를 설정하여 인증하는 방식을 처리하기 위한 필터이다. */
// Filter는 인스턴스를 생성하여 등록하기 때문에 @Component 같은 어노테이션은 붙이지 않는다.
// 한 번의 서블릿 요청에서 두 번 필터가 실행되지 않도록 OncePerRequestFilter를 상속받는다.
// 만일 서블릿이 호출되고 포워딩을 하는 경우 다시 서블릿 호출이 일어난다. 이 때 필터가 한 번 이상 실행되는 것을 막는다.
public class CustomFilter extends OncePerRequestFilter {
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();
    private AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    /** 기본 생성자 */
    public CustomFilter() {
        // AuthenticationManger를 외부에서 주입받지 않으면 직접 생성해도 된다.
        this.authenticationManager = new CustomAuthenticationManager();
    }

    /** 이 필터를 인스턴스화 하면서 AuthenticationManager를 직접 주입한다.  */
    public CustomFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(isContainPaySecurityKey(request)) {  // 헤더가 존재하면
            String token = request.getHeader("CustomToken");
//            Authentication은 인증 시에 사용자 아이디와 비밀번호를 가지고
//            인증 후에는 UserDetails와 권한정보를 갖는다.
            CustomToken authRequest = new CustomToken(token);
//            사용자 정보를 조회하고 읹으을 위해서 AuthenticationManager를 사용한다.
//            AuthenticationManager를 정의하여 직접 생성할 수도 있지만
//            SpringSecurity의 메커니즘을 사용하기 위해서 직접생성하지 않는다.
//            직접 생성하지 않으면 ProviderManager가 사용된다.
            Authentication authResult = this.authenticationManager.authenticate(authRequest);
//            Thread에 새로운 SecurityContext 인스턴스 생성
            SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
//            SecurityContext에 Authentication 저장
            context.setAuthentication(authResult);
//            SecurityContext 저장
            this.securityContextHolderStrategy.setContext(context);
//            SecurityContext를 저장하는 방식은 SecurityContextRepository에 의존한다.
//            스프링 시큐리티는 기본적으로 HttpSessionSecurityContextRepository 를 사용하기 때문에 인증정보가 HttpSession 에 저장된다.
            this.securityContextRepository.saveContext(context, request, response);
        }
//        필터체인의 다음 필터로 이동한다.
        filterChain.doFilter(request,response);
    }

    /** 헤더가 존재하는지 체크한다. */
    private boolean isContainPaySecurityKey(HttpServletRequest request) {
        String rsaKey = request.getHeader("CustomToken");
        return rsaKey != null;
    }
}
