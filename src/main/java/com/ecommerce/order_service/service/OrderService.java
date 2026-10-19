package com.ecommerce.order_service.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.ecommerce.order_service.dto.ProductResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final RestClient productRestClient;
    private final String productServiceUrl;

    public OrderService(
            OrderRepository repository,
            RestClient productRestClient,
            @Value("${product.service.url:http://localhost:8082}")
            String productServiceUrl) {

        this.repository = repository;
        this.productRestClient = productRestClient;
        this.productServiceUrl = productServiceUrl;
    }

    // Create order using the actual product price
    public Order createOrders(Order order) {

    	 ProductResponse product = productRestClient.get()
    		   .uri(productServiceUrl+ "/api/products/{id}",
    		   order.getProductId()).retrieve().body(ProductResponse.class);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        if (order.getQuantity() == null
                || order.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }

        if (order.getQuantity().compareTo(
                BigDecimal.valueOf(product.getQuantity())) > 0) {
            throw new IllegalArgumentException(
                    "Requested quantity exceeds available stock");
        }

        BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice());

        order.setUnitPrice(unitPrice);
        order.setTotalPrice(unitPrice.multiply(order.getQuantity()));
        order.setStatus("CREATED");

        return repository.save(order);
    }

    public List<Order> getOrders() {
        return repository.findAll();
    }

    public Order getOrder(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found with ID: " + id));
    }

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

    public void deleteOrder(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found with ID: " + id);
        }

        repository.deleteById(id);
    }
}