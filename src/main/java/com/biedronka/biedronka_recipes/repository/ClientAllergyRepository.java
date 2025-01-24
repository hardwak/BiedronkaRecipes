package com.biedronka.biedronka_recipes.repository;


import com.biedronka.biedronka_recipes.entity.ClientAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ClientAllergyRepository extends JpaRepository<ClientAllergy,Long> {
    // jeśli klucz kompozytowy -> EmbeddedId
}

