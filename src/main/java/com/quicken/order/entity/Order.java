package com.quicken.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Foreign key to product entity **/
    private Long productId;
    /** Enum for track order status lifeCycle **/
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
