package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.dto.PromotionDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiedronkaPromoService {

    public Map<Long, PromotionDTO> getPromotions() {
        Map<Long, PromotionDTO> promotions = new HashMap<>();
        promotions.put(1L, new PromotionDTO(1L, "Kup 2 i zapłać mniej: 3,99 przy zakupie 2. DRUGI TANIEJ O 46%", 2, 3.99));
        promotions.put(4L, new PromotionDTO(4L, "3 w cenie 2", 3, 5.0));
        return promotions;
    }
}
