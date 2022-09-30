package com.iryna.service;

import com.iryna.dao.UserDao;
import com.iryna.entity.Product;
import com.iryna.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final Map<String, List<Product>> cartList = Collections.synchronizedMap(new HashMap<>());

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public void addToCart(Product product, String login) {

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
