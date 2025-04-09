package com.hexaware.entity;

import com.hexaware.util.TableFormatterUtil;

public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(int productId, String productName, String description, double price,
                       int quantityInStock, String type, String brand, int warrantyPeriod) {
        super(productId, productName, description, price, quantityInStock, type);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String getBrandOrDefault() {
        return brand;
    }

    @Override
    public String getWarrantyOrDefault() {
        return String.valueOf(warrantyPeriod);
    }

    @Override
    public String toDetailedString() {
        return String.format("| %-10d | %-20s | %-30s | %-10.2f | %-13d | %-12s | %-15s | %-14d |",
                productId, productName, description, price, quantityInStock, type, brand, warrantyPeriod);
    }
}
