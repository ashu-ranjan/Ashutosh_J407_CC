package com.hexaware.util;

import javax.crypto.spec.PSource;

public class AJ407Constants {

    public static final String VIEW_PRODUCT_BY_ID = "SELECT COUNT(*) FROM Product WHERE productId = ?";
    public static final String INSERT_INTO_PRODUCT = "INSERT INTO Product (productId, productName, description, price, quantityInStock, type, brand, warrantyPeriod, size, color) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_PRODUCTS = "SELECT * FROM Product";
    public static final String GET_PRODUCT_BY_ID = "SELECT * FROM Product WHERE productId = ?";


}
