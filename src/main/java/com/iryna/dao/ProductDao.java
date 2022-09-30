package com.iryna.dao;

import com.iryna.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<Product> findAll(String searchedWord);

    void create(Product product);

    void update(Product product);

    void delete(Long id);

    Optional<Product> findById(Long id);
}
