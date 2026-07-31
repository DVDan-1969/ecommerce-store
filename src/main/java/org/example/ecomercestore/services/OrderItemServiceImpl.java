package org.example.ecomercestore.services;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.model.OrderItem;
import org.example.ecomercestore.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository repository;

    public OrderItemServiceImpl(OrderItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return repository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public OrderItem update(Long id, OrderItem orderItem) {
        return repository.save(orderItem);
    }

}
