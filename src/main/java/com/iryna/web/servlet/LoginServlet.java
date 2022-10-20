package com.iryna.web.servlet;

import com.iryna.security.SecurityService;
import com.iryna.service.ServiceLocator;
import com.iryna.web.template.PageGenerator;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class LoginServlet extends HttpServlet {

    private PageGenerator pageGenerator = ServiceLocator.getService(PageGenerator.class);
    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageGenerator.generatePage("login.html", Map.of()));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var login = request.getParameter("login");
        var password = request.getParameter("password");

        var token = securityService.login(login, password);
        if (token == null) {
            log.info("Not correct password for user: {}", login);
            response.sendRedirect("/login");
        }
        log.info("Login user: {}", login);

        var cookie = new Cookie("user-token", token);
        cookie.setMaxAge(4 * 60 * 60);
        response.addCookie(cookie);
        response.sendRedirect("/products");
    }
}
