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
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AsyncOrderProcessor asyncOrderProcessor;
    private final ProductService productService;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        if (orderRequest.getProductId() == null || orderRequest.getProductId() <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        /* validate if product exists; throws exception if not found **/
        productService.getProductById(orderRequest.getProductId());

        /* create order entity with initial PENDING status **/
        Order order = Order.builder().productId(orderRequest.getProductId())
                .status(OrderStatus.PENDING).build();

        final Order savedOrder = orderRepository.save(order);
        log.info("Order created with orderId: {}", order.getId());

        /* Trigger asynchronous processing **/
        asyncOrderProcessor.processOrder(savedOrder);
        return OrderResponse.fromEntity(savedOrder);
    }

    @Override
    public OrderResponse getOrderStatus(Long id) {
        log.info("Fetching order status with orderId: {}", id);
        /* Fetch latest order status from DB or throw if not found **/
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order doesn’t exist " + id));
        return OrderResponse.fromEntity(order);
    }
}
