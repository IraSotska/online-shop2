package com.iryna.security;

import jakarta.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecurityService {

    private List<String> tokens = new ArrayList<>();

    public boolean isTokenExist(Cookie[] cookies) {
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "user-token")) {
                    return tokens.contains(cookie.getValue());
                }
            }
        }
        return false;
    }
}
