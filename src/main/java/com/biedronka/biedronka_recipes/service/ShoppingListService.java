package com.biedronka.biedronka_recipes.service;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Product;

import com.biedronka.biedronka_recipes.dto.PromotionDTO;
import com.biedronka.biedronka_recipes.dto.shoppingList.ShoppingListItemResponseDTO;
import com.biedronka.biedronka_recipes.entity.ShoppingListItem;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import com.biedronka.biedronka_recipes.utils.ShoppingListItemMapper;
import com.biedronka.biedronka_recipes.repository.ShoppingListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShoppingListService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ShoppingListItemRepository shoppingListItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingListItemMapper shoppingListItemMapper;

    private final BiedronkaPromoService biedronkaPromoService = new BiedronkaPromoService();

    public List<ShoppingListItemResponseDTO> getActiveShoppingList(Long userId) {
        List<ShoppingListItem> shoppingList = shoppingListItemRepository.findByClientIdAndConfirmationDateIsNull(userId);
        return shoppingListItemMapper.toShoppingListItemResponseDTOList(shoppingList);
    }

    public void confirmShoppingList(Long userId) {
        List<ShoppingListItem> activeShoppingList = shoppingListItemRepository.findByClientIdAndConfirmationDateIsNull(userId);
        activeShoppingList.forEach(shoppingListItem -> {
            shoppingListItem.setConfirmationDate(LocalDate.now());
            shoppingListItemRepository.save(shoppingListItem);
        });
    }

    public void addToShoppingList(Long clientId, Long productId,Double amount) {
        Client client = clientRepository.getReferenceById(clientId);
        Product product = productRepository.getReferenceById(productId);
        ShoppingListItem existing = shoppingListItemRepository.findByClientAndProduct(client, product);
        int amountInt = (int) Math.round(amount);
        if(existing != null) {
            existing.setQuantity(existing.getQuantity() + amountInt);
            shoppingListItemRepository.save(existing);
        } else {
            ShoppingListItem newItem = ShoppingListItem.builder()
                    .client(client)
                    .product(product)
                    .quantity(amountInt)
                    .build();
            shoppingListItemRepository.save(newItem);
        }
    }


    public void applyPromo(Long shoppingListId) {
        ShoppingListItem shoppingListItem = shoppingListItemRepository.findById(shoppingListId).orElse(null);
        if (shoppingListItem != null) {
            PromotionDTO itemPromo = biedronkaPromoService.getPromotions().get(shoppingListItem.getProduct().getId());
            if (itemPromo != null) {
                int itemQuantity = shoppingListItem.getQuantity();
                while (itemQuantity % itemPromo.quantityRequired() != 0) {
                    itemQuantity += 1;
                    shoppingListItem.setQuantity(itemQuantity);
                }
                shoppingListItem.setFinalPrice(shoppingListItem.getQuantity() * itemPromo.promoPricePerUnit());
                shoppingListItem.setPromo(true);
                shoppingListItemRepository.save(shoppingListItem);
            }
        }
    }


    public Double calculateShoppingListPrice(Long userId) {
        double price = 0.0;
        List<ShoppingListItem> activeShoppingList = shoppingListItemRepository.findByClientIdAndConfirmationDateIsNull(userId);
        for (ShoppingListItem shoppingListItem : activeShoppingList) {
            price += shoppingListItem.getFinalPrice();
        }
        return price;
    }


    public void removeItem(Long itemId) {
        ShoppingListItem shoppingListItem = shoppingListItemRepository.findById(itemId).orElse(null);
        if (shoppingListItem != null) {
            PromotionDTO itemPromotion = biedronkaPromoService.getPromotions().get(shoppingListItem.getProduct().getId());
            int quantity = shoppingListItem.getQuantity();
            if (quantity > 1) {
                int newQuantity = quantity - 1;
                if (itemPromotion != null && itemPromotion.quantityRequired() % newQuantity != 0) {
                    shoppingListItem.setFinalPrice(newQuantity * shoppingListItem.getProduct().getPrice());
                }
                shoppingListItem.setFinalPrice(newQuantity * shoppingListItem.getProduct().getPrice());
                shoppingListItem.setQuantity(newQuantity);
                shoppingListItemRepository.save(shoppingListItem);
            } else {
                shoppingListItemRepository.delete(shoppingListItem);
            }
        }
        // else
    }
}
