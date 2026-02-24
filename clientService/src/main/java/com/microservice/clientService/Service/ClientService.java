package com.microservice.clientService.Service;

import com.microservice.clientService.Model.Client;
import com.microservice.clientService.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public void addClient(Client c){
        clientRepository.save(c);
    }
    public List<Client> getClients(){
        return clientRepository.findAll();
    }
}
