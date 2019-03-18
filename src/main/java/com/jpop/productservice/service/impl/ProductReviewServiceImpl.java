package com.jpop.productservice.service.impl;

import com.jpop.productservice.constants.Constants;
import com.jpop.productservice.exception.ProductValidationException;
import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;
import com.jpop.productservice.service.ProductReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ProductReviewServiceImpl.class);

    @Value("${review.application.name}")
    private String appName;

    private DiscoveryClient discoveryClient;

    private RestTemplate restTemplate;

    @Autowired
    public ProductReviewServiceImpl(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public ResponseEntity<Review> create(long id, ReviewDTO reviewDTO) {

        HttpEntity<ReviewDTO> httpEntity = getHttpEntity(reviewDTO);

        ResponseEntity<Review> responseEntity = restTemplate.exchange(getURL() + id + Constants.SLASH + Constants.REVIEW, HttpMethod.POST, httpEntity, Review.class);
        if (responseEntity.getStatusCode().value() != 201) {
            throw new ProductValidationException("Review data is not valid");
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity delete(long productId, long reviewId) {
        String response;
        int statusCode;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(getURL() + productId + Constants.SLASH + Constants.REVIEW + Constants.SLASH + reviewId, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
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
    public ResponseEntity<String> put(long productId, long reviewId, ReviewDTO payload) {
        HttpEntity<ReviewDTO> httpEntity = getHttpEntity(payload);
        String response;
        int statusCode;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(getURL() + productId + Constants.SLASH + Constants.REVIEW + Constants.SLASH + reviewId, HttpMethod.PUT, httpEntity, String.class);
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
    public ResponseEntity<List> get(long productId) {
        return restTemplate.getForEntity(getURL() + productId + Constants.SLASH + Constants.REVIEW, List.class);
    }

    private String getURL() {
        return discoveryClient.getInstances(appName).get(0).getUri() + Constants.API_VERSION;
    }

    private HttpEntity<ReviewDTO> getHttpEntity(ReviewDTO payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(payload, headers);
    }
}
