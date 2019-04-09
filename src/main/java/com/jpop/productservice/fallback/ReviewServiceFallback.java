package com.jpop.productservice.fallback;

import com.jpop.productservice.cilent.ReviewServiceClient;
import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ReviewServiceFallback implements ReviewServiceClient {

    @Override
    public ResponseEntity<List> getReviewsById(long productId) {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @Override
    public ResponseEntity<Review> addReview(long productId, ReviewDTO review) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Review());
    }

    @Override
    public ResponseEntity deleteReview(long productId, long reviewId) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Service not available");
    }

    @Override
    public ResponseEntity<String> updateReview(long productId, long reviewId, ReviewDTO review) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Service not available");
    }
}
