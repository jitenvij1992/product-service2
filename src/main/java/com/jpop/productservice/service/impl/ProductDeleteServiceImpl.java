package com.jpop.productservice.service.impl;

import com.jpop.productservice.dao.ProductDeleteRepository;
import com.jpop.productservice.exception.ProductNotFoundException;
import com.jpop.productservice.service.ProductDeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDeleteServiceImpl implements ProductDeleteService {

    private static final Logger logger = LoggerFactory.getLogger(ProductDetailServiceImpl.class);

    @Autowired
    private ProductDeleteRepository productDeleteRepository;

    @Override
    public void deleteProduct(long id) {
        logger.info("Servicing request to delete product having id {}", id);
        productDeleteRepository.findById(id)
                .ifPresentOrElse(product -> productDeleteRepository.deleteById(id),
                        () -> {
                            throw new ProductNotFoundException("");
                        });
    }
}