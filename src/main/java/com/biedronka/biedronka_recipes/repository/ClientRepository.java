package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
