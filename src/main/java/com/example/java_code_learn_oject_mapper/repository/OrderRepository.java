package com.example.java_code_learn_oject_mapper.repository;

import com.example.java_code_learn_oject_mapper.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
