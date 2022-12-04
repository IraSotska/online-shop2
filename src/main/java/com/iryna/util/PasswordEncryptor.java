package com.iryna.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncryptor {

    private static final String STATIC_SALT = "public boolean isTokenExist(String token) {";

    public static String encrypt(String password) {
        try {
            var hash = MessageDigest.getInstance("SHA-256").digest((password + STATIC_SALT).getBytes(StandardCharsets.UTF_8));
            var encryptedPassword = new StringBuilder();
            for (byte b : hash) {
                encryptedPassword.append(String.valueOf(b));
            }
            return encryptedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
