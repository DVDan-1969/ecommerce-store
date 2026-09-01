package org.example.ecomercestore.exception;

public class OrderItemNotFoundException extends RuntimeException{
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
