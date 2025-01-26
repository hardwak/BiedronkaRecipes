package com.biedronka.biedronka_recipes.service;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import com.biedronka.biedronka_recipes.entity.ShoppingListItem;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import com.biedronka.biedronka_recipes.repository.ShoppingListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingListService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ShoppingListItemRepository shoppingListItemRepository;
    @Autowired
    private ProductRepository productRepository;

    public void addToShoppingList(Long clientId, Long productId,Double amount) {
        Client client = clientRepository.getReferenceById(clientId);
        Product product = productRepository.getReferenceById(productId);
        ShoppingListItem existing = shoppingListItemRepository.findByClientAndProduct(client, product);

        if(existing != null) {
            existing.setQuantity(existing.getQuantity() + amount);
            shoppingListItemRepository.save(existing);
        } else {
            ShoppingListItem newItem = ShoppingListItem.builder()
                    .client(client)
                    .product(product)
                    .quantity(amount)
                    .build();
            shoppingListItemRepository.save(newItem);
        }




    }





}
