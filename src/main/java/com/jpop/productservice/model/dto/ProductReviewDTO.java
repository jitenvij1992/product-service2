package com.jpop.productservice.model.dto;

import com.jpop.productservice.model.Product;
import com.jpop.productservice.model.Review;

import java.util.List;
import java.util.Objects;

public class ProductReviewDTO {

    private Product product;
    private List<Review> review;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductReviewDTO that = (ProductReviewDTO) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, review);
    }

    @Override
    public String toString() {
        return "ProductReviewDTO{" +
                "product=" + product +
                ", review=" + review +
                '}';
    }
}
