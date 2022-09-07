package com.iryna.dao.jdbc;

import com.iryna.dao.ProductDao;
import com.iryna.dao.jdbc.mapper.ProductRowMapper;
import com.iryna.entity.Product;
import lombok.RequiredArgsConstructor;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JdbcProductDao implements ProductDao {

    private static final ProductRowMapper productRowMapper = new ProductRowMapper();
    private static final String FIND_ALL_QUERY = "SELECT * FROM products;";
    private static final String CREATE_PRODUCT_QUERY = "INSERT INTO products(name, price, creation_date) VALUES (?, ?, ?);";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name = ?, price = ?, creation_date = ? WHERE id = ?";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT name, price, creation_date FROM products WHERE id = ?;";

    private final PGSimpleDataSource pgSimpleDataSource;

    public List<Product> findAll() {
        try (var resultSet = pgSimpleDataSource.getConnection().createStatement().executeQuery(FIND_ALL_QUERY)) {
            List<Product> res = new ArrayList<>();
            while (resultSet.next()) {
                res.add(productRowMapper.mapRow(resultSet));
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException("Exception while find all query.", e);
        }
    }

    public void create(Product product) {
        try (var connection = pgSimpleDataSource.getConnection();
             var preparedStatement = connection.prepareStatement(CREATE_PRODUCT_QUERY)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception while create query.", e);
        }
    }

    public void update(Product product) {
        try (var connection = pgSimpleDataSource.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setDouble(4, product.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception while update query.", e);
        }
    }

    public void delete(Long id) {
        try (var connection = pgSimpleDataSource.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception while delete query.", e);
        }
    }

    public Product findById(Long id) {
        try (var connection = pgSimpleDataSource.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                Product product = null;
                while (resultSet.next()) {
                    product = Product.builder()
                            .id(id)
                            .name(resultSet.getString("name"))
                            .price(resultSet.getDouble("price"))
                            .creationDate(resultSet.getTimestamp("creation_date"))
                            .build();
                }
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception while find by id query.", e);
        }
    }
}
