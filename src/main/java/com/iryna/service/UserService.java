package com.iryna.service;

import com.iryna.dao.UserDao;
import com.iryna.entity.Product;
import com.iryna.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDao userDao;

    private static final Map<String, List<Product>> cartList = new ConcurrentHashMap<>();

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public void addToCart(Product product, String login) {

        log.info("Add product: {} to cart by user: {}", product, login);

        if (cartList.containsKey(login)) {
            cartList.get(login).add(product);
        } else {
            var cart = new ArrayList<Product>();
            cart.add(product);
            cartList.put(login, cart);
        }
    }

    public List<Product> getCart(String login) {
        return cartList.get(login);
    }
}
