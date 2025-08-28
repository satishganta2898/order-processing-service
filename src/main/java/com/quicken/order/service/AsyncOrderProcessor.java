package com.quicken.order.service;

import com.quicken.order.entity.Order;
import com.quicken.order.entity.OrderStatus;
import com.quicken.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncOrderProcessor {

    private final OrderRepository orderRepository;
    private final Random random = new Random();

    @Async
    public void processOrder(Order order) {
        try {
            log.info("Processing orderRequest{}", order);
            order.setStatus(OrderStatus.PROCESSING);
            orderRepository.save(order);

            Thread.sleep(2000 + random.nextInt(1000));

            if (random.nextBoolean()) {
                order.setStatus(OrderStatus.COMPLETED);
            } else {
                order.setStatus(OrderStatus.FAILED);
            }
            orderRepository.save(order);
            log.info("OrderId successfully processed {} with status {}", order.getId(), order.getStatus());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            log.error("Error while processing order {} with exception {}", order.getId(), exception.getMessage());
        }

    }
}
