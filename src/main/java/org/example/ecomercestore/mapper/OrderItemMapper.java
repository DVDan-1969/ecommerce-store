package org.example.ecomercestore.mapper;

import org.example.ecomercestore.dto.OrderItemRequestDTO;
import org.example.ecomercestore.dto.OrderItemResponseDTO;
import org.example.ecomercestore.model.Order;
import org.example.ecomercestore.model.OrderItem;
import org.example.ecomercestore.model.Product;
import org.springframework.stereotype.Component;



@Component
public class OrderItemMapper {
    public OrderItem toEntity(OrderItemRequestDTO dto,
                              Order order,
                              Product product)
    {
        OrderItem orderItem = new OrderItem();

        orderItem.setPrice(product.getPrice());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        return orderItem;
    }

    public OrderItemResponseDTO toResponseDTO(OrderItem orderItem) {
        return new OrderItemResponseDTO(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProduct().getId(),
                orderItem.getPrice(),
                orderItem.getQuantity()



        );
    }
}

