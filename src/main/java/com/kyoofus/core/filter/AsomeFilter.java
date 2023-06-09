package com.kyoofus.core.filter;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AsomeFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(">>>>>>> SomeFilter");
        Object o = this;


        chain.doFilter(request, response);
    }
}
