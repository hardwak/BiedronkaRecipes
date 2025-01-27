package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.dto.PromotionDTO;
import com.biedronka.biedronka_recipes.dto.shoppingList.ShoppingListItemResponseDTO;
import com.biedronka.biedronka_recipes.entity.ShoppingListItem;
import com.biedronka.biedronka_recipes.mapper.ShoppingListItemMapper;
import com.biedronka.biedronka_recipes.repository.ShoppingListItemRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class ShoppingListService {

    private final ShoppingListItemRepository shoppingListItemRepository;
    private final ShoppingListItemMapper shoppingListItemMapper;
    private final BiedronkaPromoService biedronkaPromoService;

    public ShoppingListService(ShoppingListItemRepository shoppingListItemRepository, ShoppingListItemMapper shoppingListItemMapper) {
        this.shoppingListItemRepository = shoppingListItemRepository;
        this.shoppingListItemMapper = shoppingListItemMapper;
        this.biedronkaPromoService = new BiedronkaPromoService();
    }

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

    public void applyPromo(Long shoppingListId) {
        System.out.println("apply promo");
        ShoppingListItem shoppingListItem = shoppingListItemRepository.findById(shoppingListId).orElse(null);
        System.out.println(shoppingListItem);
        if (shoppingListItem != null) {
            System.out.println(biedronkaPromoService.getPromotions());
            PromotionDTO itemPromo = biedronkaPromoService.getPromotions().get(shoppingListItem.getProduct().getId());
            System.out.println(itemPromo);
            if (itemPromo != null) {
                int itemQuantity = shoppingListItem.getQuantity();
                while (itemQuantity % itemPromo.quantityRequired() != 0) {
                    itemQuantity += 1;
                    shoppingListItem.setQuantity(itemQuantity);
                }
                shoppingListItem.setFinalPrice(shoppingListItem.getQuantity() * itemPromo.promoPricePerUnit());
                shoppingListItem.setPromo(true);
                System.out.println(shoppingListItem);
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
