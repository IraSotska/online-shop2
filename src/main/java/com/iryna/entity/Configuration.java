package com.iryna.entity;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Configuration {
    private String url;
    private String user;
    private String password;
    private Integer sessionTimeToLive;
}
