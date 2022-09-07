package com.iryna.entity;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Timestamp creationDate;
    private String description;
}
