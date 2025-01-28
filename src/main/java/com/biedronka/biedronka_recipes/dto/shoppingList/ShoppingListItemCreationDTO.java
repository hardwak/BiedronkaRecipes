package com.biedronka.biedronka_recipes.dto.shoppingList;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record ShoppingListItemCreationDTO (
    @NotNull Integer quantity,
    @NotNull Double finalPrice,
    LocalDate confirmationDate,
    @NotNull Long clientId,
    @NotNull Long productId
    ) {

}
