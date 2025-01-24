package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeProduct;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import com.biedronka.biedronka_recipes.repository.RecipeProductRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeProductService {

    private final RecipeProductRepository recipeProductRepository;
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    public RecipeProductService(RecipeProductRepository recipeProductRepository,
                                RecipeRepository recipeRepository,
                                ProductRepository productRepository) {
        this.recipeProductRepository = recipeProductRepository;
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
    }

    public List<RecipeProduct> findAll() {
        return recipeProductRepository.findAll();
    }

    public Optional<RecipeProduct> findById(Long id) {
        return recipeProductRepository.findById(id);
    }

    public RecipeProduct create(Long recipeId, Long productId, Double amount) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RecipeProduct rp = new RecipeProduct();
        rp.setRecipe(recipe);
        rp.setProduct(product);
        rp.setAmount(amount != null ? amount : 1.0);
        return recipeProductRepository.save(rp);
    }

    public void delete(Long id) {
        recipeProductRepository.deleteById(id);
    }
}

