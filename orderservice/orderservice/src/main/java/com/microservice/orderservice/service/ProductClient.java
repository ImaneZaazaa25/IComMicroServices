package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductClient {
    private final WebClient webClient = WebClient.create("http://localhost:8081");

    public ProductDTO getProductById(String productId) {
        return webClient.get()
                .uri("/api/product/" + productId)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block(); // attention, bloquant
    }
}

