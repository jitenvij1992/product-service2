package com.jpop.productservice.service.impl;

import com.jpop.productservice.exception.ProductValidationException;
import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;
import com.jpop.productservice.service.ProductReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ProductReviewServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public ResponseEntity<Review> callReviewServiceForPost(String url, ReviewDTO reviewDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<ReviewDTO> httpEntity = new HttpEntity<>(reviewDTO, headers);
        ResponseEntity<Review> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Review.class);
        if (responseEntity.getStatusCode().value() != 201) {
            throw new ProductValidationException("Review data is not valid");
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity callReviewServiceForDelete(String url) {
        String response;
        int statusCode;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
            return ResponseEntity.status(responseEntity.getStatusCode()).build();
        } catch (HttpStatusCodeException e) {
            logger.error("Exception occurred while delete the review {}", e.getResponseBodyAsString());
            response = e.getResponseBodyAsString();
            statusCode = e.getStatusCode().value();
        }
        return ResponseEntity.status(statusCode)
                .body(response);
    }

    @Override
    public ResponseEntity<String> callReviewServiceForPut(String url, ReviewDTO payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<ReviewDTO> httpEntity = new HttpEntity<>(payload, headers);
        String response;
        int statusCode;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
            return ResponseEntity.status(responseEntity.getStatusCode().value()).body(responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            logger.error("Exception occurred while updating the review {}", e.getResponseBodyAsString());
            response = e.getResponseBodyAsString();
            statusCode = e.getStatusCode().value();
        }
        return ResponseEntity.status(statusCode)
                .body(response);
    }

    @Override
    public ResponseEntity<List> callReviewServiceForGet(String url) {
        return restTemplate.getForEntity(url, List.class);
    }
}
