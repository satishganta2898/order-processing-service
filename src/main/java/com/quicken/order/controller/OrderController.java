package com.quicken.order.controller;

import com.quicken.order.dto.OrderRequest;
import com.quicken.order.dto.OrderResponse;
import com.quicken.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Request received for create order: {}", orderRequest);
        return orderService.createOrder(orderRequest);
    }

    @GetMapping(value = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse getOrderById(@PathVariable Long orderId) {
        log.info("Request received for get order details: {}", orderId);
        return orderService.getOrderById(orderId);
    }
}
