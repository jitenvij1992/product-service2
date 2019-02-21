package com.jpop.productservice.controller;

import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductInsertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class ProductServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceController.class);

    private ProductInsertService productInsertService;

    @Autowired
    public ProductServiceController(ProductInsertService productInsertService) {
        this.productInsertService = productInsertService;
    }

    @PostMapping(value = "/insertProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertProduct(@RequestBody Product payload) {

        logger.info("Payload received to insert data in product service with value {}", payload);
        productInsertService.processRawData(payload);

        return ResponseEntity.ok("Successfully added product in repository");
    }
}
