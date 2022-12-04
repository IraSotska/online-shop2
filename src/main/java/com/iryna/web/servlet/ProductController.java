package com.iryna.web.servlet;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import com.iryna.web.template.PageGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private PageGenerator pageGenerator;
    private ProductService productService;

    @GetMapping("/add")
    public void doGet(HttpServletResponse response) throws IOException {
        response.getWriter().println(pageGenerator.generatePage("add_product.html", Map.of()));
    }

    @PostMapping("/add")
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


    @GetMapping
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("Hello product");

        var searchedWord = request.getParameter("searchedWord");

        log.info("Request to get all with searched word: {}", searchedWord);

        Map<String, Object> templateData = Map.of("products", productService.getAll(searchedWord));

        response.getWriter().println(pageGenerator.generatePage("product_list.html", templateData));
    }

    @PostMapping("/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var id = Long.parseLong(request.getParameter("id"));
        productService.deleteById(id);

        log.info("Requested to delete product by id: {}", id);

        response.sendRedirect("/products");
    }

    @GetMapping("/edit")
    public void getEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var product = productService.findById(Long.parseLong(request.getParameter("id")));

        if (product.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.getWriter().println(pageGenerator.generatePage("edit_product.html", Map.of("product", product.get())));
    }

    @PostMapping("/edit")
    public void postEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
