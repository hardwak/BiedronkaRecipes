package com.biedronka.biedronka_recipes;



import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Comment;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeRate;
import com.biedronka.biedronka_recipes.repository.*;
import com.biedronka.biedronka_recipes.service.RecipeService;
import com.biedronka.biedronka_recipes.service.ShoppingListService;
import com.biedronka.biedronka_recipes.utils.RecipeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ShoppingListService shoppingListService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RecipeRateRepository recipeRateRepository;

    @Mock
    private StoreroomItemRepository storeroomItemRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MultimediaRepository multimediaRepository;

    @Mock
    private RecipeProductsRepository recipeProductsRepository;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe recipe;
    private Client client;

    @BeforeEach
    void setUp() {
        // Inicjalizacja przykładowego przepisu
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Przykładowy Przepis");
        recipe.setComments(new ArrayList<>());
        recipe.setRecipeProducts(new ArrayList<>());

        // Inicjalizacja przykładowego klienta
        client = new Client();
        client.setId(1L);
        client.setFavoriteRecipes(new ArrayList<>());
        client.setStoreroomItems(new ArrayList<>());
    }

    @Test
    void testLikeRecipe() {
        // Arrange
        Long recipeId = recipe.getId();
        Long clientId = client.getId();

        when(recipeRepository.getReferenceById(recipeId)).thenReturn(recipe);
        when(clientRepository.getReferenceById(clientId)).thenReturn(client);

        // Act
        recipeService.likeRecipe(recipeId, clientId);

        // Assert
        assertTrue(client.getFavoriteRecipes().contains(recipe), "Przepis powinien być dodany do ulubionych klienta.");
        verify(clientRepository, times(1)).save(client);
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void testUnlikeRecipe() {
        // Arrange
        Long recipeId = recipe.getId();
        Long clientId = client.getId();
        client.getFavoriteRecipes().add(recipe);

        when(recipeRepository.getReferenceById(recipeId)).thenReturn(recipe);
        when(clientRepository.getReferenceById(clientId)).thenReturn(client);

        // Act
        recipeService.unlikeRecipe(recipeId, clientId);

        // Assert
        assertFalse(client.getFavoriteRecipes().contains(recipe), "Przepis powinien zostać usunięty z ulubionych klienta.");
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testAddComment() {
        // Arrange
        Long recipeId = recipe.getId();
        Long clientId = client.getId();
        String commentText = "Świetny przepis!";

        when(recipeRepository.getReferenceById(recipeId)).thenReturn(recipe);
        when(clientRepository.getReferenceById(clientId)).thenReturn(client);

        // Act
        recipeService.addComment(recipeId, clientId, commentText);

        // Assert
        assertEquals(1, recipe.getComments().size(), "Przepis powinien mieć jeden komentarz.");
        Comment comment = recipe.getComments().get(0);
        assertEquals(commentText, comment.getComment(), "Tekst komentarza powinien być zgodny.");
        assertEquals(client, comment.getClient(), "Komentarz powinien być przypisany do odpowiedniego klienta.");
        assertNotNull(comment.getCreatedAt(), "Komentarz powinien mieć ustawioną datę utworzenia.");

        verify(commentRepository, times(1)).save(comment);
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void testRateRecipe_NewRate() {
        // Arrange
        Long recipeId = recipe.getId();
        Long clientId = client.getId();
        double rating = 4.5;

        when(recipeRepository.getReferenceById(recipeId)).thenReturn(recipe);
        when(clientRepository.getReferenceById(clientId)).thenReturn(client);
        //when(recipeRateRepository.findByRecipeAndClient(recipe, client)).thenReturn(Optional.empty());

        // Act
        recipeService.rateRecipe(recipeId, clientId, rating);

        // Assert
        ArgumentCaptor<RecipeRate> rateCaptor = ArgumentCaptor.forClass(RecipeRate.class);
        verify(recipeRateRepository, times(1)).save(rateCaptor.capture());
        RecipeRate savedRate = rateCaptor.getValue();

        assertEquals(rating, savedRate.getRate(), "Ocena powinna być zgodna z przekazaną wartością.");
        assertEquals(recipe, savedRate.getRecipe(), "Ocena powinna być przypisana do odpowiedniego przepisu.");
        assertEquals(client, savedRate.getClient(), "Ocena powinna być przypisana do odpowiedniego klienta.");
    }

    @Test
    void testRateRecipe_UpdateRate() {
        // Arrange
        Long recipeId = recipe.getId();
        Long clientId = client.getId();
        double initialRating = 3.0;
        double updatedRating = 4.0;

        RecipeRate existingRate = new RecipeRate();
        existingRate.setId(1L);
        existingRate.setRecipe(recipe);
        existingRate.setClient(client);
        existingRate.setRate(initialRating);

        when(recipeRepository.getReferenceById(recipeId)).thenReturn(recipe);
        when(clientRepository.getReferenceById(clientId)).thenReturn(client);
        when(recipeRateRepository.findByRecipeAndClient(recipe, client)).thenReturn(existingRate);

        // Act
        recipeService.rateRecipe(recipeId, clientId, updatedRating);

        // Assert
        assertEquals(updatedRating, existingRate.getRate(), "Ocena powinna zostać zaktualizowana.");
        verify(recipeRateRepository, times(1)).save(existingRate);
    }
}

