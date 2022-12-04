package com.iryna.web.servlet;

import com.iryna.security.SecurityService;
import com.iryna.web.template.PageGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final PageGenerator pageGenerator;
    private final SecurityService securityService;

    @GetMapping
    public void doGet(HttpServletResponse response) throws IOException {
        response.getWriter().println(pageGenerator.generatePage("login.html", Map.of()));
    }

    @PostMapping
    public String doPost(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        var token = securityService.login(login, password);
        if (token == null) {
            log.info("Not correct password for user: {}", login);
            return "redirect:login";
        }
        log.info("Login user: {}", login);

        var cookie = new Cookie("user-token", token);
        var maxAge = securityService.getSessionTimeToLive();
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
        return "redirect:products";
    }
}
