package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.OpinionDTO;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Opinion;
import com.biedronka.biedronka_recipes.service.ClientService;
import com.biedronka.biedronka_recipes.service.OpinionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/opinions")
public class OpinionController {

    private final OpinionService opinionService;
    private final ClientService clientService;

    public OpinionController(OpinionService opinionService, ClientService clientService) {
        this.opinionService = opinionService;
        this.clientService = clientService;
    }

    @GetMapping
    public List<OpinionDTO> getAll() {
        return opinionService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OpinionDTO getOne(@PathVariable Long id) {
        Opinion o = opinionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Opinion not found"));
        return toDTO(o);
    }

    @PostMapping
    public OpinionDTO create(@RequestBody OpinionDTO dto) {
        Client client = clientService.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Opinion entity = new Opinion();
        entity.setStars(dto.getStars());
        entity.setClient(client);

        Opinion saved = opinionService.createOpinion(entity);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public OpinionDTO update(@PathVariable Long id, @RequestBody OpinionDTO dto) {
        Opinion existing = opinionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Opinion not found"));
        existing.setStars(dto.getStars());
        if (dto.getClientId() != null) {
            Client client = clientService.findById(dto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            existing.setClient(client);
        }
        Opinion updated = opinionService.updateOpinion(existing);
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        opinionService.deleteOpinion(id);
    }

    // mapowanie
    private OpinionDTO toDTO(Opinion o) {
        return new OpinionDTO(
                o.getId(),
                o.getStars(),
                o.getClient() != null ? o.getClient().getId() : null
        );
    }
}
