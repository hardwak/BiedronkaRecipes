package com.biedronka.biedronka_recipes;

import com.biedronka.biedronka_recipes.dto.allergen.ClientAllergenResponseDTO;
import com.biedronka.biedronka_recipes.entity.Allergen;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.repository.AllergenRepository;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.service.AllergenService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
public class AllergenTests {
    @Autowired
    private AllergenRepository allergenRepository;
    @Autowired
    private AllergenService allergenService;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    @Transactional
    @Rollback
    void getAllClientAllergens() {
        Long clientId = 1L;
        Client client = clientRepository.findById(clientId).orElse(null);
        List<Allergen> allergens = allergenRepository.findAll();

        Assertions.assertNotNull(client);

        List<Allergen> clientAllergens = client.getAllergens();
        List<ClientAllergenResponseDTO> result = allergenService.getAllMyAllergens(clientId);

        Assertions.assertEquals(allergens.size(), result.size());
        Assertions.assertTrue(result.stream().allMatch(
                allergen -> clientAllergens.stream().allMatch(
                        clientAllergen ->
                                !allergen.id().equals(clientAllergen.getId()) || allergen.isActive()
                )
        ));
    }

    @Test
    @Transactional
    @Rollback
    void updateClientAllergensOnNew() {
        Long clientId = 1L;
        Client client = clientRepository.findById(clientId).orElse(null);

        Assertions.assertNotNull(client);

        List<Allergen> clientAllergensBefore = client.getAllergens();
        Assertions.assertFalse(clientAllergensBefore.isEmpty());

        allergenService.updateMyAllergens(clientId, List.of());

        List<Allergen> clientAllergensAfter = client.getAllergens();
        Assertions.assertTrue(clientAllergensAfter.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void updateClientAllergensOnExisting() {
        Long clientId = 1L;
        Client client = clientRepository.findById(clientId).orElse(null);

        Assertions.assertNotNull(client);

        List<Allergen> clientAllergens = client.getAllergens();
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> allergenService.updateMyAllergens(clientId, clientAllergens.stream().map(Allergen::getId).toList())
        );
    }
}
