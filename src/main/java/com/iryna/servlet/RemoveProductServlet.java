package com.iryna.servlet;

import com.iryna.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class RemoveProductServlet extends HttpServlet {

    private final ProductService productService;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        productService.deleteById(Long.parseLong(request.getParameter("id")));

        response.sendRedirect("/products");
    }
}
