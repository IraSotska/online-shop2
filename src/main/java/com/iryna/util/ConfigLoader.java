package com.iryna.util;

import lombok.Setter;

import java.io.IOException;
import java.util.Properties;

@Setter
public class ConfigLoader {

    public String load(String propertyName) {
        var property = new Properties();
        try (var inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            property.load(inputStream);

            return property.getProperty(propertyName);
        } catch (IOException e) {
            throw new RuntimeException("Can't load configuration properties", e);
        }
    }
}
