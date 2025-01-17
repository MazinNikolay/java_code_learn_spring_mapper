package com.example.java_code_learn_oject_mapper.controller;

import com.example.java_code_learn_oject_mapper.mapper.ProductMapperService;
import com.example.java_code_learn_oject_mapper.model.Product;
import com.example.java_code_learn_oject_mapper.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<String> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ProductMapperService.convertProductsToJson(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody String product) {
        String json = productService.createProduct(ProductMapperService.convertJsonToProduct(product));
        return ResponseEntity.ok().body(json);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @RequestBody String productDetails) {
        String json = productService.updateProduct(id,
                ProductMapperService.convertJsonToProduct(productDetails));
        return ResponseEntity.ok().body(json);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
