
package com.ecommerce.order_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // Create a new order
    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrders(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createOrders(order));
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(service.getOrders());
    }

    // Get an order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

    // Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestBody Order order) {
        return ResponseEntity.ok(service.updateOrder(id, order));
    }

    // Delete an order by ID
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
        return "Order item Deleted Successfully: "+id;
    }
}
