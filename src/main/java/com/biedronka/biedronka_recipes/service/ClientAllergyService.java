package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.entity.Allergen;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.ClientAllergy;

import com.biedronka.biedronka_recipes.repository.AllergenRepository;
import com.biedronka.biedronka_recipes.repository.ClientAllergyRepository;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientAllergyService {

    private final ClientAllergyRepository clientAllergyRepository;
    private final ClientRepository clientRepository;
    private final AllergenRepository allergenRepository;

    public ClientAllergyService(ClientAllergyRepository clientAllergyRepository,
                                ClientRepository clientRepository,
                                AllergenRepository allergenRepository) {
        this.clientAllergyRepository = clientAllergyRepository;
        this.clientRepository = clientRepository;
        this.allergenRepository = allergenRepository;
    }

    // Ponieważ jest composite key, zapytania do findAll() itp.
    public List<ClientAllergy> findAll() {
        return clientAllergyRepository.findAll();
    }

    // Wyszukiwanie po composite key
    public Optional<ClientAllergy> findByIds(Long clientId, Long allergenId) {
        ClientAllergyId id = new ClientAllergyId(clientId, allergenId);
        return clientAllergyRepository.findById(id);
    }

    // Tworzenie nowego wpisu
    public ClientAllergy create(Long clientId, Long allergenId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Allergen allergen = allergenRepository.findById(allergenId)
                .orElseThrow(() -> new RuntimeException("Allergen not found"));

        ClientAllergy entity = new ClientAllergy(client, allergen);
        return clientAllergyRepository.save(entity);
    }

    // Usuwanie
    public void delete(Long clientId, Long allergenId) {
        ClientAllergyId id = new ClientAllergyId(clientId, allergenId);
        clientAllergyRepository.deleteById(id);
    }
}

