package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;
import org.example.ecomercestore.dto.OrderRequestDTO;
import org.example.ecomercestore.dto.OrderResponseDTO;
import org.example.ecomercestore.exception.OrderNotFoundException;
import org.example.ecomercestore.exception.UserNotFoundException;
import org.example.ecomercestore.mapper.OrderMapper;
import org.example.ecomercestore.model.Order;
import org.example.ecomercestore.model.User;
import org.example.ecomercestore.repository.OrderRepository;
import org.example.ecomercestore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger logger=LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository repository;
    private final OrderMapper ordrMapper;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository repository, OrderMapper ordrMapper, UserRepository userRepository) {
        this.repository = repository;
        this.ordrMapper = ordrMapper;
        this.userRepository = userRepository;
    }



    @Override
    public List<OrderResponseDTO> getAllOrders() {
        logger.info("Retrieving all orders");

        return repository.findAll()
            .stream()
            .map(ordrMapper::toResponseDTO)
            .toList();
    }


    @Override
    public OrderResponseDTO getOrderById(Long id) {

        logger.info("Searching for order with id {}", id);
        Order order = repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Order with id {} not found",id);
                    return new OrderNotFoundException("Order not found");
                });
        return ordrMapper.toResponseDTO(order);
    }

    @Override
    public OrderResponseDTO save(OrderRequestDTO dto) {
        logger.info("Creating new order for user id {}", dto.getUserId());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()->{
                    logger.warn("User with id {} not found",dto.getUserId());
                    return new UserNotFoundException("User not found");
                });
        Order order = ordrMapper.toEntity(dto, user);
        Order savedOrder = repository.save(order);
        logger.info("Order created successfully with id {}", savedOrder.getId());
        return ordrMapper.toResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO update(Long id, OrderRequestDTO dto) {

        logger.info("Updating order with id {}", id);
        Order order = repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Order with id {} not found",id);
                    return new OrderNotFoundException("Order not found");
                });
        User user=userRepository.findById(dto.getUserId())
                .orElseThrow(()->{
                    logger.warn("User with id {} not found",dto.getUserId());
                    return new UserNotFoundException("User not found");
                });
        order.setUser(user);
        order.setData(dto.getDate());
        Order updatedOrder = repository.save(order);
        logger.info("Order with id {} updated successfully", updatedOrder.getId());
        return ordrMapper.toResponseDTO(updatedOrder);

    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting order with id {}", id);
        Order order=repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Order with id {} not found",id);
                    return new OrderNotFoundException("Order not found");
                });
        repository.delete(order);
        logger.info("Order with id {} deleted successfully", id);
    }
}

