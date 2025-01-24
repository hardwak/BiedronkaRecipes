package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.ShoppingListItemDTO;
import com.biedronka.biedronka_recipes.entity.ShoppingListItem;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.service.ShoppingListItemService;
import com.biedronka.biedronka_recipes.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shopping-list")
public class ShoppingListItemController {

    private final ShoppingListItemService shoppingListItemService;
    private final ClientService clientService;

    public ShoppingListItemController(ShoppingListItemService shoppingListItemService,
                                      ClientService clientService) {
        this.shoppingListItemService = shoppingListItemService;
        this.clientService = clientService;
    }

    @GetMapping("/{clientId}")
    public List<ShoppingListItemDTO> getAllForClient(@PathVariable Long clientId) {
        // e.g. shoppingListItemService.findByOwner(clientId)
        return shoppingListItemService.findByClientId(clientId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/{clientId}")
    public ShoppingListItemDTO create(@PathVariable Long clientId,
                                      @RequestBody ShoppingListItemDTO dto) {
        Client client = clientService.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        ShoppingListItem item = new ShoppingListItem();
        item.setProductName(dto.getProductName());
        item.setQuantity(dto.getQuantity());
        item.setUnit(dto.getUnit());
        item.setPromotional(dto.isPromotional());
        item.setOwner(client);

        ShoppingListItem saved = shoppingListItemService.save(item);
        return toDTO(saved);
    }

    @DeleteMapping("/item/{itemId}")
    public void delete(@PathVariable Long itemId) {
        shoppingListItemService.delete(itemId);
    }

    // mapowanie
    private ShoppingListItemDTO toDTO(ShoppingListItem sli) {
        return new ShoppingListItemDTO(
                sli.getId(),
                sli.getProductName(),
                sli.getQuantity(),
                sli.getUnit(),
                sli.isPromotional(),
                sli.getOwner() != null ? sli.getOwner().getId() : null
        );
    }
}
