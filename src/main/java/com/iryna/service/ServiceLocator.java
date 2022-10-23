package com.iryna.service;

import com.iryna.dao.jdbc.JdbcProductDao;
import com.iryna.dao.jdbc.JdbcUserDao;
import com.iryna.security.SecurityService;
import com.iryna.util.ConfigLoader;
import com.iryna.web.template.PageGenerator;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<Class<?>, Object> SERVICES = new HashMap<>();

    static {
        var configLoader = new ConfigLoader("application.properties");
        var pgSimpleDataSource = new PGSimpleDataSource();

        pgSimpleDataSource.setUrl(configLoader.load("db.url"));
        pgSimpleDataSource.setPassword(configLoader.load("db.password"));
        pgSimpleDataSource.setUser(configLoader.load("db.user"));

        var jdbcProductDao = new JdbcProductDao(pgSimpleDataSource);
        var pageGenerator = new PageGenerator();
        var productService = new ProductService(jdbcProductDao);
        var userService = new UserService(new JdbcUserDao(pgSimpleDataSource));

        var securityService = new SecurityService(userService, configLoader);

        addService(ProductService.class, productService);
        addService(DataSource.class, pgSimpleDataSource);
        addService(JdbcProductDao.class, jdbcProductDao);
        addService(UserService.class, userService);
        addService(PageGenerator.class, pageGenerator);
        addService(ConfigLoader.class, configLoader);
        addService(SecurityService.class, securityService);
    }

    public static <T> T getService(Class<T> serviceType) {
        return serviceType.cast(SERVICES.get(serviceType));
    }

    public static void addService(Class<?> serviceName, Object service) {
        SERVICES.put(serviceName, service);
    }
}
