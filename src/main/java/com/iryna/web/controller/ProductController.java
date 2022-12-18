package com.iryna.web.controller;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/add")
    public String addProductPage() {
        return "add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        log.info("Requested to create product: {}", product);
        productService.create(product);
        return "redirect:";
    }

    @GetMapping
    public String getProductList(@RequestParam(required = false) String searchedWord, Model model) {
        log.info("Request to get all with searched word: {}", searchedWord);

        model.addAttribute("products", productService.getAll(searchedWord));
        return "product_list";
    }

    @PostMapping("/remove")
    public String removeProduct(@RequestParam Long id) {
        log.info("Requested to delete product by id: {}", id);
        productService.deleteById(id);
        return "redirect:";
    }

    @GetMapping("/edit")
    public String getEditProductPage(@RequestParam Long id, Model model) {
        var product = productService.findById(id);

        if (product.isEmpty()) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("product", product.get());
        return "edit_product";
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute Product product) {
        log.info("Requested to edit product: {}", product);
        productService.update(product);
        return "redirect:";
    }
}
