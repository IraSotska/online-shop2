package com.iryna.security;

import com.iryna.security.entity.Role;
import com.iryna.security.entity.Session;
import com.iryna.service.UserService;
import com.iryna.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

import static com.iryna.security.entity.Role.*;

@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private final Integer sessionTimeToLive;
    private final Map<String, Session> sessionList = Collections.synchronizedMap(new HashMap<>());

    public String login(String login, String password) {
        var userFromDb = userService.findByLogin(login);

        if (userFromDb != null) {
            var encryptedPassword = PasswordEncryptor.encrypt(userFromDb.getSalt() + password);

            if (Objects.equals(encryptedPassword, userFromDb.getEncryptedPassword())) {
                var uuid = UUID.randomUUID().toString();
                sessionList.put(uuid, Session.builder()
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
        var session = sessionList.entrySet().stream()
                .filter(e -> Objects.equals(token, e.getValue().getToken())).findFirst();
        session.ifPresent(stringSessionEntry -> sessionList.remove(stringSessionEntry.getKey()));
    }

    public Session getSession(String token) {
        if (sessionList.containsKey(token)) {
            var session = sessionList.get(token);
            if (LocalDateTime.now().isBefore(session.getExpireDate())) {
                session.setCart(userService.getCart(session.getUser().getLogin()));
                return session;
            }
            sessionList.remove(token);
        }
        return null;
    }

    public boolean isAccessAllowedForRole(String token, Role role) {
        if (sessionList.containsKey(token)) {
            var session = sessionList.get(token);
            if (LocalDateTime.now().isBefore(session.getExpireDate())) {
                var currentRole = session.getUser().getRole();

                if ((role.equals(currentRole)) || (Objects.equals(ADMIN, currentRole))) {
                    return true;
                }
                if ((Objects.equals(USER, currentRole)) && ((role.equals(USER)) || (role.equals(GUEST)))) {
                    return true;
                }
            }
            sessionList.remove(token);
        }
        return false;
    }
}
