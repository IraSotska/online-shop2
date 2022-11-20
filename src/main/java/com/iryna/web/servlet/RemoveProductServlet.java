package com.iryna.web.servlet;

import com.iryna.service.ProductService;
import com.iryna.ioc.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RemoveProductServlet extends HttpServlet {

    private ProductService productService = ApplicationContext.getService(ProductService.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var id = Long.parseLong(request.getParameter("id"));
        productService.deleteById(id);

        log.info("Requested to delete product by id: {}", id);

        response.sendRedirect("/products");
    }
}
