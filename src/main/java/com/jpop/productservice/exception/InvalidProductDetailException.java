package com.jpop.productservice.exception;

public class InvalidProductDetailException extends RuntimeException {

    public InvalidProductDetailException(String message) {
        super(message);
    }
}
