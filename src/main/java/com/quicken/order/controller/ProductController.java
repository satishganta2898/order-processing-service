package com.quicken.order.controller;

import com.quicken.order.dto.ProductRequest;
import com.quicken.order.dto.ProductResponse;
import com.quicken.order.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /** Endpoint to create new product **/
    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse addProduct(@RequestBody ProductRequest product) {
        log.info("Request received to add productDetails {}", product);
        return productService.addProduct(product);
    }

}