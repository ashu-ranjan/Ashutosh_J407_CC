package com.hexaware.entity;

public abstract class Product {
    protected int productId;
    protected String productName;
    protected String description;
    protected double price;
    protected int quantityInStock;
    protected String type;

    public Product(int productId, String productName, String description, double price, int quantityInStock, String type) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.type = type;
    }

    // Getters and Setters

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public String getType() {
        return type;
    }

    // Unified toString() for getAllProducts
    @Override
    public String toString() {
        return String.format("| %-10d | %-20s | %-30s | %-10.2f | %-13d | %-12s | %-15s | %-14s | %-10s | %-10s |",
                productId, productName, description, price, quantityInStock, type,
                getBrandOrDefault(), getWarrantyOrDefault(), getSizeOrDefault(), getColorOrDefault());
    }

    // Abstract method for detailed view
    public abstract String toDetailedString();

    // Default fillers
    public String getBrandOrDefault() { return "--"; }
    public String getWarrantyOrDefault() { return "--"; }
    public String getSizeOrDefault() { return "--"; }
    public String getColorOrDefault() { return "--"; }
}
