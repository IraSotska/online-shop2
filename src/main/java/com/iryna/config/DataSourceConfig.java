package com.iryna.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Value("${db.password}")
    private String password;

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Bean
    public PGSimpleDataSource pGSimpleDataSource() {
        var pGSimpleDataSource = new PGSimpleDataSource();
        pGSimpleDataSource.setUser(user);
        pGSimpleDataSource.setPassword(password);
        pGSimpleDataSource.setUrl(url);

        return pGSimpleDataSource;
    }
}
