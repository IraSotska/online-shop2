package com.iryna.web.servlet;

import com.iryna.entity.Product;
import com.iryna.security.entity.Session;
import com.iryna.service.UserService;
import com.iryna.web.template.PageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final PageGenerator pageGenerator;
    private final UserService userService;

    @GetMapping
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var session = (Session) request.getAttribute("session");
        Map<String, Object> templateData = session == null ? Map.of() : Map.of("products", session.getCart());

        response.getWriter().println(pageGenerator.generatePage("cart.html", templateData));
    }

    @SneakyThrows
    @PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        var product = Product.builder()
                .id(Long.parseLong(request.getParameter("id")))
                .price(Double.parseDouble(request.getParameter("price")))
                .description(request.getParameter("description"))
                .name(String.valueOf(request.getParameter("name")))
                .creationDate(LocalDateTime.parse(request.getParameter("creationDate"), DATE_FORMAT))
                .build();

        log.info("Requested to add product with id: {} to cart", product);

        var session = (Session) request.getAttribute("session");

        if (session != null) {
            userService.addToCart(product, session.getUser().getLogin());
        }

        response.sendRedirect("/products");
    }
}
