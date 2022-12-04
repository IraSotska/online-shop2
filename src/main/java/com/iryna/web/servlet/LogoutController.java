package com.iryna.web.servlet;

import com.iryna.security.SecurityService;
import com.iryna.web.util.CookieExtractor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/logout")
@AllArgsConstructor
public class LogoutController {

    private SecurityService securityService;

    @PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var token = CookieExtractor.extractCookie(request.getCookies(), "user-token");

        token.ifPresent(s -> securityService.logout(s));

        response.sendRedirect("/login");
    }
}
