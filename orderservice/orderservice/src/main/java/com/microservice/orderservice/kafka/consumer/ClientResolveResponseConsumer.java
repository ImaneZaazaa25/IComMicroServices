package com.microservice.orderservice.kafka.consumer;

import com.microservice.orderservice.kafka.DTOKafka.ResolveClientResponse;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderStatus;
import com.microservice.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class ClientResolveResponseConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "client.resolve.response", groupId = "order-service")
    public void consume(ResolveClientResponse response) {

        Order order = orderRepository.findById(response.orderId())
                .orElse(null);

        if (order == null) return;

        if (response.success() && response.clientId() != null) {
            order.setClient_id(response.clientId());
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.REJECTED);
        }

        orderRepository.save(order);
    }
}