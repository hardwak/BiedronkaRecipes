package com.biedronka.biedronka_recipes.repository;


import com.biedronka.biedronka_recipes.entity.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {

}

