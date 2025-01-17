package com.example.java_code_learn_oject_mapper.controller;

import com.example.java_code_learn_oject_mapper.mapper.ProductMapperService;
import com.example.java_code_learn_oject_mapper.model.Product;
import com.example.java_code_learn_oject_mapper.service.impl.ProductServiceImpl;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductServiceImpl productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = Product.builder()
                .productId(1L)
                .name("product1")
                .price(141.0)
                .build();

        product2 = Product.builder()
                .productId(2L)
                .name("product2")
                .price(142.0)
                .build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = new ArrayList<>(List.of(product1, product2));
        List<String> jsons = new ArrayList<>(List.of(objectMapper.writeValueAsString(product1),
                objectMapper.writeValueAsString(product2)));

        Mockito.when(productService.getAllProducts()).thenReturn(products);
        try (MockedStatic<ProductMapperService> mockedMapper = Mockito.mockStatic(ProductMapperService.class)) {
            mockedMapper.when(() -> ProductMapperService.convertProductsToJson(products)).thenReturn(jsons);

            mockMvc.perform(MockMvcRequestBuilders.get("/products")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(jsons)));
            verify(productService, times(1)).getAllProducts();
            mockedMapper.verify(() -> ProductMapperService.convertProductsToJson(products),
                    times(1));
        }
    }

    @Test
    public void testGetProductById() throws Exception {
        String json = objectMapper.writeValueAsString(product1);
        Mockito.when(productService.getProductById(1L)).thenReturn(json);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value("product1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value(141.0));
    }

    @Test
    public void testCreateProduct() throws Exception {
        String json = objectMapper.writeValueAsString(product1);
        Mockito.when(productService.createProduct(Mockito.any(Product.class))).thenReturn(json);
        try (MockedStatic<ProductMapperService> mockedMapper = Mockito.mockStatic(ProductMapperService.class)) {
            mockedMapper.when(() -> ProductMapperService.convertJsonToProduct(anyString()))
                    .thenReturn(product1);

            mockMvc.perform(MockMvcRequestBuilders.post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                            .value("product1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                            .value(141.0));
        }
    }

    @Test
    public void testUpdateProduct() throws Exception {
        String json = objectMapper.writeValueAsString(product1);
        Mockito.when(productService.updateProduct(anyLong(), Mockito.any(Product.class))).thenReturn(json);
        try (MockedStatic<ProductMapperService> mockedMapper = Mockito.mockStatic(ProductMapperService.class)) {
            mockedMapper.when(() -> ProductMapperService.convertJsonToProduct(anyString()))
                    .thenReturn(product1);

            mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                            .value("product1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                            .value(141.0));
        }
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }
}