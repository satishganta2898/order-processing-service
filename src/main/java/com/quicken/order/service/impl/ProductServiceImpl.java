package com.quicken.order.service.impl;

import com.quicken.order.dto.ProductRequest;
import com.quicken.order.dto.ProductResponse;
import com.quicken.order.entity.Product;
import com.quicken.order.exception.ResourceNotFoundException;
import com.quicken.order.repository.ProductRepository;
import com.quicken.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    /* add the new product with given product name and price **/
    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        log.info("Adding new product with name {}", productRequest.getName());
        Product product = Product.builder().name(productRequest.getName()).price(productRequest.getPrice()).build();
        return ProductResponse.fromEntity(productRepository.save(product));
    }

    @Override
    public Product getProductById(Long id) {
        log.info("Fetching product by id {}", id);
        /* Fetch product from DB or throw if not found **/
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product doesn’t exist " + id));
    }

}
