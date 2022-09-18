package com.iryna.security;

import com.iryna.service.UserService;
import com.iryna.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private List<String> tokens = new ArrayList<>();

    public String login(String login, String password) {
        var userFromDb = userService.findByLogin(login);
        var encryptedPassword = PasswordEncryptor.encrypt(userFromDb.getSalt() + password);

        if (Objects.equals(encryptedPassword, userFromDb.getEncryptedPassword())) {
            var uuid = UUID.randomUUID().toString();
            tokens.add(uuid);
            return uuid;
        }
        return null;
    }

    public boolean isTokenExist(String token) {
        return tokens.contains(token);
    }
}
