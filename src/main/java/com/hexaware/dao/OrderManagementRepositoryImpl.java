package com.hexaware.dao;

import com.hexaware.entity.*;
import com.hexaware.exception.ProductAlreadyExistException;
import com.hexaware.exception.ProductNotExistException;
import com.hexaware.util.AJ407Constants;
import com.hexaware.util.DbConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManagementRepositoryImpl implements IOrderManagementRepository {

    private Connection conn;

    public OrderManagementRepositoryImpl() {
        try {
            conn = DbConnectionUtil.getDbConnection(); // One connection per instance
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean createProduct(Product product) {

        try (
                PreparedStatement checkStmt = conn.prepareStatement(AJ407Constants.VIEW_PRODUCT_BY_ID);
                PreparedStatement insertStmt = conn.prepareStatement(AJ407Constants.INSERT_INTO_PRODUCT)
        ) {
            checkStmt.setInt(1, product.getProductId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new ProductAlreadyExistException("Product with ID " + product.getProductId() + " already exists.");
            }

            insertStmt.setInt(1, product.getProductId());
            insertStmt.setString(2, product.getProductName());
            insertStmt.setString(3, product.getDescription());
            insertStmt.setDouble(4, product.getPrice());
            insertStmt.setInt(5, product.getQuantityInStock());
            insertStmt.setString(6, product.getType());

            if (product instanceof Electronics) {
                Electronics e = (Electronics) product;
                insertStmt.setString(7, e.getBrandOrDefault());
                insertStmt.setInt(8, Integer.parseInt(e.getWarrantyOrDefault()));
                insertStmt.setNull(9, Types.VARCHAR);
                insertStmt.setNull(10, Types.VARCHAR);
            } else if (product instanceof Clothing) {
                Clothing c = (Clothing) product;
                insertStmt.setNull(7, Types.VARCHAR);
                insertStmt.setNull(8, Types.INTEGER);
                insertStmt.setString(9, c.getSizeOrDefault());
                insertStmt.setString(10, c.getColorOrDefault());
            } else {
                insertStmt.setNull(7, Types.VARCHAR);
                insertStmt.setNull(8, Types.INTEGER);
                insertStmt.setNull(9, Types.VARCHAR);
                insertStmt.setNull(10, Types.VARCHAR);
            }

            return insertStmt.executeUpdate() > 0;

        } catch (SQLException | ProductAlreadyExistException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(AJ407Constants.GET_ALL_PRODUCTS)
        ) {
            while (rs.next()) {
                String type = rs.getString("type");
                products.add(createProductFromResultSet(rs, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product getProductById(int productId) {

        try (PreparedStatement pstmt = conn.prepareStatement(AJ407Constants.GET_PRODUCT_BY_ID)) {
            pstmt.setInt(1, productId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("type");
                    return createProductFromResultSet(rs, type);
                } else {
                    throw new ProductNotExistException("Product with ID " + productId + " not found.");
                }
            }

        } catch (SQLException | ProductNotExistException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Product createProductFromResultSet(ResultSet rs, String type) throws SQLException {
        if ("Electronics".equalsIgnoreCase(type)) {
            return new Electronics(
                    rs.getInt("productId"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantityInStock"),
                    type,
                    rs.getString("brand"),
                    rs.getInt("warrantyPeriod")
            );
        } else if ("Clothing".equalsIgnoreCase(type)) {
            return new Clothing(
                    rs.getInt("productId"),
                    rs.getString("productName"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantityInStock"),
                    type,
                    rs.getString("size"),
                    rs.getString("color")
            );
        } else {
            throw new SQLException("Unknown product type: " + type);
        }
    }


    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
