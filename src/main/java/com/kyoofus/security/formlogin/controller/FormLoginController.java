package com.kyoofus.security.formlogin.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/** Spring Scurity Form Login 테스트용 컨트롤러이다. */
@Controller
@Slf4j
public class FormLoginController {

    
    /** 사용자 정의 로그인 페이지 */
    @GetMapping("/formlogin")
    public String formlogin() {
        return "formlogin";
    }


    /** 로그인 실패 페이지*/
    @GetMapping("/login-fail")
    public String loginFail() {
        return "loginFail.mustache";
    }


    /** 로그인 사용자 정보 표시 */
    @GetMapping("/user-info")
    public String userInfo(HttpServletRequest request, Model model) {

        // 로그인을 통해 인증된 유저 정보는 Security Context Holder에 저장되며 아래와 같이 가져올 수 있다. 
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // import org.springframework.security.core.annotation.AuthenticationPrincipal;
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) obj;
        log.debug("user: {}", user.getUsername());
        System.out.println("user: " + user.getUsername());
        // request.setAttribute("user", user);

        model.addAttribute("user", user); // mustache에서 사용하려면 Model 객체에 담아야 한다.
        model.addAttribute("greeting", "Hello");
        request.setAttribute("greeting", "Hello"); // mustache에서 사용할 수 없다.
        return "userinfo";

    }

}