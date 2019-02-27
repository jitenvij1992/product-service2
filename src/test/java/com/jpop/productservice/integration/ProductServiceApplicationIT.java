package com.jpop.productservice.integration;

import com.jpop.productservice.ProductServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceApplicationIT {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testGetProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(null, headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(createUrl(), HttpMethod.GET, httpEntity, String.class);
        assertEquals("Status code is invalid", 200, responseEntity.getStatusCode().value());
    }

    private String createUrl() {
        return "http://localhost:" + port + "/api/v1/products/";
    }
}
