package com.iryna.web.servlet;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import com.iryna.service.ServiceLocator;
import com.iryna.web.template.PageGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

@Slf4j
public class AddProductServlet extends HttpServlet {

    private PageGenerator pageGenerator = ServiceLocator.getService(PageGenerator.class);
    private ProductService productService = ServiceLocator.getService(ProductService.class);

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
                .description(request.getParameter("description"))
                .build();

        log.info("Requested to create product: {}", product);

        productService.create(product);
        response.sendRedirect("/products");
    }
}
