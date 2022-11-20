package com.iryna.dao.jdbc;

import com.iryna.ioc.annotation.Value;
import com.study.ioc.processor.PostConstruct;
import org.postgresql.ds.PGSimpleDataSource;

public class DataSourceContainer {

    private static final PGSimpleDataSource PG_SIMPLE_DATA_SOURCE = new PGSimpleDataSource();

    @Value(path = "${db.password}")
    private String password;

    @Value(path = "${db.url}")
    private String url;

    @Value(path = "${db.user}")
    private String user;

    @PostConstruct
    public void init() {
        PG_SIMPLE_DATA_SOURCE.setUrl(url);
        PG_SIMPLE_DATA_SOURCE.setPassword(password);
        PG_SIMPLE_DATA_SOURCE.setUser(user);
    }

    public PGSimpleDataSource getPGSimpleDataSource() {
        return PG_SIMPLE_DATA_SOURCE;
    }
}
