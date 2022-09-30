package com.iryna.web.servlet;

import com.iryna.service.ProductService;
import com.iryna.service.ServiceLocator;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class RemoveProductServlet extends HttpServlet {

    private ProductService productService = ServiceLocator.getService(ProductService.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);

        var id = Long.parseLong(request.getParameter("id"));
        productService.deleteById(id);

        log.info("Requested to delete product by id: {}", id);

        response.sendRedirect("/products");
    }
}
