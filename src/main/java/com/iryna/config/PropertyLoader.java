package com.iryna.config;

import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

@Getter
public class PropertyLoader {

    public static com.iryna.entity.Properties load(String path) {
        var property = new Properties();
        try (var inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(path)) {
            property.load(inputStream);

            return com.iryna.entity.Properties.builder()
                    .password(property.getProperty("db.password"))
                    .url(property.getProperty("db.url"))
                    .user(property.getProperty("db.user"))
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Can't load configuration properties", e);
        }
    }
}
