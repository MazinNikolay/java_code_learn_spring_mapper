package com.example.java_code_learn_oject_mapper.service;

import com.example.java_code_learn_oject_mapper.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    String getOrderById(Long id);

    String createOrder(Order order);

    void deleteOrder(Long id);
}
