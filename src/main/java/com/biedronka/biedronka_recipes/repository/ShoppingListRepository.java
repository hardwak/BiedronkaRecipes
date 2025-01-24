package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

}

