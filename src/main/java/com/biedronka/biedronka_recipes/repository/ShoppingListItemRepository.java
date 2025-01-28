package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {


    ShoppingListItem findByClientAndProduct(Client client, Product product);
    List<ShoppingListItem> findByClientIdAndConfirmationDateIsNull(Long clientId);
    List<ShoppingListItem> findByClientIdAndConfirmationDateIsNotNull(Long clientId);
}
