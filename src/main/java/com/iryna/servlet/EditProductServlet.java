package com.iryna.servlet;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import com.iryna.template.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class EditProductServlet extends HttpServlet {

    private final PageGenerator pageGenerator;
    private final ProductService productService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().println(pageGenerator.generatePage("edit_product.html",
                Map.of("product", productService.findById(Long.parseLong(request.getParameter("id"))))));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var product = Product.builder()
                .name(request.getParameter("name"))
                .id(Long.parseLong(request.getParameter("id")))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();

        productService.update(product);
        response.sendRedirect("/products");
    }
}
