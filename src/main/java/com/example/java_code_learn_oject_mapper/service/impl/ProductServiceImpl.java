package com.example.java_code_learn_oject_mapper.service.impl;

import com.example.java_code_learn_oject_mapper.mapper.ProductMapperService;
import com.example.java_code_learn_oject_mapper.model.Product;
import com.example.java_code_learn_oject_mapper.repository.ProductRepository;
import com.example.java_code_learn_oject_mapper.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        logger.info("was invoked get all products method in service");
        return productRepository.findAll();
    }

    @Override
    public String getProductById(Long id) {
        logger.info("was invoked get product by id method in service");
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return ProductMapperService.convertProductToJson(product);
    }

    @Override
    public String createProduct(Product product) {
        logger.info("was invoked create products method in service");
        return ProductMapperService.convertProductToJson(productRepository.save(product));
    }

    @Override
    public String updateProduct(Long id, Product productDetails) {
        logger.info("was invoked update products method in service");
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantityInStock(productDetails.getQuantityInStock());
        return ProductMapperService.convertProductToJson(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        logger.info("was invoked delete products method in service");
        productRepository.deleteById(id);
    }
}
