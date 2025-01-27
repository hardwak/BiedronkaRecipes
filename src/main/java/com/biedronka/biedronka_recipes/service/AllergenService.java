package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.dto.allergen.ClientAllergenResponseDTO;
import com.biedronka.biedronka_recipes.entity.Allergen;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.repository.AllergenRepository;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.utils.AllergenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllergenService {
    private final AllergenRepository allergenRepository;
    private final ClientRepository clientRepository;
    private final AllergenMapper allergenMapper;

    public List<ClientAllergenResponseDTO> getAllMyAllergens(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));
        List<Allergen> allAllergens = allergenRepository.findAll();

        return allergenMapper.toClientAllergenResponseDTOList(allAllergens, client);
    }

    public List<ClientAllergenResponseDTO> updateMyAllergens(Long clientId, List<Long> allergenIds) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));

        List<Allergen> selectedAllergens = allergenRepository.findAllById(allergenIds != null ? allergenIds : List.of());
        List<Allergen> clientAllergens = client.getAllergens();

        if (
                selectedAllergens.size() == clientAllergens.size() &&
                new HashSet<>(selectedAllergens).containsAll(clientAllergens) &&
                new HashSet<>(clientAllergens).containsAll(selectedAllergens)
        ) {
            throw new IllegalArgumentException("Allergens lists are the same");
        }

        client.setAllergens(selectedAllergens);
        clientRepository.save(client);

        return getAllMyAllergens(clientId);
    }
}
