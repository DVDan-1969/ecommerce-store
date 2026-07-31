package org.example.ecomercestore.services;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.model.Order;
import org.example.ecomercestore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Order> getAllOrders() {return repository.findAll();}


    @Override
    public Order getOrderById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Order update(Long id, Order order) {
        return repository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

