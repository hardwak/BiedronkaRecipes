package com.biedronka.biedronka_recipes;

import com.biedronka.biedronka_recipes.dto.PromotionDTO;
import com.biedronka.biedronka_recipes.dto.shoppingList.ShoppingListItemResponseDTO;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.ShoppingListItem;
import com.biedronka.biedronka_recipes.utils.ShoppingListItemMapper;
import com.biedronka.biedronka_recipes.repository.ShoppingListItemRepository;
import com.biedronka.biedronka_recipes.service.BiedronkaPromoService;
import com.biedronka.biedronka_recipes.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ShoppingListServiceTest {

    private ShoppingListItemRepository shoppingListItemRepository;
    private ShoppingListItemMapper shoppingListItemMapper;
    private BiedronkaPromoService biedronkaPromoService;
    private ShoppingListService shoppingListService;

    @BeforeEach
    void setUp() {
        shoppingListItemRepository = mock(ShoppingListItemRepository.class);
        shoppingListItemMapper = mock(ShoppingListItemMapper.class);
        biedronkaPromoService = mock(BiedronkaPromoService.class);

        shoppingListService = new ShoppingListService(shoppingListItemRepository, shoppingListItemMapper);
    }

    @Test
    void shouldReturnActiveShoppingList() {
        // Given
        Long userId = 1L;
        List<ShoppingListItem> mockShoppingList = List.of(new ShoppingListItem());
        List<ShoppingListItemResponseDTO> mockResponseDTOs = List.of(
                new ShoppingListItemResponseDTO(
                        1L, // id
                        2, // quantity
                        20.0, // finalPrice
                        LocalDate.now(), // confirmationDate
                        userId, // clientId
                        100L, // productId
                        "Test Product", // productName
                        "Test Brand", // productBrand
                        10.0, // productPrice
                        false, // promo
                        "http://example.com/image.jpg", // productMultimediaUrl
                        null // promoName
                )
        );

        when(shoppingListItemRepository.findByClientIdAndConfirmationDateIsNull(userId)).thenReturn(mockShoppingList);
        when(shoppingListItemMapper.toShoppingListItemResponseDTOList(mockShoppingList)).thenReturn(mockResponseDTOs);

        // When
        List<ShoppingListItemResponseDTO> result = shoppingListService.getActiveShoppingList(userId);

        // Then
        assertThat(result).isEqualTo(mockResponseDTOs);
        verify(shoppingListItemRepository, times(1)).findByClientIdAndConfirmationDateIsNull(userId);
        verify(shoppingListItemMapper, times(1)).toShoppingListItemResponseDTOList(mockShoppingList);
    }


    @Test
    void shouldConfirmShoppingList() {
        // Given
        Long userId = 1L;
        ShoppingListItem item1 = new ShoppingListItem();
        ShoppingListItem item2 = new ShoppingListItem();

        List<ShoppingListItem> mockShoppingList = List.of(item1, item2);
        when(shoppingListItemRepository.findByClientIdAndConfirmationDateIsNull(userId)).thenReturn(mockShoppingList);

        // When
        shoppingListService.confirmShoppingList(userId);

        // Then
        assertThat(item1.getConfirmationDate()).isEqualTo(LocalDate.now());
        assertThat(item2.getConfirmationDate()).isEqualTo(LocalDate.now());

        verify(shoppingListItemRepository, times(2)).save(any(ShoppingListItem.class));
    }

    @Test
    void shouldApplyPromotion() {
        // Given
        Long shoppingListId = 1L;
        Product product = new Product();
        product.setId(4L);
        product.setPrice(20.0);

        ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setProduct(product);
        shoppingListItem.setQuantity(2);
        shoppingListItem.setFinalPrice(100.0);

        PromotionDTO promotionDTO = new PromotionDTO(4L, "3 w cenie 2", 3, 5.0);

        when(shoppingListItemRepository.findById(shoppingListId)).thenReturn(Optional.of(shoppingListItem));
        when(biedronkaPromoService.getPromotions()).thenReturn(Map.of(product.getId(), promotionDTO));

        // When
        shoppingListService.applyPromo(shoppingListId);

        // Then
        ArgumentCaptor<ShoppingListItem> captor = ArgumentCaptor.forClass(ShoppingListItem.class);
        verify(shoppingListItemRepository).save(captor.capture());

        ShoppingListItem updatedItem = captor.getValue();
        assertThat(updatedItem.getQuantity()).isEqualTo(3);
        assertThat(updatedItem.getFinalPrice()).isEqualTo(15.0);
        assertThat(updatedItem.getPromo()).isTrue();
    }

    @Test
    void shouldCalculateShoppingListPrice() {
        // Given
        Long userId = 1L;
        ShoppingListItem item1 = new ShoppingListItem();
        item1.setFinalPrice(20.0);

        ShoppingListItem item2 = new ShoppingListItem();
        item2.setFinalPrice(30.0);

        List<ShoppingListItem> mockShoppingList = List.of(item1, item2);
        when(shoppingListItemRepository.findByClientIdAndConfirmationDateIsNull(userId)).thenReturn(mockShoppingList);

        // When
        Double result = shoppingListService.calculateShoppingListPrice(userId);

        // Then
        assertThat(result).isEqualTo(50.0);
    }

    @Test
    void shouldRemoveItem() {
        // Given
        Long itemId = 1L;
        Product product = new Product();
        product.setPrice(10.0);
        product.setId(100L);

        ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setProduct(product);
        shoppingListItem.setQuantity(2);
        shoppingListItem.setFinalPrice(20.0);

        when(shoppingListItemRepository.findById(itemId)).thenReturn(Optional.of(shoppingListItem));
        //when(biedronkaPromoService.getPromotions()).thenReturn(Map.of(product.getId(), null));

        // When
        shoppingListService.removeItem(itemId);

        // Then
        verify(shoppingListItemRepository, times(1)).save(any(ShoppingListItem.class));
        assertThat(shoppingListItem.getQuantity()).isEqualTo(1);
        assertThat(shoppingListItem.getFinalPrice()).isEqualTo(10.0);
    }
}

