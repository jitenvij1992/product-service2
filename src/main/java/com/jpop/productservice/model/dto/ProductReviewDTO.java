package com.jpop.productservice.model.dto;

import com.jpop.productservice.model.Review;

import java.math.BigDecimal;
import java.util.List;

public class ProductReviewDTO {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;

    private List<Review> review;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }
}
