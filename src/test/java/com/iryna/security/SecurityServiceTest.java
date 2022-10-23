package com.iryna.security;

import com.iryna.entity.Product;
import com.iryna.security.entity.Role;
import com.iryna.entity.User;
import com.iryna.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.iryna.security.entity.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityServiceTest {

    private static final String ENCRYPTED_PASSWORD =
            "49-7326-8046-77-971106656023101-54-91121-20-57-118-461-105-95-63-10478-11796-42-94-938";
    private UserService userService = mock(UserService.class);
    private SecurityService securityService = new SecurityService(userService, 5000);

    @Test
    @DisplayName("Should Create Session")
    void shouldCreateSession() {
        var login = "log";
        var user = User.builder()
                .role(USER)
                .login(login)
                .encryptedPassword(ENCRYPTED_PASSWORD)
                .salt("salt")
                .build();

        var product = Product.builder()
                .description("some")
                .creationDate(LocalDateTime.now())
                .name("name")
                .price(1.1D)
                .id(1L)
                .build();

        when(userService.findByLogin(login)).thenReturn(user);
        when(userService.getCart(login)).thenReturn(List.of(product));

        var token = securityService.login(login, "pass");
        var session = securityService.getSession(token);

        assertEquals(session.getUser(), user);
        assertEquals(session.getToken(), token);
        assertEquals(session.getCart(), List.of(product));

        verify(userService).findByLogin(login);
        verify(userService).getCart(login);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Should Check Is Access To Admin Page Allowed For Admin Role")
    void shouldCheckIsAccessToAdminPageAllowedForAdminRole() {
        assertTrue(isUserHaveAccessForRole(ADMIN, ADMIN));
    }

    @Test
    @DisplayName("should Check Is Access To User Page Allowed For Admin Role")
    void shouldCheckIsAccessToUserPageAllowedForAdminRole() {
        assertTrue(isUserHaveAccessForRole(USER, ADMIN));
    }

    @Test
    @DisplayName("Should Check Is Access To Guest Page Allowed For Admin Role")
    void shouldCheckIsAccessToGuestPageAllowedForAdminRole() {
        assertTrue(isUserHaveAccessForRole(GUEST, ADMIN));
    }

    @Test
    @DisplayName("Should Check Is Access To Guest Page Allowed For Guest Role")
    void shouldCheckIsAccessToGuestPageAllowedForGuestRole() {
        assertTrue(isUserHaveAccessForRole(GUEST, GUEST));
    }

    @Test
    @DisplayName("Should Check Is Access To Admin Page Allowed For Guest Role")
    void shouldCheckIsAccessToAdminPageAllowedForGuestRole() {
        assertFalse(isUserHaveAccessForRole(ADMIN, GUEST));
    }

    @Test
    @DisplayName("Should Check Is Access To User Page Allowed For Guest Role")
    void shouldCheckIsAccessToUserPageAllowedForGuestRole() {
        assertFalse(isUserHaveAccessForRole(USER, GUEST));
    }

    @Test
    @DisplayName("Should Check Is Access To User Page Allowed For User Role")
    void shouldCheckIsAccessToUserPageAllowedForUserRole() {
        assertTrue(isUserHaveAccessForRole(USER, USER));
    }

    @Test
    @DisplayName("Should Check Is Access To Admin Page Allowed For User Role")
    void shouldCheckIsAccessToAdminPageAllowedForUserRole() {
        assertFalse(isUserHaveAccessForRole(ADMIN, USER));
    }

    @Test
    @DisplayName("Should Check Is Access To Guest Page Allowed For User Role")
    void shouldCheckIsAccessToGuestPageAllowedForUserRole() {
        assertTrue(isUserHaveAccessForRole(GUEST, USER));
    }

    private boolean isUserHaveAccessForRole(Role pageToAccess, Role userRole) {
        var login = "log";
        var user = User.builder()
                .role(userRole)
                .encryptedPassword(ENCRYPTED_PASSWORD)
                .salt("salt")
                .build();

        when(userService.findByLogin(login)).thenReturn(user);

        var result = securityService.isAccessAllowedForRole(securityService.login(login, "pass"), pageToAccess);

        verify(userService).findByLogin(login);
        verifyNoMoreInteractions(userService);

        return result;
    }
}
