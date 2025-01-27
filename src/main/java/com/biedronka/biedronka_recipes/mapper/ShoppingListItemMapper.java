package com.biedronka.biedronka_recipes.mapper;

import com.biedronka.biedronka_recipes.dto.PromotionDTO;
import com.biedronka.biedronka_recipes.dto.shoppingList.ShoppingListItemCreationDTO;
import com.biedronka.biedronka_recipes.dto.shoppingList.ShoppingListItemResponseDTO;
import com.biedronka.biedronka_recipes.entity.ShoppingListItem;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import com.biedronka.biedronka_recipes.service.BiedronkaPromoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShoppingListItemMapper {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final BiedronkaPromoService biedronkaPromoService;

    public ShoppingListItemMapper(ProductRepository productRepository, ClientRepository clientRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.biedronkaPromoService = new BiedronkaPromoService();
    }

    public ShoppingListItem toShoppingListItem(ShoppingListItemCreationDTO dto) {
        return ShoppingListItem.builder()
                .quantity(dto.quantity())
                .finalPrice(dto.finalPrice())
                .confirmationDate(dto.confirmationDate())
                .product(productRepository.findById(dto.productId()).orElse(null))
                .client(clientRepository.findById(dto.clientId()).orElse(null))
                .promo(Boolean.FALSE)
                .build();
    }

    public ShoppingListItemResponseDTO toShoppingListItemResponseDTO(ShoppingListItem shoppingListItem, PromotionDTO promotionDTO) {
        return new ShoppingListItemResponseDTO(
                shoppingListItem.getId(),
                shoppingListItem.getQuantity(),
                shoppingListItem.getFinalPrice(),
                shoppingListItem.getConfirmationDate(),
                shoppingListItem.getClient().getId(),
                shoppingListItem.getProduct().getId(),
                shoppingListItem.getProduct().getName(),
                shoppingListItem.getProduct().getBrand(),
                shoppingListItem.getProduct().getPrice(),
                promotionDTO != null, //shoppingListItem.getPromo(),
                shoppingListItem.getProduct().getMultimedia().getUrl(),
                promotionDTO == null ? "" : promotionDTO.promotionName()
        );
    }

    public List<ShoppingListItemResponseDTO> toShoppingListItemResponseDTOList(List<ShoppingListItem> shoppingListItems) {
        Map<Long, PromotionDTO> promotions = biedronkaPromoService.getPromotions();
        return shoppingListItems.stream()
                .map(item -> {
                    PromotionDTO promotion = promotions.get(item.getProduct().getId());
                    return toShoppingListItemResponseDTO(item, promotion);
                })
                .toList();
    }
}
