package com.iryna.dao;

import com.iryna.entity.Product;
import com.iryna.entity.User;

import java.util.List;

public interface UserDao {

    User findByLogin(String login);

    void addToCart(Product product, Long userId);

    List<Product> getCart(Long userId);
}
