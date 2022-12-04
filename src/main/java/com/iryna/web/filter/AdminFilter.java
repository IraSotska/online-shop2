package com.iryna.web.filter;

import com.iryna.security.SecurityService;
import com.iryna.web.util.CookieExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.iryna.security.entity.Role.ADMIN;

@Slf4j
public class AdminFilter implements Filter {

    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) {
        var webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        securityService = webApplicationContext.getBean(SecurityService.class);
    }

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
