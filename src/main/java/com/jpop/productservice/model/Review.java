package com.jpop.productservice.model;


import java.util.Date;
import java.util.Objects;


public class Review {

    private long id;

    private String description;

    private long rating;

    private long productId;

    private Date updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", productId=" + productId +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id &&
                rating == review.rating &&
                productId == review.productId &&
                Objects.equals(description, review.description) &&
                Objects.equals(updatedAt, review.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, rating, productId, updatedAt);
    }
}
