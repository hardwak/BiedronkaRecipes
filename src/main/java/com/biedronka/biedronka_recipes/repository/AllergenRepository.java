package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long> {
}
