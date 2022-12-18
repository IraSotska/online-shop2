package com.iryna.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    private Long id;
    private String name;
    private Double price;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime creationDate;
    private String description;
}
