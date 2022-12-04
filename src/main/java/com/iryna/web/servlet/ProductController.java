package com.iryna.web.servlet;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import com.iryna.web.template.PageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final PageGenerator pageGenerator;
    private final ProductService productService;

    @GetMapping("/add")
    public void doGet(HttpServletResponse response) throws IOException {
        response.getWriter().println(pageGenerator.generatePage("add_product.html", Map.of()));
    }

    @PostMapping("/add")
    public String doPost(@RequestParam String productName,
                         @RequestParam Double productPrice,
                         @RequestParam String description) {

        var product = Product.builder()
                .creationDate(LocalDateTime.now())
                .name(productName)
                .price(productPrice)
                .description(description)
                .build();

        log.info("Requested to create product: {}", product);

        productService.create(product);
        return "redirect:products";
    }


    @GetMapping
    public void get(@RequestParam(required = false) String searchedWord, HttpServletResponse response) throws IOException {
        log.info("Request to get all with searched word: {}", searchedWord);

        Map<String, Object> templateData = Map.of("products", productService.getAll(searchedWord));
        response.getWriter().println(pageGenerator.generatePage("product_list.html", templateData));
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long id) {
        log.info("Requested to delete product by id: {}", id);
        productService.deleteById(id);

        return "redirect:products";
    }

    @GetMapping("/edit")
    public void getEdit(@RequestParam Long id, HttpServletResponse response) throws IOException {
        var product = productService.findById(id);

        if (product.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.getWriter().println(pageGenerator.generatePage("edit_product.html", Map.of("product", product.get())));
    }

    @PostMapping("/edit")
    public String postEdit(@RequestParam String name,
                           @RequestParam String description,
                           @RequestParam Long id,
                           @RequestParam Double price) {

        var product = Product.builder()
                .name(name)
                .id(id)
                .price(price)
                .description(description)
                .build();

        log.info("Requested to edit product: {}", product);

        productService.update(product);
        return "redirect:products";
    }
}
