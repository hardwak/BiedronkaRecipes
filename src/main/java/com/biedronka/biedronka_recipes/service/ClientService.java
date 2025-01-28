package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
}
