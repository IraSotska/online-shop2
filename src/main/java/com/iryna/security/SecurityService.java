package com.iryna.security;

import com.iryna.security.entity.Role;
import com.iryna.security.entity.Session;
import com.iryna.service.UserService;
import com.iryna.util.PasswordEncryptor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.iryna.security.entity.Role.*;

@Slf4j
@Getter
@RequiredArgsConstructor
@Service
public class SecurityService {

    private List<Session> sessionList = new ArrayList<>();
    private final UserService userService;

    @Value("${session.time-to-live}")
    private Integer sessionTimeToLive;

    public String login(String login, String password) {
        var userFromDb = userService.findByLogin(login);

        if (userFromDb != null) {
            var encryptedPassword = PasswordEncryptor.encrypt(userFromDb.getSalt() + password);

            if (Objects.equals(encryptedPassword, userFromDb.getEncryptedPassword())) {
                var uuid = UUID.randomUUID().toString();
                sessionList.add(Session.builder()
                        .expireDate(LocalDateTime.now().plusSeconds(sessionTimeToLive))
                        .user(userFromDb)
                        .token(uuid)
                        .build());
                return uuid;
            }
        }
        return null;
    }

    public void logout(String token) {
        sessionList.removeIf(session -> Objects.equals(token, session.getToken()));
    }

    public Session getSession(String token) {
        var optionalSession = sessionList.stream()
                .filter(session -> Objects.equals(session.getToken(), token)).findFirst();
        if (optionalSession.isPresent()) {
            var session = optionalSession.get();
            if (LocalDateTime.now().isBefore(session.getExpireDate())) {
                session.setCart(userService.getCart(session.getUser().getLogin()));
                return session;
            }
            logout(token);
        }
        return null;
    }

    public boolean isAccessAllowedForRole(String token, Role role) {
        var optionalSession = sessionList.stream()
                .filter(session -> Objects.equals(session.getToken(), token)).findFirst();

        if (optionalSession.isPresent()) {
            var session = optionalSession.get();
            if (LocalDateTime.now().isBefore(session.getExpireDate())) {
                var currentRole = session.getUser().getRole();

                if ((role.equals(currentRole)) || (Objects.equals(ADMIN, currentRole))) {
                    return true;
                }
                if ((Objects.equals(USER, currentRole)) && ((role.equals(USER)) || (role.equals(GUEST)))) {
                    return true;
                }
            }
            logout(token);
        }
        return false;
    }
}
