package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.entity.Allergen;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.ProductAllergen;
import com.biedronka.biedronka_recipes.repository.AllergenRepository;
import com.biedronka.biedronka_recipes.repository.ProductAllergenRepository;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductAllergenService {

    private final ProductAllergenRepository productAllergenRepository;
    private final ProductRepository productRepository;
    private final AllergenRepository allergenRepository;

    public ProductAllergenService(ProductAllergenRepository productAllergenRepository,
                                  ProductRepository productRepository,
                                  AllergenRepository allergenRepository) {
        this.productAllergenRepository = productAllergenRepository;
        this.productRepository = productRepository;
        this.allergenRepository = allergenRepository;
    }

    public List<ProductAllergen> findAll() {
        return productAllergenRepository.findAll();
    }

    public Optional<ProductAllergen> findById(Long id) {
        return productAllergenRepository.findById(id);
    }

    public ProductAllergen create(Long productId, Long allergenId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Allergen allergen = allergenRepository.findById(allergenId)
                .orElseThrow(() -> new RuntimeException("Allergen not found"));

        ProductAllergen pa = new ProductAllergen();
        pa.setProduct(product);
        pa.setAllergen(allergen);
        return productAllergenRepository.save(pa);
    }

    public void delete(Long id) {
        productAllergenRepository.deleteById(id);
    }
}

