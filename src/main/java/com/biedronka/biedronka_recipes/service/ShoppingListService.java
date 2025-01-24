package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.ShoppingList;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import com.biedronka.biedronka_recipes.repository.ShoppingListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository,
                               ClientRepository clientRepository,
                               ProductRepository productRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public List<ShoppingList> findAll() {
        return shoppingListRepository.findAll();
    }

    public Optional<ShoppingList> findById(Long id) {
        return shoppingListRepository.findById(id);
    }

    public ShoppingList create(ShoppingList sl) {
        return shoppingListRepository.save(sl);
    }

    public ShoppingList update(ShoppingList sl) {
        return shoppingListRepository.save(sl);
    }

    public void delete(Long id) {
        shoppingListRepository.deleteById(id);
    }

    /**
     * Przykład bardziej "wysokopoziomowego" tworzenia rekordu,
     * gdzie przekazywane są ID klienta i produktu, a w serwisie
     * pobieramy odpowiednie encje.
     */
    public ShoppingList createWithIds(Long clientId, Long productId,
                                      Integer quantity, Double finalPrice) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID = " + clientId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID = " + productId));

        ShoppingList sl = new ShoppingList();
        sl.setClient(client);
        sl.setProduct(product);
        sl.setQuantity(quantity);
        sl.setFinalPrice(finalPrice);

        return shoppingListRepository.save(sl);
    }
}
