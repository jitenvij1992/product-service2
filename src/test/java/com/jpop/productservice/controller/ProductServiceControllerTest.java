package com.jpop.productservice.controller;

import com.jpop.productservice.exception.ProductDeletionException;
import com.jpop.productservice.model.Product;
import com.jpop.productservice.service.ProductDeleteService;
import com.jpop.productservice.service.ProductDetailService;
import com.jpop.productservice.service.ProductInsertService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
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
    private ProductDeleteService productDeleteService;

    @Test
    public void getAllProducts() throws Exception {

        Product product = new Product(1, "Shirt", "Adidas", new BigDecimal(123.98));
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
        Product product = new Product(1, "Shirt", "Adidas", new BigDecimal(123.98));
        final String expectedString = "{id=1, name=Shirt, description=Adidas, price=123.9800000000000039790393202565610408782958984375}";

        given(productDetailService.getProductDetails(1)).willReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasToString(expectedString)))
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
        Mockito.doThrow(ProductDeletionException.class).when(productDeleteService).deleteProduct(8);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/8")
                .contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNoContent());
    }
}
