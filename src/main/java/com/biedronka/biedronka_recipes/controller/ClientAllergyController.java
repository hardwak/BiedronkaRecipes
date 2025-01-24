package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.ClientAllergyDTO;
import com.biedronka.biedronka_recipes.entity.ClientAllergy;
import com.biedronka.biedronka_recipes.service.ClientAllergyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client-allergies")
public class ClientAllergyController {

    private final ClientAllergyService clientAllergyService;

    public ClientAllergyController(ClientAllergyService clientAllergyService) {
        this.clientAllergyService = clientAllergyService;
    }

    @GetMapping
    public List<ClientAllergyDTO> getAll() {
        return clientAllergyService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Dostęp do encji z kluczem kompozytowym – np. w parametrach
    @GetMapping("/find")
    public ClientAllergyDTO find(@RequestParam Long clientId,
                                 @RequestParam Long allergenId) {
        ClientAllergy ca = clientAllergyService.findByIds(clientId, allergenId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(ca);
    }

    @PostMapping
    public ClientAllergyDTO create(@RequestBody ClientAllergyDTO dto) {
        ClientAllergy saved = clientAllergyService.create(dto.getClientId(), dto.getAllergenId());
        return toDTO(saved);
    }

    @DeleteMapping
    public void delete(@RequestParam Long clientId,
                       @RequestParam Long allergenId) {
        clientAllergyService.delete(clientId, allergenId);
    }

    // mapowanie
    private ClientAllergyDTO toDTO(ClientAllergy entity) {
        return new ClientAllergyDTO(
                entity.getClient().getId(),
                entity.getAllergen().getId()
        );
    }
}

