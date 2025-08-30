package com.quicken.order.controller;

import com.quicken.order.dto.OrderRequest;
import com.quicken.order.dto.OrderResponse;
import com.quicken.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    /** Endpoint to create new order **/
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Request received for create order: {}", orderRequest);
        return orderService.createOrder(orderRequest);
    }

    /** Endpoint to get order status by ID **/
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse getOrderStatus(@PathVariable Long id) {
        log.info("Request received for get order status: {}", id);
        return orderService.getOrderStatus(id);
    }
}
