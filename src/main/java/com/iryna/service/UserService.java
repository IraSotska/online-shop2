package com.iryna.service;

import com.iryna.dao.UserDao;
import com.iryna.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
