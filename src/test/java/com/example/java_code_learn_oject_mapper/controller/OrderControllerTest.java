package com.example.java_code_learn_oject_mapper.controller;

import com.example.java_code_learn_oject_mapper.mapper.OrderMapperService;
import com.example.java_code_learn_oject_mapper.model.Order;
import com.example.java_code_learn_oject_mapper.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderServiceImpl orderService;

    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        order1 = Order.builder()
                .orderId(1L)
                .totalPrice(451.1)
                .shippingAddress("address1")
                .build();

        order2 = Order.builder()
                .orderId(2L)
                .totalPrice(452.2)
                .shippingAddress("address2")
                .build();
    }

    @Test
    void getAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>(List.of(order1, order2));
        List<String> jsons = new ArrayList<>(List.of(objectMapper.writeValueAsString(order1),
                objectMapper.writeValueAsString(order2)));

        Mockito.when(orderService.getAllOrders()).thenReturn(orders);
        try (MockedStatic<OrderMapperService> mockedMapper = Mockito.mockStatic(OrderMapperService.class)) {
            mockedMapper.when(() -> OrderMapperService.convertOrdersToJson(orders)).thenReturn(jsons);

            mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(jsons)));
            verify(orderService, times(1)).getAllOrders();
            mockedMapper.verify(() -> OrderMapperService.convertOrdersToJson(orders),
                    times(1));
        }
    }

    @Test
    void getOrderById() throws Exception {
        String json = objectMapper.writeValueAsString(order1);
        Mockito.when(orderService.getOrderById(1L)).thenReturn(json);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shippingAddress")
                        .value("address1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice")
                        .value(451.1));
    }

    @Test
    void createOrder() throws Exception {
        String json = objectMapper.writeValueAsString(order1);
        Mockito.when(orderService.createOrder(Mockito.any(Order.class))).thenReturn(json);
        try (MockedStatic<OrderMapperService> mockedMapper = Mockito.mockStatic(OrderMapperService.class)) {
            mockedMapper.when(() -> OrderMapperService.convertJsonToOrder(anyString()))
                    .thenReturn(order1);

            mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.shippingAddress")
                            .value("address1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice")
                            .value(451.1));
        }
    }

    @Test
    void deleteOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(1L);
    }
}