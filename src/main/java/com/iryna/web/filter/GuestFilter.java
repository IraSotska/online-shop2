package com.iryna.web.filter;

import com.iryna.security.SecurityService;
import com.iryna.service.ServiceLocator;

import com.iryna.web.util.CookieExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.iryna.entity.Role.GUEST;

public class GuestFilter implements Filter {

    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;
        var httpServletResponse = (HttpServletResponse) servletResponse;

        if (httpServletRequest.getRequestURI().equals("/login")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var token = CookieExtractor.extractCookie(httpServletRequest.getCookies(), "user-token");

        if (token.isPresent()) {
            if (securityService.isAccessAllowedForRole(token.get(), GUEST)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }

        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}
