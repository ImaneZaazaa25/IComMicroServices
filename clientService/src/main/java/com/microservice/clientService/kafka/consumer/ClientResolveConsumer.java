package com.microservice.clientService.kafka.consumer;




import com.microservice.clientService.Model.Client;
import com.microservice.clientService.Repositories.ClientRepository;
import com.microservice.clientService.kafka.DTOKafka.ResolveClientRequest;
import com.microservice.clientService.kafka.DTOKafka.ResolveClientResponse;
import com.microservice.clientService.kafka.producer.ClientResponseProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
@RequiredArgsConstructor
@Component
public class ClientResolveConsumer {

    private final ClientRepository clientRepository;
    private final ClientResponseProducer responseProducer;



    @KafkaListener(topics = "client.resolve.request", groupId = "client-service")
    public void consume(ResolveClientRequest request) {
        System.out.println("[CLIENT] received => " + request);

        try {
            Client client = clientRepository
                    .findByNomAndPrenom(request.nom(), request.prenom())
                    .orElseGet(() ->
                            clientRepository.save(
                                    new Client(null,
                                            request.nom(),
                                            request.prenom())
                            )
                    );

            ResolveClientResponse response = new ResolveClientResponse(
                    request.correlationId(),
                    request.orderId(),
                    client.getClient_id(),
                    true,
                    null,
                    Instant.now()
            );

            responseProducer.sendResponse(response);

        } catch (Exception e) {

            ResolveClientResponse response = new ResolveClientResponse(
                    request.correlationId(),
                    request.orderId(),
                    null,
                    false,
                    e.getMessage(),
                    Instant.now()
            );

            responseProducer.sendResponse(response);
            System.out.println("[CLIENT] sent response => " + response);
        }
    }
}
