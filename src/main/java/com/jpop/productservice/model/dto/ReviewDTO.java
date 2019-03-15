package com.jpop.productservice.model.dto;

import java.util.Objects;

public class ReviewDTO {

    private long rating;
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDTO reviewDTO = (ReviewDTO) o;
        return rating == reviewDTO.rating &&
                Objects.equals(description, reviewDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, description);
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "rating=" + rating +
                ", description='" + description + '\'' +
                '}';
    }
}
