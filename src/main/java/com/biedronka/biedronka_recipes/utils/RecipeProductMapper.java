package com.biedronka.biedronka_recipes.utils;

import com.biedronka.biedronka_recipes.dto.RecipeProductOnlyIdDTO;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeProductMapper {

    public static List<RecipeProducts> mapToEntityList(List<RecipeProductOnlyIdDTO> dtoList, ProductRepository productRepository) {
        return dtoList.stream()
                .map(dto -> mapToEntity(dto, productRepository))
                .collect(Collectors.toList());
    }

    private static RecipeProducts mapToEntity(RecipeProductOnlyIdDTO dto, ProductRepository productRepository) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + dto.getProductId() + " not found"));
        System.out.println("Product: " + product.getId());
        return RecipeProducts.builder()
                .amount(dto.getAmount())
                .weightUnit("kg") // Możesz zmienić na dynamiczne przypisanie jednostki
                .product(product)
                .build();
    }
    public static List<RecipeProductOnlyIdDTO> mapToDtoList(List<RecipeProducts> entityList) {
        return entityList.stream()
                .map(entity -> mapToDTO(entity))
                .collect(Collectors.toList());
    }

    private static RecipeProductOnlyIdDTO mapToDTO(RecipeProducts entity) {
        if (entity == null){
            return null;
        }
        return new RecipeProductOnlyIdDTO(entity.getProduct().getId(), entity.getAmount());

    }
}
