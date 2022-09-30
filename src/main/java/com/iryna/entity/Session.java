package com.iryna.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Session {
    private String token;
    private User user;
    private LocalDateTime expireDate;
    private List<Product> cart;
}
