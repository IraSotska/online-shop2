package com.iryna.web.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;

class CookieExtractorTest {

    @Test
    @DisplayName("Should Extract Cookie")
    void shouldExtractCookie() {
        var coockiesName = "user-token";
        var value = "token";
        var cookies = new Cookie[] {new Cookie("some", "value"), new Cookie(coockiesName, value)};
        assertEquals(CookieExtractor.extractCookie(cookies, coockiesName).get(), value);
    }
}
