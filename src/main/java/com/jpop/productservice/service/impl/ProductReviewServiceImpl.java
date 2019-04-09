package com.jpop.productservice.service.impl;

import com.jpop.productservice.cilent.ReviewServiceClient;
import com.jpop.productservice.exception.ProductValidationException;
import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;
import com.jpop.productservice.service.ProductReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ProductReviewServiceImpl.class);

    private ReviewServiceClient reviewServiceClient;

    @Autowired
    public ProductReviewServiceImpl(ReviewServiceClient reviewServiceClient) {
        this.reviewServiceClient = reviewServiceClient;
    }

    @Override
    public ResponseEntity<Review> create(long id, ReviewDTO reviewDTO) {

        ResponseEntity<Review> responseEntity = reviewServiceClient.addReview(id, reviewDTO);
        if (responseEntity.getStatusCode().value() != 201) {
            throw new ProductValidationException(responseEntity.getBody().toString());
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity delete(long productId, long reviewId) {
        String response;
        int statusCode;
        try {
            ResponseEntity responseEntity = reviewServiceClient.deleteReview(productId, reviewId);
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
        String response;
        int statusCode;
        try {
            ResponseEntity<String> responseEntity = reviewServiceClient.updateReview(productId, reviewId, payload);
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
        return reviewServiceClient.getReviewsById(productId);
    }
}
