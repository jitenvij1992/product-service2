package com.jpop.productservice.controller;

import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;
import com.jpop.productservice.service.ProductReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ProductReviewController.class);

    private RestTemplate restTemplate;

    private ProductReviewService productReviewService;

    @Autowired
    public ProductReviewController(ProductReviewService productReviewService, RestTemplate restTemplate) {
        this.productReviewService = productReviewService;
        this.restTemplate = restTemplate;
    }

    @ApiOperation(value = "Insert new review", notes = "This will be used to add review in inventory")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product added in inventory", responseHeaders = @ResponseHeader(name = "location", description = "location of created resources", response = URI.class))
    })
    @PostMapping(value = "/{id}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> postReview(@PathVariable long id, @RequestBody ReviewDTO payload) {
        logger.info("Received request to insert review having product id {} and payload {}", id, payload);
        ResponseEntity<Review> responseEntity = productReviewService.create(id, payload);
        Review review = responseEntity.getBody();
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}/review")
                        .buildAndExpand(review.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(review);
    }

    @ApiOperation(value = "Delete review by ID", notes = "This will be used to delete the review by using product ID and review Id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review deleted from inventory")
    })
    @DeleteMapping(value = "/products/{id}/reviews/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable("id") long productId, @PathVariable("reviewId") long reviewId) {
        logger.info("Request received to delete the review having product Id {} and review Id {}", productId, reviewId);
        return productReviewService.delete(productId, reviewId);
    }

    @ApiOperation(value = "Update review by ID", notes = "This will be used to update the review by using product ID and review.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Review updated in inventory")
    })
    @PutMapping(value = "/{id}/reviews/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReview(@PathVariable("id") long productId, @PathVariable("reviewId") long reviewId, @RequestBody ReviewDTO payload) {
        logger.info("Request received to update the review having product Id {} and review Id {}", productId, reviewId);
        return productReviewService.put(productId, reviewId, payload);
    }
}
