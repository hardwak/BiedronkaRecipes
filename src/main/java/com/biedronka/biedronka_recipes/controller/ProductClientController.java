package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.ProductClientDTO;
import com.biedronka.biedronka_recipes.entity.ProductClient;
import com.biedronka.biedronka_recipes.service.ProductClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product-client")
public class ProductClientController {

    private final ProductClientService service;

    public ProductClientController(ProductClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductClientDTO> getAll() {
        return service.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductClientDTO getOne(@PathVariable Long id) {
        var pc = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(pc);
    }

    @PostMapping
    public ProductClientDTO create(@RequestBody ProductClientDTO dto) {
        var saved = service.create(dto.getProductId(), dto.getClientId(),
                dto.getAmount(), dto.getLastCheckDate());
        return toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // mapowanie
    private ProductClientDTO toDTO(ProductClient pc) {
        return new ProductClientDTO(
                pc.getId(),
                pc.getProduct() != null ? pc.getProduct().getId() : null,
                pc.getClient() != null ? pc.getClient().getId() : null,
                pc.getAmount(),
                pc.getLastCheckDate()
        );
    }
}
