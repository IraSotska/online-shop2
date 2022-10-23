package com.iryna.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Product {
    private Long id;
    private String name;
    private Double price;
    private LocalDateTime creationDate;
    private String description;
}
