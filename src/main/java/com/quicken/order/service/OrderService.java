package com.quicken.order.service;

import com.quicken.order.dto.OrderRequest;
import com.quicken.order.dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long id);
}
