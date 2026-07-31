package org.example.ecomercestore.services;


import org.example.ecomercestore.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems();

    OrderItem getOrderItemById(Long id);

    OrderItem save(OrderItem orderItem);

    OrderItem update(Long id, OrderItem orderItem);

    void deleteById(Long id);
}
