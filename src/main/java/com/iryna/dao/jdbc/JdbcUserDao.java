package com.iryna.dao.jdbc;

import com.iryna.dao.UserDao;
import com.iryna.entity.User;
import com.iryna.security.entity.Role;
import lombok.Setter;

import java.sql.SQLException;

@Setter
public class JdbcUserDao implements UserDao {

    private static final String FIND_BY_LOGIN_QUERY = "SELECT login, role, encrypted_password, salt FROM users WHERE login = ?;";

    private DataSourceContainer dataSourceContainer;

    public User findByLogin(String login) {
        try (var connection = dataSourceContainer.getPGSimpleDataSource().getConnection();
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
