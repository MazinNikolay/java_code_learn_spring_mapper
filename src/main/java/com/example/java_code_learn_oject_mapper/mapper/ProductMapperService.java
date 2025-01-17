package com.example.java_code_learn_oject_mapper.mapper;

import com.example.java_code_learn_oject_mapper.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class ProductMapperService {
    private static ObjectMapper objectMapper;

    static{
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String convertProductToJson(Product product) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Product convertJsonToProduct(String json) {
        Product product = new Product();
        try {
            product = objectMapper.readValue(json, Product.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static List<String> convertProductsToJson(List<Product> products) {
        List<String> jsons = new ArrayList<>();
        products.forEach(product -> {
            try {
                jsons.add(objectMapper.writeValueAsString(product));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return jsons;
    }
}
