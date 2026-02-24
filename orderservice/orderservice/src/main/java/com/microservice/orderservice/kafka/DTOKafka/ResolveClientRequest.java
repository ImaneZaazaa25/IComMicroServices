package com.microservice.orderservice.kafka.DTOKafka;

import java.time.Instant;

public record ResolveClientRequest(
        String correlationId,
        Long orderId,
        String nom,
        String prenom,
        Instant createdAt
) {}