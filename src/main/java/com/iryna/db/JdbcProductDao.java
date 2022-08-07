package com.iryna.db;

import com.iryna.entity.Product;
import lombok.RequiredArgsConstructor;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JdbcProductDao implements ProductDao {

    private final PGSimpleDataSource pgSimpleDataSource;

    public List<Product> findAll() {
        try (var resultSet = pgSimpleDataSource.getConnection().createStatement().executeQuery(
                "SELECT * FROM products;")) {
            List<Product> res = new ArrayList<>();
            while (resultSet.next()) {
                res.add(mapRow(resultSet));
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(Product product) {
        try (var connection = pgSimpleDataSource.getConnection();
             var preparedStatement = connection.prepareStatement(
                     "INSERT INTO products(name, price, creation_date) VALUES (?, ?, ?);")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Product product) {
        try (var connection = pgSimpleDataSource.getConnection();
             var preparedStatement = connection.prepareStatement(
                     "UPDATE products SET name = ?, price = ?, creation_date = ? WHERE id = ?")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setDouble(4, product.getId());
            preparedStatement.execute();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void delete(Long id) {
        try (var connection = pgSimpleDataSource.getConnection();
             var preparedStatement = connection.prepareStatement("DELETE FROM products WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Product findById(Long id) {
        try (var connection = pgSimpleDataSource.getConnection();
             var preparedStatement = connection.prepareStatement(
                     "SELECT name, price, creation_date FROM products WHERE id = ?;")) {
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
            throw new RuntimeException(e);
        }
    }

    private Product mapRow(ResultSet resultSet) throws SQLException {

        return Product.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getDouble("price"))
                .creationDate(resultSet.getTimestamp("creation_date"))
                .build();
    }
}
