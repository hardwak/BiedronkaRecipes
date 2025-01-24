package com.biedronka.biedronka_recipes.repository;


import com.biedronka.biedronka_recipes.entity.ProductClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductClientRepository extends JpaRepository<ProductClient, Long> {
}
