package com.iryna.ioc.entity;

import com.iryna.ioc.annotation.Value;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestClass {

    @Value(path = "{$string.property}")
    private String stringProperty;

    @Value(path = "{$int.property}")
    private Integer intProperty;

    @Value(path = "{$boolean.property}")
    private Boolean booleanProperty;

    @Value(path = "{$double.property}")
    private Double doubleProperty;
}
