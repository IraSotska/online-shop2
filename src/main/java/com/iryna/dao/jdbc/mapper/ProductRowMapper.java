package com.iryna.dao.jdbc.mapper;

import com.iryna.entity.Product;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductRowMapper {

    public Product mapRow(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getDouble("price"))
                .creationDate(resultSet.getTimestamp("creation_date").toLocalDateTime())
                .description(resultSet.getString("description"))
                .build();
    }
}
