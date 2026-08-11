package org.example.ecomercestore.controller;

import org.example.ecomercestore.dto.OrderItemRequestDTO;
import org.example.ecomercestore.dto.OrderItemResponseDTO;
import org.example.ecomercestore.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
public class OrderItemController {
    private final OrderItemService service;

    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderItemResponseDTO> getAllOrderItems() {
        return service.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public OrderItemResponseDTO getOrderItemById(@PathVariable Long id) {
        return service.getOrderItemById(id);
    }

    @PostMapping
    public OrderItemResponseDTO saveOrderItem(@RequestBody OrderItemRequestDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> update(@PathVariable Long id, @RequestBody OrderItemRequestDTO dto) {
        OrderItemResponseDTO updatedOrderItem = service.update(id,dto);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
