package com.microservice.orderservice.kafka.producer;




import com.microservice.orderservice.kafka.DTOKafka.ResolveClientRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClientResolveProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ClientResolveProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResolveRequest(ResolveClientRequest request) {
        kafkaTemplate.send("client.resolve.request",
                request.correlationId(),
                request);
    }
}
