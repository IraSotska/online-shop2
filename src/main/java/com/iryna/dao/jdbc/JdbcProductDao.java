package com.iryna.dao.jdbc;

import com.iryna.dao.ProductDao;
import com.iryna.dao.jdbc.mapper.ProductRowMapper;
import com.iryna.entity.Product;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
public class JdbcProductDao implements ProductDao {

    private ProductRowMapper productRowMapper;
    private DataSourceHolder dataSourceHolder;

    private static final String FIND_ALL_WITH_SEARCH_QUERY = "SELECT id, name, price, creation_date, description FROM products WHERE name LIKE ? OR description LIKE ?;";
    private static final String CREATE_PRODUCT_QUERY = "INSERT INTO products(name, price, creation_date, description) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name = ?, price = ?, description = ? WHERE id = ?";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT name, price, creation_date, description FROM products WHERE id = ?;";

    public List<Product> findAll(String searchedWord) {
        List<Product> result = new ArrayList<>();
        try (var connection = dataSourceHolder.getPGSimpleDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_WITH_SEARCH_QUERY)) {
            var searchTerm = "%" + searchedWord + "%";
            preparedStatement.setString(1, searchTerm);
            preparedStatement.setString(2, searchTerm);
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(productRowMapper.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception while find all query with searched word: {}." + searchedWord, e);
        }
        return result;
    }

    public void create(Product product) {
        try (var connection = dataSourceHolder.getPGSimpleDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(CREATE_PRODUCT_QUERY)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception while create query.", e);
        }
    }

    public void update(Product product) {
        try (var connection = dataSourceHolder.getPGSimpleDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception while update query.", e);
        }
    }

    public void delete(Long id) {
        try (var connection = dataSourceHolder.getPGSimpleDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception while delete query.", e);
        }
    }

    public Optional<Product> findById(Long id) {
        try (var connection = dataSourceHolder.getPGSimpleDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                Product product = null;
                while (resultSet.next()) {
                    product = Product.builder()
                            .id(id)
                            .name(resultSet.getString("name"))
                            .price(resultSet.getDouble("price"))
                            .creationDate(resultSet.getTimestamp("creation_date").toLocalDateTime())
                            .description(resultSet.getString("description"))
                            .build();
                }
                return Optional.ofNullable(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception while find by id query.", e);
        }
    }
}
