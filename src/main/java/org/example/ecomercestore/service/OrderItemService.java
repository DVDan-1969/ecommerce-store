package org.example.ecomercestore.service;


import org.example.ecomercestore.dto.OrderItemRequestDTO;
import org.example.ecomercestore.dto.OrderItemResponseDTO;


import java.util.List;

public interface OrderItemService {
    List<OrderItemResponseDTO> getAllOrderItems();

    OrderItemResponseDTO getOrderItemById(Long id);

    OrderItemResponseDTO save(OrderItemRequestDTO dto);

    OrderItemResponseDTO update(Long id, OrderItemRequestDTO dto);

    void deleteById(Long id);
}
