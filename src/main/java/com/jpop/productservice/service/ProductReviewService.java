package com.jpop.productservice.service;

import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductReviewService {

    ResponseEntity<Review> create(long id, ReviewDTO reviewDTO);

    ResponseEntity delete(long productId, long reviewId);

    ResponseEntity<String> put(long productId, long reviewId, ReviewDTO payload);

    ResponseEntity<List> get(long productId);
}
