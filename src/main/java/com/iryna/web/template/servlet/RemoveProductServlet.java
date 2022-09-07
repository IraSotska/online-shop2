package com.iryna.web.template.servlet;

import com.iryna.service.ProductService;
import com.iryna.service.ServiceLocator;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProductServlet extends HttpServlet {

    private ProductService productService = ServiceLocator.getService(ProductService.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        productService.deleteById(Long.parseLong(request.getParameter("id")));

        response.sendRedirect("/products");
    }
}
