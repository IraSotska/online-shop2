package com.iryna.service;

import com.iryna.dao.ProductDao;
import com.iryna.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<Product> getAll(String searchedWord) {
        log.info("Get all SEARCH {} ", searchedWord);

        return productDao.findAll(searchedWord == null ? "" : searchedWord);
    }

    public void deleteById(Long id) {
        productDao.delete(id);
    }

    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public void create(Product product) {
        productDao.create(product);
    }
}
