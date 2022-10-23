package com.iryna.web.servlet;

import com.iryna.service.ProductService;
import com.iryna.service.ServiceLocator;
import com.iryna.web.template.PageGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class ProductsServlet extends HttpServlet {

    private PageGenerator pageGenerator = ServiceLocator.getService(PageGenerator.class);
    private ProductService productService = ServiceLocator.getService(ProductService.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        var searchedWord = request.getParameter("searchedWord");

        log.info("Request to get all with searched word: {}", searchedWord);

        Map<String, Object> templateData = Map.of("products", productService.getAll(searchedWord));

        response.getWriter().println(pageGenerator.generatePage("product_list.html", templateData));
    }
}
