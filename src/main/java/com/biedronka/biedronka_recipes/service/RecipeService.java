package com.biedronka.biedronka_recipes.service;


import com.biedronka.biedronka_recipes.dto.ConfirmCookingRequestDTO;
import com.biedronka.biedronka_recipes.dto.CookingResultDTO;
import com.biedronka.biedronka_recipes.dto.CookingResultDTO;
import com.biedronka.biedronka_recipes.entity.*;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        recipe.getComments().add(comment);
        recipeRepository.save(recipe);
    }

    public void rateRecipe(Long recipeId, Long clientId, double rating) {
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        Client client = clientRepository.getReferenceById(clientId);


        RecipeRate recipeRate = new RecipeRate();
        recipeRate.setClient(client);
        recipeRate.setRecipe(recipe);
        recipeRate.setRate(rating);

        recipe.getRecipeRates().add(recipeRate);
        recipeRepository.save(recipe);
    }

    public CookingResultDTO startCooking(Long recipeId, Long clientId) {
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        Client client = clientRepository.getReferenceById(clientId);

        List<StoreroomItem> client_storeroomItems = client.getStoreroomItems();
        List<RecipeProducts> recipe_items = recipe.getRecipeProducts();

        HashMap<RecipeProducts,Double> missingProducts = findMissingProducts(client_storeroomItems, recipe_items);

        if (!missingProducts.isEmpty()) {
            // Krok 7: Informuj klienta o brakach
            String missingInfo = getMissingInfo(missingProducts);
            return new CookingResultDTO(false, missingProducts, missingInfo);
        }


        clientRepository.save(client);

        return new CookingResultDTO(true, null, "All ingredients available.");



    }

    public String confirmCooking(ConfirmCookingRequestDTO requestDTO) {
        Long recipeId = requestDTO.getRecipeId();
        Long clientId = requestDTO.getClientId();
        HashMap<RecipeProducts,Double> missingProducts = requestDTO.getMissingProducts();
        Recipe recipe = recipeRepository.getReferenceById(recipeId);
        Client client = clientRepository.getReferenceById(clientId);
        List<StoreroomItem> client_storeroomItems = client.getStoreroomItems();
        List<RecipeProducts> recipe_items = recipe.getRecipeProducts();

        for (Map.Entry<RecipeProducts, Double> entry : missingProducts.entrySet()) {
            RecipeProducts recipeProducts = entry.getKey();
            Product product = recipeProducts.getProduct();
            Double amount = entry.getValue();
            shoppingListService.addToShoppingList(clientId, product, amount);

        }
        updateStoreroom(client_storeroomItems, recipe_items);

        return "Cooking confirmed, storeroom updated. Shopping list updated";
    }

    private HashMap<RecipeProducts,Double> findMissingProducts(List<StoreroomItem> storeroom, List<RecipeProducts> recipeItems) {
        // Dla przejrzystości można to zrobić wprost:
        HashMap<RecipeProducts,Double> missingMap = new HashMap<>();

        for (RecipeProducts needed : recipeItems) {
            boolean found = false;
            double need_amount = needed.getAmount();
            for (StoreroomItem have : storeroom) {
                if (have.getProduct().getId().equals(needed.getProduct().getId())) {
                    // Ten sam produkt
                    double have_amount = have.getAmount();

                    if (have_amount > need_amount) {
                        // Ilość w spiżarni jest wystarczająca
                        found = true;
                        break;
                    } else {
                        // Produkt jest, ale ilość niewystarczająca
                        double toBuy_amount = need_amount - have_amount;
                        missingMap.put(needed,toBuy_amount);
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                // W spiżarni w ogóle nie ma takiego produktu
                missingMap.put(needed,need_amount);
            }
        }
        return missingMap;
    }

    private void updateStoreroom(List<StoreroomItem> storeroom, List<RecipeProducts> recipeItems) {
        for (RecipeProducts needed : recipeItems) {
            for (StoreroomItem have : storeroom) {
                if (have.getProduct().getId().equals(needed.getProduct().getId())) {
                    // Zmniejszamy ilość w spiżarni
                    double newQuantity = have.getAmount() - needed.getAmount();
                    have.setAmount(Math.max(newQuantity, 0)); // na wszelki wypadek nie schodzimy poniżej 0

                    break;
                }
            }
        }
    }

    private String getMissingInfo(HashMap<RecipeProducts,Double> missingProducts) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<RecipeProducts, Double> entry : missingProducts.entrySet()) {
            RecipeProducts rp = entry.getKey();
            Double amount = entry.getValue();
            sb.append("- ")
                    .append(rp.getProduct().getName())
                    .append(" (potrzebne: ")
                    .append(amount)
                    .append(")\n");
        }

        return sb.toString();
    }


}
