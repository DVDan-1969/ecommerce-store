package org.example.ecomercestore.mapper;

import org.example.ecomercestore.dto.OrderRequestDTO;
import org.example.ecomercestore.dto.OrderResponseDTO;
import org.example.ecomercestore.model.Order;
import org.example.ecomercestore.model.User;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toEntity(OrderRequestDTO dto, User user) {
        Order order = new Order();

        order.setData(dto.getDate());

        order.setUser(user);
        return order;
    }

    public OrderResponseDTO toResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getData(),
                order.getTotal(),
                order.getUser().getId()
        );

    }


}
