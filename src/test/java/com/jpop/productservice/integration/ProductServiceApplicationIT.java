package com.jpop.productservice.integration;

import com.google.gson.Gson;
import com.jpop.productservice.ProductServiceApplication;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductDetailService;
import com.jpop.productservice.service.impl.ProductDetailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceApplicationIT {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ProductDetailService productDetailService;

    @TestConfiguration
    static class ProductServiceApplicationITConfig {

        @Bean
        public ProductDetailService productDetailService() {
            return new ProductDetailServiceImpl();
        }
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(null, headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(createUrl("/api/v1/products/1"), HttpMethod.GET, httpEntity, String.class);
        assertEquals("Status code is invalid", 200, responseEntity.getStatusCode().value());
        assertNotNull("Data is null for the product ID", responseEntity.getBody());
    }

    @Test
    public void testInsertProduct() {
        Product product = new Product(3, "Shirt", "Adidas", new BigDecimal(123.98));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(new Gson().toJson(product), headers);
        ResponseEntity<Product> responseEntity = testRestTemplate.exchange(createUrl("/api/v1/products/"), HttpMethod.POST, httpEntity, Product.class);
        assertEquals("Status code is invalid", 201, responseEntity.getStatusCode().value());
        assertEquals("Name is not correct",product.getName(), responseEntity.getBody().getName());
    }

    private String createUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
