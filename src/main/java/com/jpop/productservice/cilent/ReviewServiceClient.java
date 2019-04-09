package com.jpop.productservice.cilent;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jpop.productservice.fallback.ReviewServiceFallback;
import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;

@FeignClient(name = "API-GATEWAY", fallback = ReviewServiceFallback.class)
public interface ReviewServiceClient {

    @GetMapping(value = "/api/client/api/v1/{productId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List> getReviewsById(@PathVariable long productId);

    @PostMapping(value = "/api/client/api/v1/{productId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Review> addReview(@PathVariable long productId, @RequestBody ReviewDTO review);

    @DeleteMapping(value = "/api/client/api/v1/{productId}/reviews/{reviewId}")
    ResponseEntity deleteReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId);

    @PutMapping(value = "/api/client/api/v1/{productId}/reviews/{reviewId}", consumes =
            MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId, @RequestBody ReviewDTO review);
}
