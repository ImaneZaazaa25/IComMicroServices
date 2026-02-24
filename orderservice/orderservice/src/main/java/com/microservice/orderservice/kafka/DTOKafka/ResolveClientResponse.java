package com.microservice.orderservice.kafka.DTOKafka;

import java.time.Instant;

public record ResolveClientResponse(
        String correlationId,
        Long orderId,
        Long clientId,
        boolean success,
        String error,
        Instant respondedAt
) {}
