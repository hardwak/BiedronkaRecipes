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


    @GetMapping("/find")
    public ClientAllergyDTO find(@PathVariable Long id) {
        ClientAllergy ca = clientAllergyService.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(ca);
    }

    @PostMapping
    public ClientAllergyDTO create(@RequestBody ClientAllergyDTO dto) {
        ClientAllergy saved = clientAllergyService.create(dto.getClientId(), dto.getAllergenId());
        return toDTO(saved);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        clientAllergyService.delete(id);
    }

    // mapowanie
    private ClientAllergyDTO toDTO(ClientAllergy entity) {
        return new ClientAllergyDTO(
                entity.getClient().getId(),
                entity.getAllergen().getId()
        );
    }
}

