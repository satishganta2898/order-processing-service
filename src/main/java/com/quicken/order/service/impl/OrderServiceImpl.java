package com.quicken.order.service.impl;

import com.quicken.order.dto.OrderRequest;
import com.quicken.order.dto.OrderResponse;
import com.quicken.order.entity.Order;
import com.quicken.order.entity.OrderStatus;
import com.quicken.order.exception.ResourceNotFoundException;
import com.quicken.order.repository.OrderRepository;
import com.quicken.order.service.AsyncOrderProcessor;
import com.quicken.order.service.OrderService;
import com.quicken.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AsyncOrderProcessor asyncOrderProcessor;
    private final ProductService productService;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {

        productService.getProductById(orderRequest.getProductId());
        Order order = Order.builder().productId(orderRequest.getProductId())
                .status(OrderStatus.PENDING).build();
        final Order savedOrder = orderRepository.save(order);
        log.info("Order created with orderId: {}", order.getId());

        asyncOrderProcessor.processOrder(order);
        order = orderRepository.findById(order.getId()).orElseThrow(() -> new ResourceNotFoundException("Order not found " + savedOrder.getId()));
        return OrderResponse.fromEntity(order);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        log.info("Fetching order with orderId: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found " + id));
        return OrderResponse.fromEntity(order);
    }
}
