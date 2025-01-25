package com.biedronka.biedronka_recipes.service;


import com.biedronka.biedronka_recipes.dto.*;
import com.biedronka.biedronka_recipes.dto.CookingResultDTO;
import com.biedronka.biedronka_recipes.entity.*;
import com.biedronka.biedronka_recipes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ShoppingListService shoppingListService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RecipeRateRepository recipeRateRepository;
    @Autowired
    private StoreroomItemRepository storeroomItemRepository;

    public void likeRecipe(Long recipeId, Long clientId) {
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        Client client = clientRepository.getReferenceById(clientId);
        if (!client.getFavoriteRecipes().contains(recipe)) {
            client.getFavoriteRecipes().add(recipe);
            clientRepository.save(client); // Zapisujemy zmienionego klienta
        }
        recipeRepository.save(recipe);
    }

    public void unlikeRecipe(Long recipeId, Long clientId) {
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        Client client = clientRepository.getReferenceById(clientId);

        // Usuwamy przepis z ulubionych
        if (client.getFavoriteRecipes().contains(recipe)) {
            client.getFavoriteRecipes().remove(recipe);
            clientRepository.save(client);
        }
    }

    public void addComment(Long recipeId, Long clientId, String commentText) {
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        Client client = clientRepository.getReferenceById(clientId);


        Comment comment = new Comment();
        comment.setClient(client);
        comment.setRecipe(recipe);
        comment.setComment(commentText);
        comment.setCreatedAt(LocalDateTime.now());
        recipe.getComments().add(comment);
        commentRepository.save(comment);
        recipeRepository.save(recipe);
    }

    public void rateRecipe(Long recipeId, Long clientId, double rating) {
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        Client client = clientRepository.getReferenceById(clientId);

        // Znajdź istniejącą ocenę
        RecipeRate existingRate = recipeRateRepository.findByRecipeAndClient(recipe, client);

        if (existingRate != null) {
            // Aktualizuj istniejącą ocenę
            existingRate.setRate(rating);
            recipeRateRepository.save(existingRate);
        } else {
            // Dodaj nową ocenę, jeśli nie istnieje
            RecipeRate newRate = new RecipeRate();
            newRate.setClient(client);
            newRate.setRecipe(recipe);
            newRate.setRate(rating);

            recipeRateRepository.save(newRate);
        }
    }
    public boolean hasUserLikedRecipe(Long recipeId, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika o ID: " + clientId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono przepisu o ID: " + recipeId));

        return client.getFavoriteRecipes().contains(recipe);
    }

    public CookingResultDTO startCooking(Long recipeId, Long clientId) {
        System.out.println("Recipe1: " + recipeId);
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        System.out.println("Recipe2: " + recipeId);
        Client client = clientRepository.getReferenceById(clientId);
        System.out.println("Recipe3: " + recipeId);

        List<StoreroomItem> client_storeroomItems = client.getStoreroomItems();
        System.out.println("Recipe4: " + recipeId);
        List<RecipeProducts> recipe_items = recipe.getRecipeProducts();
        System.out.println("Recipe5: " + recipeId);

        List<MissingProductDTO> missingProducts = findMissingProducts(client_storeroomItems, recipe_items);

        if (!missingProducts.isEmpty()) {
            // Krok 7: Informuj klienta o brakach
            String missingInfo = getMissingInfo(missingProducts);
            return new CookingResultDTO(false, missingProducts, missingInfo);
        }


        clientRepository.save(client);

        return new CookingResultDTO(true, null, "All ingredients available.");



    }

    public String confirmCooking(ConfirmCookingRequestDTO requestDTO) {
        System.out.println("ConfirmCooking: " + requestDTO);
        Long recipeId = requestDTO.getRecipeId();
        Long clientId = requestDTO.getClientId();
        List<MissingProductDTO> missingProducts = requestDTO.getMissingProducts();
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        Client client = clientRepository.getReferenceById(clientId);
        List<StoreroomItem> client_storeroomItems = client.getStoreroomItems();
        List<RecipeProducts> recipe_items = recipe.getRecipeProducts();

        for (MissingProductDTO missingProductDTO : missingProducts) {
            System.out.println("MissingProductDTO: " + missingProductDTO);
            RecipeProductsDTO recipeProducts = missingProductDTO.getRecipeProductsDTO();
            Long productId = recipeProducts.getProductId();
            Double amount = missingProductDTO.getMissingAmount();
            shoppingListService.addToShoppingList(clientId, productId, amount);

        }
        updateStoreroom(client_storeroomItems, recipe_items);

        return "Cooking confirmed, storeroom updated. Shopping list updated";
    }
    public Double getAverageRating(Long recipeId) {
        Double averageRating = recipeRepository.findAverageRatingByRecipeId(recipeId);
        return averageRating != null ? Math.round(averageRating * 10.0) / 10.0 : 0.0;
    }

    private List<MissingProductDTO> findMissingProducts(List<StoreroomItem> storeroom, List<RecipeProducts> recipeItems) {
        // Dla przejrzystości można to zrobić wprost:
        List<MissingProductDTO> missingProducts = new ArrayList<>();

        for (RecipeProducts needed : recipeItems) {
            boolean found = false;
            double need_amount = needed.getAmount();
            for (StoreroomItem have : storeroom) {
                if (have.getProduct().getId().equals(needed.getProduct().getId())) {
                    // Ten sam produkt
                    double have_amount = have.getAmount();

                    if (have_amount >= need_amount) {
                        // Ilość w spiżarni jest wystarczająca
                        found = true;
                        break;
                    } else {
                        // Produkt jest, ale ilość niewystarczająca
                        double toBuy_amount = need_amount - have_amount;
                        MissingProductDTO dto = new MissingProductDTO(
                                 new RecipeProductsDTO(
                                        needed.getId(),
                                        needed.getAmount(),
                                        needed.getWeightUnit(),
                                        needed.getRecipe().getId(),
                                        needed.getProduct().getId(),
                                         needed.getProduct().getName()
                        ),
                                toBuy_amount
                        );
                        missingProducts.add(dto);
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                // W spiżarni w ogóle nie ma takiego produktu
                MissingProductDTO dto = new MissingProductDTO(
                        new RecipeProductsDTO(
                                needed.getId(),
                                needed.getAmount(),
                                needed.getWeightUnit(),
                                needed.getRecipe().getId(),
                                needed.getProduct().getId(),
                                needed.getProduct().getName()
                        ),
                        need_amount
                );
                missingProducts.add(dto);
            }
        }
        return missingProducts;
    }

    private void updateStoreroom(List<StoreroomItem> storeroom, List<RecipeProducts> recipeItems) {
        for (RecipeProducts needed : recipeItems) {
            for (StoreroomItem have : storeroom) {
                if (have.getProduct().getId().equals(needed.getProduct().getId())) {
                    System.out.println("Updating storeroom: " + have.getProduct().getName());
                    System.out.println("Storeroom: " + have.getAmount());
                    System.out.println("Needed: " + needed.getAmount());
                    // Zmniejszamy ilość w spiżarni
                    double newQuantity = have.getAmount() - needed.getAmount();
                    have.setAmount(Math.max(newQuantity, 0)); // na wszelki wypadek nie schodzimy poniżej 0
                    storeroomItemRepository.save(have);

                    break;
                }
            }
        }
    }

    private String getMissingInfo(List<MissingProductDTO> missingProducts) {
        StringBuilder sb = new StringBuilder();
        for (MissingProductDTO missingProductDTO : missingProducts) {
            RecipeProductsDTO rpDTO = missingProductDTO.getRecipeProductsDTO();
            Double amount = missingProductDTO.getMissingAmount();
            sb.append("- ")
                    .append(rpDTO.getProductName())
                    .append(":   ")
                    .append(amount)
                    .append("\n");
        }

        return sb.toString();
    }


}
