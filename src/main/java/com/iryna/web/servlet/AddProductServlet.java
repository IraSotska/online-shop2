package com.iryna.web.servlet;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import com.iryna.ioc.ApplicationContext;
import com.iryna.web.template.PageGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
public class AddProductServlet extends HttpServlet {

    private PageGenerator pageGenerator = ApplicationContext.getService(PageGenerator.class);
    private ProductService productService = ApplicationContext.getService(ProductService.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(pageGenerator.generatePage("add_product.html", Map.of()));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var product = Product.builder()
                .creationDate(LocalDateTime.now())
                .name(request.getParameter("productName"))
                .price(Double.parseDouble(request.getParameter("productPrice")))
                .description(request.getParameter("description"))
                .build();

        log.info("Requested to create product: {}", product);

        productService.create(product);
        response.sendRedirect("/products");
    }
}
