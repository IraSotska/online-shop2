package com.iryna.dao.jdbc.mapper;

import com.iryna.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {

    private static final Long ID = 1L;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final Double PRICE = 1.11;
    private static final Timestamp CREATION_DATE = new Timestamp(System.currentTimeMillis());

    @Test
    @DisplayName("Should Map Row")
    void shouldMapRow() throws SQLException {
        var productRowMapper = new ProductRowMapper();
        var resultSet = mock(ResultSet.class);
        var expectedProduct = Product.builder()
                .id(ID)
                .description(DESCRIPTION)
                .name(NAME)
                .creationDate(CREATION_DATE)
                .price(PRICE)
                .build();

        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("description")).thenReturn(DESCRIPTION);
        when(resultSet.getDouble("price")).thenReturn(PRICE);
        when(resultSet.getTimestamp("creation_date")).thenReturn(CREATION_DATE);

        var product = productRowMapper.mapRow(resultSet);

        assertNotNull(product);
        assertEquals(expectedProduct, product);
    }
}