package com.microservice.orderservice.controller;

import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public Order createOrder(@RequestBody CreateOrderRequest req) {
        return orderService.createOrder(req.order(), req.nom(), req.prenom());
    }

    public record CreateOrderRequest(String nom, String prenom, Order order) {}
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

}
