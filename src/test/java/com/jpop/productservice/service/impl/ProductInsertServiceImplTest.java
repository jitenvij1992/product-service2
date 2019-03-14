package com.jpop.productservice.service.impl;

import com.jpop.productservice.dao.ProductInsertRepository;
import com.jpop.productservice.exception.ProductValidationException;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductInsertService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ProductInsertServiceImplTest {

    @TestConfiguration
    static class ProductInsertTestConfig {

        @Bean
        ProductInsertService productInsertService() {
            return new ProductInsertServiceImpl();
        }
    }

    @MockBean
    ProductInsertRepository productInsertRepository;

    @Autowired
    ProductInsertService productInsertService;

    @Test
    public void processRawData() {
        Product product = new Product(1, "Shirt", "Adidas", BigDecimal.valueOf(123.98));
        productInsertService.processRawData(product);
        verify(productInsertRepository, times(1)).save(product);

    }

    @Test(expected = ProductValidationException.class)
    public void processUpdatedData() {
        Product product = new Product(1, null, "Adidas", BigDecimal.valueOf(123.98));
        productInsertService.processRawData(product);
        verify(productInsertRepository, times(1)).save(product);
    }
}