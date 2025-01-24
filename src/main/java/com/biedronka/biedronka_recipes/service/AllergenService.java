package com.biedronka.biedronka_recipes.service;



import com.biedronka.biedronka_recipes.entity.Allergen;
import com.biedronka.biedronka_recipes.repository.AllergenRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllergenService {

    private final AllergenRepository allergenRepository;

    public AllergenService(AllergenRepository allergenRepository) {
        this.allergenRepository = allergenRepository;
    }

    public List<Allergen> findAll() {
        return allergenRepository.findAll();
    }

    public Optional<Allergen> findById(Long id) {
        return allergenRepository.findById(id);
    }

    public Allergen create(Allergen recipe) {
        // np. weryfikacja
        return allergenRepository.save(recipe);
    }

    public Allergen update(Allergen recipe) {
        return allergenRepository.save(recipe);
    }

    public void delete(Long id) {
        allergenRepository.deleteById(id);
    }
}
