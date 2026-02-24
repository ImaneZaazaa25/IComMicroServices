package com.microservice.clientService.Controller;

import com.microservice.clientService.Model.Client;
import com.microservice.clientService.Service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody Client c){
        clientService.addClient(c);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getAllOrders(){
        return clientService.getClients();
    }
}
