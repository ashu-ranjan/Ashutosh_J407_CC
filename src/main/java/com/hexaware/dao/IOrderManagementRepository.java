package com.hexaware.dao;

import com.hexaware.entity.Product;
import java.util.List;

public interface IOrderManagementRepository {
    boolean createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(int productId);
}


