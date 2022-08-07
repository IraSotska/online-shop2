package com.iryna.service;

import com.iryna.db.ProductDao;
import com.iryna.entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<Product> getAll() {
        return productDao.findAll();
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
