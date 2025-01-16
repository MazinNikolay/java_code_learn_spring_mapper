package com.example.java_code_learn_oject_mapper.repository;

import com.example.java_code_learn_oject_mapper.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
