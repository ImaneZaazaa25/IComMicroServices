package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.ProductDTO;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderItem;
import com.microservice.orderservice.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public void createOrder(Order order) {
        for (OrderItem orderItem : order.getOrderItemList()) {
            // récupère le produit depuis le microservice Product
            ProductDTO product = productClient.getProductById(orderItem.getProductId());

            orderItem.setOrder(order);
            orderItem.setPrice(product.getPrice());
        }
        orderRepository.save(order);
    }
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
}
