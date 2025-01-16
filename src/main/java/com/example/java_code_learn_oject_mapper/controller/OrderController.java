package com.example.java_code_learn_oject_mapper.controller;

import com.example.java_code_learn_oject_mapper.mapper.OrderMapperService;
import com.example.java_code_learn_oject_mapper.model.Order;
import com.example.java_code_learn_oject_mapper.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<String> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return OrderMapperService.convertOrdersToJson(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody String order) {
        String json = orderService.createOrder(OrderMapperService.convertJsonToOrder(order));
        return ResponseEntity.ok().body(json);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
