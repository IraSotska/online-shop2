package com.iryna.db;

import com.iryna.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    void create(Product product);

    void update(Product product);

    void delete(Long id);

    Product findById(Long id);
}
