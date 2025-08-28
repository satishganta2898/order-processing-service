package com.quicken.order.service;

import com.quicken.order.dto.ProductRequest;
import com.quicken.order.dto.ProductResponse;
import com.quicken.order.entity.Product;

public interface ProductService {

    ProductResponse addProduct(ProductRequest productRequest);

    Product getProductById(Long id);
}
