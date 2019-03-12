package com.jpop.productservice.controller;

import com.jpop.productservice.constants.Constants;
import com.jpop.productservice.exception.ProductValidationException;
import com.jpop.productservice.model.Review;
import com.jpop.productservice.model.dto.ReviewDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ProductReviewController.class);

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "Insert new review", notes = "This will be used to add review in inventory")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product added in inventory", responseHeaders = @ResponseHeader(name = "location", description = "location of created resources", response = URI.class))
    })
    @PostMapping(value = "/{id}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> postReview(@PathVariable long id, @RequestBody ReviewDTO payload) {
        logger.info("Received request to insert review having product id {} and payload {}", id, payload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<ReviewDTO> httpEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<Review> responseEntity = restTemplate.exchange(Constants.HOSTNAME + id + "/reviews", HttpMethod.POST, httpEntity, Review.class);
        if (responseEntity.getStatusCode().value() != 201) {
            throw new ProductValidationException("Review data is not valid");
        }
        Review review = responseEntity.getBody();
        assert review != null;
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
        String response;
        int statusCode;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(Constants.HOSTNAME + productId + "/reviews/" + reviewId, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
            return ResponseEntity.status(responseEntity.getStatusCode()).build();
        } catch (HttpStatusCodeException e) {
            logger.error("Exception occurred while delete the review {}", e.getResponseBodyAsString());
            response = e.getResponseBodyAsString();
            statusCode = e.getStatusCode().value();
        }
        return ResponseEntity.status(statusCode)
                .body(response);
    }

    @ApiOperation(value = "Update review by ID", notes = "This will be used to update the review by using product ID and review.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Review updated in inventory")
    })
    @PutMapping(value = "/{id}/reviews/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReview(@PathVariable("id") long productId, @PathVariable("reviewId") long reviewId, @RequestBody ReviewDTO payload) {
        logger.info("Request received to update the review having product Id {} and review Id {}", productId, reviewId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<ReviewDTO> httpEntity = new HttpEntity<>(payload, headers);
        String response;
        int statusCode;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(Constants.HOSTNAME + productId + "/reviews/" + reviewId, HttpMethod.PUT, httpEntity, String.class);
            return ResponseEntity.status(responseEntity.getStatusCode().value()).body(responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            logger.error("Exception occurred while updating the review {}", e.getResponseBodyAsString());
            response = e.getResponseBodyAsString();
            statusCode = e.getStatusCode().value();
        }
        return ResponseEntity.status(statusCode)
                .body(response);
    }
}
