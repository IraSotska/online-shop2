package com.iryna.web.controller;

import com.iryna.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {

    private final SecurityService securityService;

    @PostMapping
    public String logout(@CookieValue("user-token") String token) {
        securityService.logout(token);
        return "redirect:login";
    }
}
