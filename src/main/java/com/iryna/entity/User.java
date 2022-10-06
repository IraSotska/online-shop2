package com.iryna.entity;

import com.iryna.security.entity.Role;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class User {
    private String login;
    private Role role;
    private String salt;
    private String encryptedPassword;
    private List<Product> cart;
}
