package com.iryna.web.controller;

import com.iryna.entity.Product;
import com.iryna.security.entity.Session;
import com.iryna.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final UserService userService;

    @GetMapping
    public String getCart(HttpServletRequest request, Model model) {
        var session = (Session) request.getAttribute("session");
        model.addAttribute("products", session == null ? List.of() : session.getCart());

        return "cart";
    }

    @SneakyThrows
    @PostMapping
    public String addToCart(HttpServletRequest request, @ModelAttribute Product product) {
        log.info("Requested to add product with id: {} to cart", product);

        var session = (Session) request.getAttribute("session");

        if (session != null) {
            userService.addToCart(product, session.getUser().getLogin());
        }
        return "redirect:products";
    }
}
