package com.biedronka.biedronka_recipes.dto.shoppingList;

import java.time.LocalDate;

public record ShoppingListItemResponseDTO (
        Long id,
        Integer quantity,
        Double finalPrice,
        LocalDate confirmationDate,
        Long clientId,
        Long productId,
        String productName,
        String productBrand,
        Double productPrice,
        Boolean promo,
        String productMultimediaUrl,
        String promoName
    ) {

}
