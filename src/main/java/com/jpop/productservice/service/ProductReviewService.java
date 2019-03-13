package com.jpop.productservice.service;

import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductReviewService {

    ResponseEntity<Review> callReviewServiceForPost(String url, ReviewDTO reviewDTO);

    ResponseEntity callReviewServiceForDelete(String s);

    ResponseEntity<String> callReviewServiceForPut(String s, ReviewDTO payload);

    ResponseEntity<List> callReviewServiceForGet(String s);
}
