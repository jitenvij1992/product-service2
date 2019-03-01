package com.jpop.productservice.service.impl;

import com.jpop.productservice.dao.ProductDeleteRepository;
import com.jpop.productservice.exception.ProductNotFoundException;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductDeleteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProductDeleteServiceImplTest {

    @TestConfiguration
    static class ProductDeleteServiceImplConfig {

        @Bean
        ProductDeleteService productDeleteService() {
            return new ProductDeleteServiceImpl();
        }
    }

    @Autowired
    ProductDeleteService productDeleteService;

    @MockBean
    ProductDeleteRepository productDeleteRepository;

    @Test
    public void deleteProduct() {
        Mockito.doNothing().when(productDeleteRepository).deleteById(1L);
        when(productDeleteRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        productDeleteService.deleteProduct(1L);
        verify(productDeleteRepository, times(1)).deleteById(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void deleteProductWithException() {
        when(productDeleteRepository.findById(1l)).thenThrow(ProductNotFoundException.class);
        Mockito.doNothing().when(productDeleteRepository).deleteById(1L);
        productDeleteService.deleteProduct(1L);
        verify(productDeleteRepository, times(1)).deleteById(1L);
    }
}