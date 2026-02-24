package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.ProductDTO;
import com.microservice.orderservice.kafka.DTOKafka.ResolveClientRequest;
import com.microservice.orderservice.kafka.producer.ClientResolveProducer;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderItem;
import com.microservice.orderservice.model.OrderStatus;
import com.microservice.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final ClientResolveProducer clientResolveProducer;

    public Order createOrder(Order order, String nom, String prenom) {

        // 1) remplir les infos produits (synchrone)
        if (order.getOrderItemList() != null) {
            for (OrderItem orderItem : order.getOrderItemList()) {
                ProductDTO product = productClient.getProductById(orderItem.getProductId());

                orderItem.setOrder(order);           // important pour mappedBy
                orderItem.setPrice(product.getPrice());
            }
        }

        // 2) commande en attente (on n'a pas encore client_id)
        order.setClient_id(null);
        order.setStatus(OrderStatus.PENDING);

        // 3) save d'abord pour générer l'id
        Order saved = orderRepository.save(order);

        // ⚠️ Attention: selon ton entity, ça peut être getId() ou getId() avec I majuscule.
        // Comme tu as "private Long Id;", Lombok génère souvent getId() mais c'est ambigu.
        // Le mieux est de renommer le champ en "id".
        Long orderId = saved.getId(); // si ça ne compile pas, remplace par saved.getId() / saved.getId()

        // 4) envoyer request Kafka au service Client
        String correlationId = UUID.randomUUID().toString();

        ResolveClientRequest req = new ResolveClientRequest(
                correlationId,
                orderId,
                nom,
                prenom,
                Instant.now()
        );

        clientResolveProducer.sendResolveRequest(req);

        // 5) retourner la commande PENDING
        return saved;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}