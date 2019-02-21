package com.epam.productservice.service.impl;

import com.epam.productservice.dao.ProductInsertRepository;
import com.epam.productservice.exception.ProductValidationException;
import com.epam.productservice.model.Product;
import com.epam.productservice.service.ProductInsertService;
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
    public void processRawData(Product product) {
        logger.info("Validating product data having values {}", product);

        if (validateRawData(product)) {
            logger.info("Product is not valid!!");
            throw new ProductValidationException("Product item is not valid with value: "+ product);
        }
        logger.info("Validation completed for the product.");
        productInsertRepository.save(product);
    }

    private boolean validateRawData(Product product) {
        return Stream.of(product.getName(), product.getPrice(), product.getDescription()).anyMatch(Objects::isNull);
    }
}
