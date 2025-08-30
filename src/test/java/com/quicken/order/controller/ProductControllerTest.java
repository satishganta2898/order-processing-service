package com.quicken.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quicken.order.dto.ProductRequest;
import com.quicken.order.dto.ProductResponse;
import com.quicken.order.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {

    private MockMvc mockMvc;
    private ProductService productService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        productService = Mockito.mock(ProductService.class);
        ProductController productController = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createOrderTest() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Rose");
        request.setPrice(20.0);
        ProductResponse response = new ProductResponse(10L, "Rose", 20.0);

        Mockito.when(productService.addProduct(any(ProductRequest.class))).thenReturn(response);
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rose"))
                .andExpect(jsonPath("$.price").value(20.0))
                .andExpect(jsonPath("$.id").value(10L));
    }
}
