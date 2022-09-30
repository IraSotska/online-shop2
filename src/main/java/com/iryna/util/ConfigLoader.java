package com.iryna.util;

import com.iryna.entity.Configuration;
import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

@Getter
public class ConfigLoader {

    public static Configuration load(String path) {
        var property = new Properties();
        try (var inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(path)) {
            property.load(inputStream);

            return Configuration.builder()
                    .password(property.getProperty("db.password"))
                    .url(property.getProperty("db.url"))
                    .user(property.getProperty("db.user"))
                    .sessionTimeToLive(Integer.parseInt(property.getProperty("session.time-to-live")))
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Can't load configuration properties", e);
        }
    }
}
