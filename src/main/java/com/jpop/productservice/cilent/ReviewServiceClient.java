package com.jpop.productservice.cilent;

import com.jpop.productservice.fallback.ReviewServiceFallback;
import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "REVIEW-SERVICE", fallback = ReviewServiceFallback.class)
public interface ReviewServiceClient {

    @GetMapping(value = "/api/v1/{productId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List> getReviewsById(@PathVariable long productId);

    @PostMapping(value = "/api/v1/{productId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Review> addReview(@PathVariable long productId, @RequestBody ReviewDTO review);

    @DeleteMapping(value = "/api/v1/{productId}/reviews/{reviewId}")
    ResponseEntity deleteReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId);

    @PutMapping(value = "/api/v1/{productId}/reviews/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId, @RequestBody ReviewDTO review);
}
