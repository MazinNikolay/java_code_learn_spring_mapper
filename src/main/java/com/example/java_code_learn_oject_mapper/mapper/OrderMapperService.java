package com.example.java_code_learn_oject_mapper.mapper;

import com.example.java_code_learn_oject_mapper.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderMapperService {
    @Autowired
    private static ObjectMapper objectMapper;

    public static String convertOrderToJson(Order order) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Order convertJsonToOrder(String json) {
        Order order = new Order();
        try{
            order = objectMapper.readValue(json, Order.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return order;
    }

    public static List<String> convertOrdersToJson(List<Order> orders) {
        List<String> jsons = new ArrayList<>();
        orders.forEach(order -> {
            try {
                jsons.add(objectMapper.writeValueAsString(order));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return jsons;
    }
}
