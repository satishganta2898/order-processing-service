package com.quicken.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quicken.order.dto.OrderRequest;
import com.quicken.order.dto.OrderResponse;
import com.quicken.order.entity.OrderStatus;
import com.quicken.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest {

    private MockMvc mockMvc;
    private OrderService orderService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        orderService = Mockito.mock(OrderService.class);
        OrderController orderController = new OrderController(orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createOrderTest() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId(1L);
        OrderResponse orderResponse = new OrderResponse(10L, OrderStatus.PENDING);

        Mockito.when(orderService.createOrder(any(OrderRequest.class))).thenReturn(orderResponse);
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest))).andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(10))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.PENDING.toString()));
    }

    @Test
    void getOrderTest() throws Exception {
        OrderResponse response = new OrderResponse(10L, OrderStatus.COMPLETED);
        Mockito.when(orderService.getOrderStatus(10L)).thenReturn(response);

        mockMvc.perform(get("/orders/10").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(10))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.COMPLETED.toString()));
    }

}
