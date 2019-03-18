package com.jpop.productservice.service.impl;

import com.jpop.productservice.dao.ProductInsertRepository;
import com.jpop.productservice.exception.ProductValidationException;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductInsertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ProductInsertServiceImpl implements ProductInsertService {

    private static final Logger logger = LoggerFactory.getLogger(ProductInsertServiceImpl.class);

    @Autowired
    private ProductInsertRepository productInsertRepository;

    @Override
    public Product processRawData(Product product) {
        logger.info("Validating product data having values {}", product);

        if (validateRawData(product)) {
            logger.info("Product is not valid!!");
            throw new ProductValidationException("Product item is not valid with value: "+ product);
        }
        logger.info("Validation completed for the product.");
        return productInsertRepository.save(product);
    }

    @Override
    public void processUpdatedData(Product product, long id) {
        if(!productInsertRepository.findById(id).isPresent())
            throw new ProductValidationException("No Product found having id: " +id);
        product.setId(id);
        processRawData(product);
    }

    private boolean validateRawData(Product product) {
        return Stream.of(product.getName(), product.getPrice(), product.getDescription()).anyMatch(Objects::isNull);
    }
}
