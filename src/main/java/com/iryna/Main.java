package com.iryna;

import com.iryna.config.PropertyLoader;
import com.iryna.db.JdbcProductDao;
import com.iryna.service.ProductService;
import com.iryna.servlet.AddProductServlet;
import com.iryna.servlet.EditProductServlet;
import com.iryna.servlet.ProductsServlet;
import com.iryna.servlet.RemoveProductServlet;
import com.iryna.template.PageGenerator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;

public class Main {

    public static void main(String[] args) throws Exception {

        var properties = PropertyLoader.load("application.properties");

        var pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(properties.getUrl());
        pgSimpleDataSource.setPassword(properties.getPassword());
        pgSimpleDataSource.setUser(properties.getUser());

        var dbService = new JdbcProductDao(pgSimpleDataSource);

        var pageGenerator = new PageGenerator();
        var productService = new ProductService(dbService);

        var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ProductsServlet(pageGenerator, productService)), "/products");
        context.addServlet(new ServletHolder(new AddProductServlet(pageGenerator, productService)), "/products/add");
        context.addServlet(new ServletHolder(new RemoveProductServlet(productService)), "/products/remove");
        context.addServlet(new ServletHolder(new EditProductServlet(pageGenerator, productService)), "/products/edit");

        var server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
