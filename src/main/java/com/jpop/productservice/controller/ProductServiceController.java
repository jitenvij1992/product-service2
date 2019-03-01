package com.jpop.productservice.controller;

import com.google.gson.Gson;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.model.dto.ProductDTO;
import com.jpop.productservice.service.ProductDeleteService;
import com.jpop.productservice.service.ProductDetailService;
import com.jpop.productservice.service.ProductInsertService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Get all products", notes = "This will be used to get all the products in inventory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product found in inventory"),
            @ApiResponse(code = 404, message = "URL not found"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productDetailService.getAvailableProducts());
    }

    @ApiOperation(value = "Insert new products", notes = "This will be used to add products in inventory")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product added in inventory"),
            @ApiResponse(code = 404, message = "URL not found"),
            @ApiResponse(code = 400, message = "Bad request")
    })
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

    @ApiOperation(value = "Find product by ID", notes = "This will be used to find the product by using product ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product found in inventory"),
            @ApiResponse(code = 204, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "URL not found"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProductDetails(@ApiParam(value = "Numeric Product ID that needs to be find", required = true)@PathVariable long id) {

        logger.info("Received request to get details of product having id {}", id);
        String productData = new Gson().toJson(productDetailService.getProductDetails(id));
        return ResponseEntity.ok(productData);
    }

    @ApiOperation(value = "Delete product by ID", notes = "This will be used to delete the product by using product ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product deleted from inventory"),
            @ApiResponse(code = 204, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "URL not found"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteProductById(@ApiParam(value = "Numeric Product ID that need to be delete", required = true)@PathVariable long id) {
        logger.info("Request received to delete product having id {}", id);
        productDeleteService.deleteProduct(id);
        return ResponseEntity.ok("Successfully deleted data");
    }

    @ApiOperation(value = "Update product by ID", notes = "This will be used to update the product by using product ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product updated in inventory"),
            @ApiResponse(code = 204, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "URL not found"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProduct(@RequestBody ProductDTO payload, @PathVariable long id) {
        logger.info("Received request to update the product having product id {} and payload {}", id, payload);
        ModelMapper modelMapper = new ModelMapper();
        Product productMap = modelMapper.map(payload, Product.class);
        productInsertService.processUpdatedData(productMap, id);
        return ResponseEntity.ok("Updated data successfully ");
    }
}
