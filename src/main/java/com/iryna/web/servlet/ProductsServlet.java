package com.iryna.web.servlet;

import com.iryna.service.ProductService;
import com.iryna.ioc.ApplicationContextListener;
import com.iryna.web.template.PageGenerator;
import com.study.ioc.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class ProductsServlet extends HttpServlet {

    private PageGenerator pageGenerator;
    private ProductService productService;

    @Override
    public void init(ServletConfig config) {
        var context = (ApplicationContext) config.getServletContext().getAttribute(ApplicationContextListener.APPLICATION_CONTEXT);
        pageGenerator = context.getBean(PageGenerator.class);
        productService = context.getBean(ProductService.class);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        var searchedWord = request.getParameter("searchedWord");

        log.info("Request to get all with searched word: {}", searchedWord);

        Map<String, Object> templateData = Map.of("products", productService.getAll(searchedWord));

        response.getWriter().println(pageGenerator.generatePage("product_list.html", templateData));
    }
}
