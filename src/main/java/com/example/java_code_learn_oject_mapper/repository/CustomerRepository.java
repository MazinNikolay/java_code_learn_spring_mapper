package com.example.java_code_learn_oject_mapper.repository;

import com.example.java_code_learn_oject_mapper.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
