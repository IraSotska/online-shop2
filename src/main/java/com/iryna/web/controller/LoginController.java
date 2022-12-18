package com.iryna.web.controller;

import com.iryna.security.SecurityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final SecurityService securityService;

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
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
