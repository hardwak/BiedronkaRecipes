package com.biedronka.biedronka_recipes.controller;


import com.biedronka.biedronka_recipes.dto.ClientDTO;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientDTO> getAll() {
        return clientService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDTO getOne(@PathVariable Long id) {
        Client client = clientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return toDTO(client);
    }

    @PostMapping
    public ClientDTO create(@RequestBody ClientDTO dto) {
        Client entity = toEntity(dto);
        Client saved = clientService.createClient(entity);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO dto) {
        Client existing = clientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setAccountCreatedDate(dto.getAccountCreatedDate());
        Client updated = clientService.updateClient(existing);
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    // mapowanie
    private ClientDTO toDTO(Client c) {
        return new ClientDTO(
                c.getId(),
                c.getEmail(),
                c.getPassword(),
                c.getFirstName(),
                c.getLastName(),
                c.getAccountCreatedDate()
        );
    }

    private Client toEntity(ClientDTO dto) {
        Client c = new Client();
        c.setId(dto.getId());
        c.setEmail(dto.getEmail());
        c.setPassword(dto.getPassword());
        c.setFirstName(dto.getFirstName());
        c.setLastName(dto.getLastName());
        c.setAccountCreatedDate(dto.getAccountCreatedDate());
        return c;
    }
}

