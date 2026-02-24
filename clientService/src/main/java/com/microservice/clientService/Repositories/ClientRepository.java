package com.microservice.clientService.Repositories;

import com.microservice.clientService.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByNomAndPrenom(String nom, String prenom);
}
