package com.iryna.web.servlet;

import com.iryna.entity.Product;
import com.iryna.security.entity.Session;
import com.iryna.service.ServiceLocator;
import com.iryna.service.UserService;
import com.iryna.web.template.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

@Slf4j
public class CartServlet extends HttpServlet {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss aaa");
    private PageGenerator pageGenerator = ServiceLocator.getService(PageGenerator.class);
    private UserService userService = ServiceLocator.getService(UserService.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        var session = (Session) request.getAttribute("session");
        Map<String, Object> templateData = session == null ? Map.of() : Map.of("products", session.getCart());

        response.getWriter().println(pageGenerator.generatePage("cart.html", templateData));
    }

    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        var product = Product.builder()
                .id(Long.parseLong(request.getParameter("id")))
                .price(Double.parseDouble(request.getParameter("price")))
                .name(String.valueOf(request.getParameter("name")))
                .creationDate(new Timestamp(DATE_FORMAT.parse(request.getParameter("creationDate")).getTime()))
                .description(String.valueOf(request.getParameter("description")))
                .build();

        log.info("Requested to add product with id: {} to cart", product);

        var session = (Session) request.getAttribute("session");

        if (session != null) {
            userService.addToCart(product, session.getUser().getLogin());
        }

        response.sendRedirect("/products");
    }
}
