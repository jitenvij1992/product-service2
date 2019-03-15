package com.jpop.productservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Review {

    private long id;

    private String description;

    private long rating;

    private long productId;

    private Date updatedAt;

}
