package com.iryna.web.filter;

import com.iryna.security.SecurityService;
import com.iryna.web.util.CookieExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.iryna.security.entity.Role.ADMIN;

@Slf4j
@RequiredArgsConstructor
public class AdminFilter implements Filter {

    private final SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;
        var httpServletResponse = (HttpServletResponse) servletResponse;
        var token = CookieExtractor.extractCookie((httpServletRequest).getCookies(), "user-token");

        if (token.isPresent()) {
            if (securityService.isAccessAllowedForRole(token.get(), ADMIN)) {
                log.info("Admin filter access allowed.");
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }
        log.info("Admin filter access not allowed.");
        httpServletResponse.sendRedirect("/login");
    }
}
