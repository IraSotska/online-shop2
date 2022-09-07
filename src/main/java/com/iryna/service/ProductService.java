package com.iryna.service;

import com.iryna.dao.ProductDao;
import com.iryna.entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<Product> getAll(String searchedWord) {
        return productDao.findAll().stream()
                .filter(product -> product.getName().contains(searchedWord) || product.getDescription().contains(searchedWord))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        productDao.delete(id);
    }

    public Product findById(Long id) {
        return productDao.findById(id);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public void create(Product product) {
        productDao.create(product);
    }
}
