package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.ShoppingListDTO;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.ShoppingList;
import com.biedronka.biedronka_recipes.service.ClientService;
import com.biedronka.biedronka_recipes.service.ProductService;
import com.biedronka.biedronka_recipes.service.ShoppingListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Kontroler do obsługi ShoppingLists
 */
@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final ClientService clientService;
    private final ProductService productService;

    public ShoppingListController(ShoppingListService shoppingListService,
                                  ClientService clientService,
                                  ProductService productService) {
        this.shoppingListService = shoppingListService;
        this.clientService = clientService;
        this.productService = productService;
    }

    /**
     * [GET] /shopping-lists
     * Zwraca wszystkie rekordy ShoppingList
     */
    @GetMapping
    public List<ShoppingListDTO> getAll() {
        return shoppingListService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * [GET] /shopping-lists/{id}
     * Zwraca 1 rekord z ShoppingList
     */
    @GetMapping("/{id}")
    public ShoppingListDTO getOne(@PathVariable Long id) {
        ShoppingList sl = shoppingListService.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingList not found, id=" + id));
        return toDTO(sl);
    }

    /**
     * [POST] /shopping-lists
     * Tworzy nowy rekord w ShoppingList
     */
    @PostMapping
    public ShoppingListDTO create(@RequestBody ShoppingListDTO dto) {
        // Odszukaj Client i Product
        Client client = clientService.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found, id=" + dto.getClientId()));
        Product product = productService.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found, id=" + dto.getProductId()));

        // Tworzymy encję
        ShoppingList sl = new ShoppingList();
        sl.setClient(client);
        sl.setProduct(product);
        sl.setQuantity(dto.getQuantity());
        sl.setFinalPrice(dto.getFinalPrice());
        sl.setConfirmationDate(dto.getConfirmationDate());

        ShoppingList saved = shoppingListService.create(sl);
        return toDTO(saved);
    }

    /**
     * [PUT] /shopping-lists/{id}
     * Aktualizuje istniejący rekord
     */
    @PutMapping("/{id}")
    public ShoppingListDTO update(@PathVariable Long id, @RequestBody ShoppingListDTO dto) {
        ShoppingList existing = shoppingListService.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingList not found, id=" + id));

        // Aktualizujemy pola
        if (dto.getClientId() != null) {
            Client client = clientService.findById(dto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found, id=" + dto.getClientId()));
            existing.setClient(client);
        }
        if (dto.getProductId() != null) {
            Product product = productService.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found, id=" + dto.getProductId()));
            existing.setProduct(product);
        }

        existing.setQuantity(dto.getQuantity());
        existing.setFinalPrice(dto.getFinalPrice());
        existing.setConfirmationDate(dto.getConfirmationDate());

        ShoppingList updated = shoppingListService.update(existing);
        return toDTO(updated);
    }

    /**
     * [DELETE] /shopping-lists/{id}
     * Usuwa rekord z ShoppingList
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        shoppingListService.delete(id);
    }

    // ---------------------
    // Metody pomocnicze
    // ---------------------

    private ShoppingListDTO toDTO(ShoppingList sl) {
        return new ShoppingListDTO(
                sl.getId(),
                sl.getClient() != null ? sl.getClient().getId() : null,
                sl.getProduct() != null ? sl.getProduct().getId() : null,
                sl.getQuantity(),
                sl.getFinalPrice(),
                sl.getConfirmationDate()
        );
    }
}
