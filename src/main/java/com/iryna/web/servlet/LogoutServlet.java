package com.iryna.web.servlet;

import com.iryna.security.SecurityService;
import com.iryna.service.ServiceLocator;
import com.iryna.web.util.CookieExtractor;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LogoutServlet extends HttpServlet {

    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var token = CookieExtractor.extractCookie(request.getCookies(), "user-token");

        token.ifPresent(s -> securityService.logout(s));

        response.sendRedirect("/login");
    }
}
