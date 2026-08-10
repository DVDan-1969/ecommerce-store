package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.dto.OrderRequestDTO;
import org.example.ecomercestore.dto.OrderResponseDTO;
import org.example.ecomercestore.mapper.OrderMapper;
import org.example.ecomercestore.model.Order;
import org.example.ecomercestore.model.User;
import org.example.ecomercestore.repository.OrderRepository;
import org.example.ecomercestore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderMapper ordrMapper;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository repository, OrderMapper ordrMapper, UserRepository userRepository) {
        this.repository = repository;
        this.ordrMapper = ordrMapper;
        this.userRepository = userRepository;
    }



    @Override
    public List<OrderResponseDTO> getAllOrders() {return repository.findAll()
            .stream()
            .map(ordrMapper::toResponseDTO)
            .toList();
    }


    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(()->new RuntimeException("Order not found"));
        return ordrMapper.toResponseDTO(order);
    }

    @Override
    public OrderResponseDTO save(OrderRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()->new RuntimeException("User not found"));
        Order order = ordrMapper.toEntity(dto, user);
        Order savedOrder = repository.save(order);
        return ordrMapper.toResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO update(Long id, OrderRequestDTO dto) {
        Order order = repository.findById(id)
                .orElseThrow(()->new RuntimeException("Order not found"));
        User user=userRepository.findById(dto.getUserId())
                .orElseThrow(()->new RuntimeException("User not found"));
        order.setUser(user);
        order.setData(dto.getDate());
        Order updatedOrder = repository.save(order);
        return ordrMapper.toResponseDTO(updatedOrder);

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

