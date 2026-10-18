package com.ecommerce.order_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    // Create a new order
    public Order createOrders(Order order) {
        return repository.save(order);
    }

    // Get all orders
    public List<Order> getOrders() {
        return repository.findAll();
    }

    // Get an order by ID
    public Order getOrder(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found with ID: " + id));
    }

    // Update an existing order
    public Order updateOrder(Long id, Order order) {

        Order existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found with ID: " + id));

        existing.setProductId(order.getProductId());
        existing.setCustomerName(order.getCustomerName());
        existing.setQuantity(order.getQuantity());
        existing.setUnitPrice(order.getUnitPrice());
        existing.setTotalPrice(order.getTotalPrice());
        existing.setStatus(order.getStatus());

        return repository.save(existing);
    }

    // Delete an order by ID
    public void deleteOrder(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found with ID: " + id);
        }

        repository.deleteById(id);
    }
}

