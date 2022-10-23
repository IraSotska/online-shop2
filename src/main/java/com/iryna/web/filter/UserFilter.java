package com.iryna.web.filter;

import com.iryna.security.SecurityService;
import com.iryna.service.ServiceLocator;
import com.iryna.web.util.CookieExtractor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.iryna.security.entity.Role.USER;

public class UserFilter implements Filter {

    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;
        var httpServletResponse = (HttpServletResponse) servletResponse;

        var token = CookieExtractor.extractCookie(httpServletRequest.getCookies(), "user-token");

        if (token.isPresent()) {
            if (securityService.isAccessAllowedForRole(token.get(), USER)) {

                var session = securityService.getSession(token.get());
                httpServletRequest.setAttribute("session", session);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}
