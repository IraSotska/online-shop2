package com.iryna.web.filter;

import com.iryna.security.SecurityService;
import com.iryna.service.ServiceLocator;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

public class SecurityFilter implements Filter {

    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;

        if (httpServletRequest.getRequestURI().equals("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        var cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "user-token")) {
                    if (securityService.isTokenExist(cookie.getValue())) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
            }
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }
}
