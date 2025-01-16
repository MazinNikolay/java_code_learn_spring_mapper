package com.example.java_code_learn_oject_mapper.service;

import com.example.java_code_learn_oject_mapper.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    String getProductById(Long id);

    String createProduct(Product product);

    String updateProduct(Long id, Product productDetails);

    void deleteProduct(Long id);
}
