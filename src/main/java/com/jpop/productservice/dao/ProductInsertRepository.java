package com.jpop.productservice.dao;

import com.jpop.productservice.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInsertRepository extends CrudRepository<Product, Long> {
}
