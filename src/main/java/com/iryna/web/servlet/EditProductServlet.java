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
import java.util.Map;

@Slf4j
public class EditProductServlet extends HttpServlet {

    private PageGenerator pageGenerator = ApplicationContext.getService(PageGenerator.class);
    private ProductService productService = ApplicationContext.getService(ProductService.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");

        var product = productService.findById(Long.parseLong(request.getParameter("id")));

        if (product.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.getWriter().println(pageGenerator.generatePage("edit_product.html", Map.of("product", product.get())));
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
