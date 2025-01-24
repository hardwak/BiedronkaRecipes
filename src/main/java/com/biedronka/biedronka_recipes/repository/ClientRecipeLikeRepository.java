package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.ClientRecipeLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRecipeLikeRepository extends JpaRepository<ClientRecipeLikes, Long> {
}

