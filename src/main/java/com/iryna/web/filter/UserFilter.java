package com.iryna.web.filter;

import com.iryna.security.SecurityService;
import com.iryna.service.ServiceLocator;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserFilter implements Filter {

    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var cookies= ((HttpServletRequest)servletRequest).getCookies();

        if(securityService.isTokenExist(cookies)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            ((HttpServletResponse)servletResponse).sendRedirect("/login");
        }
    }
}
