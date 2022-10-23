package com.iryna.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Properties;

@Getter
@RequiredArgsConstructor
public class ConfigLoader {

    private final String configPath;

    public String load(String propertyName) {
        var property = new Properties();
        try (var inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(configPath)) {
            property.load(inputStream);

            return property.getProperty(propertyName);
        } catch (IOException e) {
            throw new RuntimeException("Can't load configuration properties", e);
        }
    }
}
