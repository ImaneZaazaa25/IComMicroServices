package com.microservice.clientService.kafka.producer;


import com.microservice.clientService.kafka.DTOKafka.ResolveClientResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClientResponseProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ClientResponseProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResponse(ResolveClientResponse response) {
        kafkaTemplate.send("client.resolve.response",
                response.correlationId(),
                response);
    }
}

