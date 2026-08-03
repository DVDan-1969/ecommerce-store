package org.example.ecomercestore.controller;

import org.example.ecomercestore.model.OrderItem;
import org.example.ecomercestore.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orderItems")
public class OrderItemController {
    private OrderItemService service;

    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return service.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItemById(@PathVariable Long id) {
        return service.getOrderItemById(id);
    }

    @PostMapping
    public OrderItem saveOrderItem(@RequestBody OrderItem orderItem) {
        return service.save(orderItem);
    }

    @PutMapping
    public ResponseEntity<OrderItem> update(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        OrderItem updatedOrderItem = service.update(id, orderItem);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
