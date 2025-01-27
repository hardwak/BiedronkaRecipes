package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
    List<ShoppingListItem> findByClientIdAndConfirmationDateIsNull(Long clientId);
    List<ShoppingListItem> findByClientIdAndConfirmationDateIsNotNull(Long clientId);
}
