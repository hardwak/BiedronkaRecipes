package com.biedronka.biedronka_recipes.service;



import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.ClientRecipeLikes;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.repository.ClientRecipeLikeRepository;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientRecipeLikeService {

    private final ClientRecipeLikeRepository likeRepository;
    private final ClientRepository clientRepository;
    private final RecipeRepository recipeRepository;

    public ClientRecipeLikeService(ClientRecipeLikeRepository likeRepository,
                                   ClientRepository clientRepository,
                                   RecipeRepository recipeRepository) {
        this.likeRepository = likeRepository;
        this.clientRepository = clientRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<ClientRecipeLikes> findAll() {
        return likeRepository.findAll();
    }

    public Optional<ClientRecipeLikes> findById(Long id) {
        return likeRepository.findById(id);
    }

    public ClientRecipeLikes create(Long clientId, Long recipeId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        ClientRecipeLikes like = new ClientRecipeLikes();
        like.setClient(client);
        like.setRecipe(recipe);
        return likeRepository.save(like);
    }

    public void delete(Long id) {
        likeRepository.deleteById(id);
    }
}
