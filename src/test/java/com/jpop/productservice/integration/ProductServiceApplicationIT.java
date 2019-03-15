package com.jpop.productservice.integration;

import com.google.gson.Gson;
import com.jpop.productservice.ProductServiceApplication;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductDetailService;
import com.jpop.productservice.service.ProductReviewService;
import com.jpop.productservice.service.impl.ProductDetailServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceApplicationIT {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ProductDetailService productDetailService;
    @MockBean
    ProductReviewService productReviewService;

    Product product;

    @TestConfiguration
    static class ProductServiceApplicationITConfig {

        @Bean
        public ProductDetailService productDetailService() {
            return new ProductDetailServiceImpl();
        }
    }

    @Before
    public void setUp() {
        product = new Product(3, "Shirt", "Adidas", BigDecimal.valueOf(123.98));
    }
    @Test
    public void testGetProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(null, headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(createUrl("/api/v1/products/"), HttpMethod.GET, httpEntity, String.class);
        assertEquals("Status code is invalid", 200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testGetProductByID() {
        Product product = new Product(1, "Shirt", "Adidas", BigDecimal.valueOf(123.98));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(null, headers);
        Mockito.when(productReviewService.get(anyLong()))
                .thenReturn(new ResponseEntity(List.of(product), HttpStatus.OK));

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(createUrl("/api/v1/products/1"), HttpMethod.GET, httpEntity, String.class);
        assertEquals("Status code is invalid", 200, responseEntity.getStatusCode().value());
        assertNotNull("Data is null for the product ID", responseEntity.getBody());
    }

    @Test
    public void testInsertProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(new Gson().toJson(product), headers);
        ResponseEntity<Product> responseEntity = testRestTemplate.exchange(createUrl("/api/v1/products/"), HttpMethod.POST, httpEntity, Product.class);
        assertEquals("Status code is invalid", 201, responseEntity.getStatusCode().value());
        assertEquals("Name is not correct",product.getName(), responseEntity.getBody().getName());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product(2, "Jeans", "Adidas", BigDecimal.valueOf(123.98));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(new Gson().toJson(product), headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(createUrl("/api/v1/products/2"), HttpMethod.PUT, httpEntity, String.class);
        assertEquals("Status code is invalid", 200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testDeleteProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(createUrl("/api/v1/products/2"), HttpMethod.DELETE, httpEntity, String.class);
        assertEquals("Status code is invalid", 200, responseEntity.getStatusCode().value());
    }

    private String createUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
