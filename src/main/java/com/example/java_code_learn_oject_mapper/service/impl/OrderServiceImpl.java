package com.example.java_code_learn_oject_mapper.service.impl;

import com.example.java_code_learn_oject_mapper.mapper.OrderMapperService;
import com.example.java_code_learn_oject_mapper.model.Order;
import com.example.java_code_learn_oject_mapper.repository.OrderRepository;
import com.example.java_code_learn_oject_mapper.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public String getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return OrderMapperService.convertOrderToJson(order);
    }

    @Override
    public String createOrder(Order order) {
        return OrderMapperService.convertOrderToJson(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
