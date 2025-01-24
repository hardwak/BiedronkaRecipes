package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {
}

