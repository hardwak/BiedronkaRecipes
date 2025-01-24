package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.ProductDTO;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getOne(@PathVariable Long id) {
        Product p = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return toDTO(p);
    }

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO dto) {
        Product entity = toEntity(dto);
        Product saved = productService.createProduct(entity);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        Product existing = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setName(dto.getName());
        existing.setBrand(dto.getBrand());
        existing.setWeight(dto.getWeight());
        existing.setUnit(dto.getUnit());
        existing.setExpirationDate(dto.getExpirationDate());
        existing.setPrice(dto.getPrice());
        existing.setOnSale(dto.getOnSale());
        Product updated = productService.updateProduct(existing);
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // mapowanie
    private ProductDTO toDTO(Product p) {
        return new ProductDTO(
                p.getId(),
                p.getName(),
                p.getBrand(),
                p.getWeight(),
                p.getUnit(),
                p.getExpirationDate(),
                p.getPrice(),
                p.getOnSale()
        );
    }

    private Product toEntity(ProductDTO dto) {
        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setBrand(dto.getBrand());
        p.setWeight(dto.getWeight());
        p.setUnit(dto.getUnit());
        p.setExpirationDate(dto.getExpirationDate());
        p.setPrice(dto.getPrice());
        p.setOnSale(dto.getOnSale());
        return p;
    }
}
