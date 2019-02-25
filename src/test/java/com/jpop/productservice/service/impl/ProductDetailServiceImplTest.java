package com.jpop.productservice.service.impl;

import com.jpop.productservice.dao.ProductDeleteRepository;
import com.jpop.productservice.dao.ProductDetailRepository;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductDeleteService;
import com.jpop.productservice.service.ProductDetailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ProductDetailServiceImplTest {


    @TestConfiguration
    static class ProductDetailServiceImplTestConfig {

        @Bean
        ProductDetailService productDetailService() {
            return new ProductDetailServiceImpl();
        }
     }

     @Autowired
     ProductDetailService productDetailService;

    @MockBean
    ProductDetailRepository productDetailRepository;

    @Before
    public void setUp() {
        Product product = new Product(1, "Shirt", "Adidas", new BigDecimal(123.98));
        //https://www.baeldung.com/spring-boot-testing
       // Mockito.when(productDetailRepository.findById(1l)).thenReturn(product);
    }

    @Test
    public void getProductDetails() {
       // Product product = new Product(1, "Shirt", "Adidas", new BigDecimal(123.98));
        Product productActualDetails = productDetailService.getProductDetails(1);
        assertEquals("Name is not equal", "shirt", productActualDetails.getName());
    }




}