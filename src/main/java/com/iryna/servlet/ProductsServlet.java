package com.iryna.servlet;

import com.iryna.service.ProductService;
import com.iryna.template.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ProductsServlet extends HttpServlet {

    private final PageGenerator pageGenerator;
    private final ProductService productService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("products", productService.getAll());

        response.getWriter().println(pageGenerator.generatePage("product_list.html", templateData));
    }
}
