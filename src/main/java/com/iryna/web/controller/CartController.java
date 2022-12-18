package com.iryna.web.controller;

import com.iryna.entity.Product;
import com.iryna.security.entity.Session;
import com.iryna.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final UserService userService;

    @GetMapping
    public String getCart(@RequestAttribute Session session, Model model) {
        model.addAttribute("products", session == null ? List.of() : session.getCart());
        return "cart";
    }

    @SneakyThrows
    @PostMapping
    public String addToCart(@RequestAttribute Session session, @ModelAttribute Product product) {
        log.info("Requested to add product with id: {} to cart", product);

        if (session != null) {
            userService.addToCart(product, session.getUser().getLogin());
        }
        return "redirect:products";
    }
}
