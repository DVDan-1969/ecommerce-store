package org.example.ecomercestore.controller;

import jakarta.validation.Valid;
import org.example.ecomercestore.dto.OrderRequestDTO;
import org.example.ecomercestore.dto.OrderResponseDTO;
import org.example.ecomercestore.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    @PostMapping
    public OrderResponseDTO saveOrder(
            @Valid @RequestBody OrderRequestDTO dto) {
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @Valid @PathVariable Long id, @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO updatedOrder = service.update(id, dto);
        return ResponseEntity.ok(updatedOrder);
    }
}
