package com.iryna.dao;

import com.iryna.entity.User;

public interface UserDao {

    User findByLogin(String login);
}
