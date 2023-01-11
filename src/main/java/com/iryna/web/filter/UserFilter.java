package com.iryna.web.filter;

import com.iryna.security.SecurityService;
import com.iryna.web.util.CookieExtractor;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.iryna.security.entity.Role.USER;

@Slf4j
@RequiredArgsConstructor
public class UserFilter implements Filter {

    private final SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;
        var httpServletResponse = (HttpServletResponse) servletResponse;

        var token = CookieExtractor.extractCookie(httpServletRequest.getCookies(), "user-token");

        if (token.isPresent()) {
            if (securityService.isAccessAllowedForRole(token.get(), USER)) {

                var session = securityService.getSession(token.get());
                httpServletRequest.setAttribute("session", session);
                log.info("User filter access allowed.");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        log.info("User filter access not allowed.");
        httpServletResponse.sendRedirect("/login");
    }
}
