package com.iryna.web.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Objects;
import java.util.Optional;

@Component
public class CookieExtractor {

    public static Optional<String> extractCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), name)) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }
}
