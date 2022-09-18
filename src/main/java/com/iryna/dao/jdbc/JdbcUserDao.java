package com.iryna.dao.jdbc;

import com.iryna.dao.UserDao;
import com.iryna.entity.User;
import com.iryna.entity.Role;
import lombok.RequiredArgsConstructor;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.SQLException;

@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {

    private static final String FIND_BY_LOGIN_QUERY = "SELECT login, role, encrypted_password, salt FROM users WHERE login = ?;";

    private final PGSimpleDataSource pgSimpleDataSource;

    public User findByLogin(String login) {
        try (var connection = pgSimpleDataSource.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_QUERY)) {
            preparedStatement.setString(1, login);
            try (var resultSet = preparedStatement.executeQuery()) {
                User user = null;
                while (resultSet.next()) {
                    user = User.builder()
                            .encryptedPassword(resultSet.getString("encrypted_password"))
                            .login(resultSet.getString("login"))
                            .role(Role.valueOf(resultSet.getString("role").toUpperCase()))
                            .salt(resultSet.getString("salt"))
                            .build();
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception while find by login query.", e);
        }
    }
}
