package com.biedronka.biedronka_recipes.dto;

public record PromotionDTO (
        Long productId,
        String promotionName,
        Integer quantityRequired,
        Double promoPricePerUnit
) {

}
