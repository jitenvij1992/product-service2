package com.jpop.productservice.controller;

import com.google.gson.Gson;
import com.jpop.productservice.exception.ProductNotFoundException;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductDeleteService;
import com.jpop.productservice.service.ProductDetailService;
import com.jpop.productservice.service.ProductInsertService;
import com.jpop.productservice.service.ProductReviewService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductServiceController.class)
public class ProductServiceControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ProductDetailService productDetailService;
    @MockBean
    ProductInsertService productInsertService;
    @MockBean
    RestTemplate restTemplate;
    @MockBean
    ProductReviewService productReviewService;
    @MockBean
    private ProductDeleteService productDeleteService;
    Product product;

    @Before
    public void setUp() {
        product = new Product(1, "Shirt", "Adidas", BigDecimal.valueOf(123.98));
    }

    @Test
    public void getAllProducts() throws Exception {

        List<Product> productList = Arrays.asList(product);
        given(productDetailService.getAvailableProducts()).willReturn(productList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(product.getName())));
    }

    @Test
    public void getProductDetails() throws Exception {
        given(productDetailService.getProductDetails(1)).willReturn(product);
        Mockito.when(productReviewService.get(anyLong()))
                .thenReturn(new ResponseEntity(List.of(product), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.description", is(product.getDescription())));
    }

    @Test
    public void deleteProductById() throws Exception {
        Mockito.doNothing().when(productDeleteService).deleteProduct(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductByIdWhenException() throws Exception {
        Mockito.doThrow(ProductNotFoundException.class).when(productDeleteService).deleteProduct(8);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/8")
                .contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNoContent());
    }

    @Test
    public void updateProduct() throws Exception {
        Mockito.doNothing().when(productInsertService).processUpdatedData(product, 1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(product)))
                .andExpect(status().isOk());
    }
}
