package com.example.java_code_learn_oject_mapper.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;

    @NotBlank(message = "Shipping address is mandatory")
    private String shippingAddress;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
