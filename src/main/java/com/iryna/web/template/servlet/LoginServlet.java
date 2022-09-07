package com.iryna.web.template.servlet;

import com.iryna.service.ServiceLocator;
import com.iryna.web.template.PageGenerator;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {

    private PageGenerator pageGenerator = ServiceLocator.getService(PageGenerator.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageGenerator.generatePage("login.html", Map.of()));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var uuid = UUID.randomUUID();
        var cookie = new Cookie("user-token", uuid.toString());
        response.addCookie(cookie);
        response.sendRedirect("/products");
    }
}
