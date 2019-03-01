package com.jpop.productservice.controller;

import com.google.gson.Gson;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.model.dto.ProductDTO;
import com.jpop.productservice.service.ProductDeleteService;
import com.jpop.productservice.service.ProductDetailService;
import com.jpop.productservice.service.ProductInsertService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceController.class);
    private ProductInsertService productInsertService;
    private ProductDetailService productDetailService;
    private ProductDeleteService productDeleteService;

    @Autowired
    public ProductServiceController(ProductInsertService productInsertService, ProductDetailService productDetailService, ProductDeleteService productDeleteService) {
        this.productInsertService = productInsertService;
        this.productDetailService = productDetailService;
        this.productDeleteService = productDeleteService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productDetailService.getAvailableProducts());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> insertProduct(@RequestBody ProductDTO payload) {

        logger.info("Payload received to insert data in product service with value {}", payload);
        ModelMapper modelMapper = new ModelMapper();
        Product productMap = modelMapper.map(payload, Product.class);
        Product savedProduct = productInsertService.processRawData(productMap);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(savedProduct.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(savedProduct);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProductDetails(@PathVariable long id) {

        logger.info("Received request to get details of product having id {}", id);
        String productData = new Gson().toJson(productDetailService.getProductDetails(id));
        return ResponseEntity.ok(productData);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteProductById(@PathVariable long id) {
        logger.info("Request received to delete product having id {}", id);
        productDeleteService.deleteProduct(id);
        return ResponseEntity.ok("Successfully deleted data");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProduct(@RequestBody ProductDTO payload, @PathVariable long id) {
        logger.info("Received request to update the product having product id {} and payload {}", id, payload);
        ModelMapper modelMapper = new ModelMapper();
        Product productMap = modelMapper.map(payload, Product.class);
        productInsertService.processUpdatedData(productMap, id);
        return ResponseEntity.ok("Updated data successfully ");
    }
}
