package com.iryna.web.servlet;

import com.iryna.security.SecurityService;
import com.iryna.ioc.ApplicationContextListener;
import com.iryna.web.util.CookieExtractor;
import com.study.ioc.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LogoutServlet extends HttpServlet {

    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) {
        var context = (ApplicationContext) config.getServletContext().getAttribute(ApplicationContextListener.APPLICATION_CONTEXT);
        securityService = context.getBean(SecurityService.class);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var token = CookieExtractor.extractCookie(request.getCookies(), "user-token");

        token.ifPresent(s -> securityService.logout(s));

        response.sendRedirect("/login");
    }
}
