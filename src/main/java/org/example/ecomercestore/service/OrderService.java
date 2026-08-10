package org.example.ecomercestore.service;

import org.example.ecomercestore.dto.OrderRequestDTO;
import org.example.ecomercestore.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO getOrderById(Long id);

    OrderResponseDTO save(OrderRequestDTO dto);

    OrderResponseDTO update(Long id, OrderRequestDTO dto);

    void deleteById(Long id);
}

