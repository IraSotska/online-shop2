package com.iryna.web.servlet;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import com.iryna.service.ServiceLocator;
import com.iryna.web.template.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class EditProductServlet extends HttpServlet {

    private PageGenerator pageGenerator = ServiceLocator.getService(PageGenerator.class);
    private ProductService productService = ServiceLocator.getService(ProductService.class);

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
                .description(request.getParameter("description"))
                .build();

        log.info("Requested to edit product: {}", product);

        productService.update(product);
        response.sendRedirect("/products");
    }
}
