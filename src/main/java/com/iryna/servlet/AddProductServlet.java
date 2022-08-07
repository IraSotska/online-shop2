package com.iryna.servlet;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import com.iryna.template.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

@RequiredArgsConstructor
public class AddProductServlet extends HttpServlet {

    private final PageGenerator pageGenerator;
    private final ProductService productService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageGenerator.generatePage("add_product.html", Map.of()));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var product = Product.builder()
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .name(request.getParameter("productName"))
                .price(Double.parseDouble(request.getParameter("productPrice")))
                .build();

        productService.create(product);
        response.sendRedirect("/products");
    }
}
