package com.jpop.productservice.service.impl;

import com.jpop.productservice.dao.ProductDetailRepository;
import com.jpop.productservice.model.Product;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
        List productList = Arrays.asList(product);
        Mockito.when(productDetailRepository.findById(1l)).thenReturn(java.util.Optional.of(product));
        Mockito.when(productDetailRepository.findAll()).thenReturn(productList);
    }

    @Test
    public void getProductDetails() {
        String name = "Shirt";
        Product productActualDetails = productDetailService.getProductDetails(1);
        assertEquals("Name is not equal", name, productActualDetails.getName());
    }

    @Test
    public void getAvailableProducts() {
        List<Product> availableProducts = productDetailService.getAvailableProducts();
        assertThat(availableProducts, hasSize(1));
    }
}