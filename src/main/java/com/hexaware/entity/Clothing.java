package com.hexaware.entity;

import com.hexaware.util.TableFormatterUtil;

public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(int productId, String productName, String description, double price,
                    int quantityInStock, String type, String size, String color) {
        super(productId, productName, description, price, quantityInStock, type);
        this.size = size;
        this.color = color;
    }

    @Override
    public String getSizeOrDefault() {
        return size;
    }

    @Override
    public String getColorOrDefault() {
        return color;
    }

    @Override
    public String toDetailedString() {
        return String.format("| %-10d | %-20s | %-30s | %-10.2f | %-13d | %-12s | %-10s | %-10s |",
                productId, productName, description, price, quantityInStock, type, size, color);
    }
}



