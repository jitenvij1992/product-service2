package com.jpop.productservice.service.impl;

import com.jpop.productservice.dao.ProductDetailRepository;
import com.jpop.productservice.exception.ProductNotFoundException;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    private static final Logger logger = LoggerFactory.getLogger(ProductDetailServiceImpl.class);

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public Product getProductDetails(long id) {

        logger.info("Inside Product detail. Getting details for the product having id {}", id);
        return productDetailRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product id is not valid"));
    }

    @Override
    public List<Product> getAvailableProducts() {
       return (List<Product>) productDetailRepository.findAll();
    }
}
