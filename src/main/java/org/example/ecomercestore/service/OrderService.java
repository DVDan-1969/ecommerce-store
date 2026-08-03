package org.example.ecomercestore.service;

import org.example.ecomercestore.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();


    Order getOrderById(Long id);

    Order save(Order order);

    Order update(Long id, Order order);

    void deleteById(Long id);
}

