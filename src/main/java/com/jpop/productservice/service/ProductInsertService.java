package com.jpop.productservice.service;

import com.jpop.productservice.model.Product;

public interface ProductInsertService {

    void processRawData(Product product);

    void processUpdatedData(Product product, long id);
}
