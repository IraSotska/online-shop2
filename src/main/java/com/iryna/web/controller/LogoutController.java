package com.iryna.web.controller;

import com.iryna.security.SecurityService;
import com.iryna.web.util.CookieExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {

    private final SecurityService securityService;

    @PostMapping
    public String logout(HttpServletRequest request) {
        CookieExtractor.extractCookie(request.getCookies(), "user-token").ifPresent(securityService::logout);

        return "redirect:login";
    }
}
