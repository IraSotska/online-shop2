package com.iryna.web.template;

import com.iryna.entity.Product;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageGeneratorTest {

    @Test
    void generatePage() {

        var expectedHtml = "<!DOCTYPE html>\r\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" +
                "<body>\r\n" +
                "<div class=\"container\">\r\n" +
                "    <form action=\"/products/add\" method=\"POST\">\r\n" +
                "        Product name: <input type=\"text\" name=\"productName\"/>\r\n" +
                "        Product price: <input type=\"text\" name=\"productPrice\"/>\r\n" +
                "        <br>\r\n" +
                "        <input type=\"submit\" value=\"Send\">\r\n" +
                "    </form>\r\n" +
                "</div>\r\n" +
                "</body>\r\n" +
                "</html>";

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("products", List.of(Product.builder()
                .name("name1")
                .price(1D)
                .id(2L)
                .creationDate(new Timestamp(System.currentTimeMillis())).build()));
        var pageGenerator = new PageGenerator();
        assertEquals(expectedHtml, pageGenerator.generatePage("test_page.html", templateData));
    }
}