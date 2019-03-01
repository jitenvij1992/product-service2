package com.jpop.productservice.service;

import com.jpop.productservice.model.Product;

import java.util.List;

public interface ProductDetailService {

    Product getProductDetails(long id);

    List<Product> getAvailableProducts();
}
