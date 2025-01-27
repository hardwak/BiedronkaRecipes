package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.dto.recipe.RecipeCreationDTO;
import com.biedronka.biedronka_recipes.dto.recipe.RecipeResponseDTO;
import com.biedronka.biedronka_recipes.entity.Multimedia;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.entity.RecipeProducts;
import com.biedronka.biedronka_recipes.utils.RecipeMapper;
import com.biedronka.biedronka_recipes.dto.*;
import com.biedronka.biedronka_recipes.dto.recipe.RecipeSearchResponseDTO;
import com.biedronka.biedronka_recipes.entity.*;
import com.biedronka.biedronka_recipes.entity.tags.Tag;
import com.biedronka.biedronka_recipes.repository.*;
import jakarta.transaction.Transactional;
import com.biedronka.biedronka_recipes.utils.RecipeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeProductsRepository recipeProductsRepository;
    private final RecipeMapper recipeMapper;

    private final ClientRepository clientRepository;
    private final ShoppingListService shoppingListService;
    private final CommentRepository commentRepository;
    private final RecipeRateRepository recipeRateRepository;
    private final StoreroomItemRepository storeroomItemRepository;
    private final ProductRepository productRepository;
    private final MultimediaRepository multimediaRepository;
    private final EmployeeRepository employeeRepository;

    public List<RecipeSearchResponseDTO> searchRecipe(
            String keyword,
            List<Long> tags,
            List<Double> rates,
            Boolean filterByClientAllergens,
            Long clientId
    ) {
        List<Recipe> matchedKeywordRecipes;
        if (keyword != null && !keyword.isEmpty())
            matchedKeywordRecipes =
                    recipeRepository.findByKeywordInNameDescriptionOrProducts(keyword);
        else
            matchedKeywordRecipes =
                    recipeRepository.findAll();

        if (matchedKeywordRecipes.isEmpty())
            return List.of();

        return matchedKeywordRecipes.stream()
                .filter(recipe -> !recipe.getIsDraft())
                .filter(recipe -> {
                    boolean tagsMatched = (tags == null || tags.isEmpty()) || new HashSet<>(
                            recipe.getTags().stream()
                                    .map(Tag::getId)
                                    .toList()
                    ).containsAll(tags);

                    boolean ratesMatched = (rates == null || rates.isEmpty()) || rates.stream()
                            .anyMatch(rate -> rate == Math.round(
                                    recipe.getRecipeRates().stream()
                                            .mapToDouble(RecipeRate::getRate)
                                            .average()
                                            .orElse(0.0)));

                    boolean filterByClientAllergensMatched = true;
                    if (filterByClientAllergens != null && filterByClientAllergens) {
                        List<Long> clientAllergenIds = recipeRepository.findClientAllergensByClientId(clientId);
                        filterByClientAllergensMatched = recipe.getRecipeProducts().stream()
                                .noneMatch(recipeProduct -> recipeProduct.getProduct().getAllergens().stream()
                                        .anyMatch(allergen -> clientAllergenIds.contains(allergen.getId())));
                    }

                    return tagsMatched && ratesMatched && filterByClientAllergensMatched;
                })
                .map(recipeMapper::toRecipeSearchResponseDTO)
                .toList();
    }

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


    public void saveRecipe(RecipeDTO recipeDTO, boolean isDraft, Long clientId) {
        System.out.println("saveRecipe: " + recipeDTO);
        Recipe recipe = recipeMapper.toEntity(recipeDTO, productRepository);

        Client client = clientRepository.getReferenceById(clientId);
        recipe.setClient(client);
        recipe.setEmployee(employeeRepository.getReferenceById(1L));
        recipe.setIsDraft(isDraft);
        Multimedia multimedia = Multimedia.builder()
                .url("https://images.ctfassets.net/kugm9fp9ib18/3aHPaEUU9HKYSVj1CTng58/d6750b97344c1dc31bdd09312d74ea5b/menu-default-image_220606_web.png")
                .type("image")
                .build();
        multimediaRepository.save(multimedia);
        recipe.setMultimedia(multimedia);

        Recipe existingRecipe;
        if (recipeDTO.getId() == null) {
            existingRecipe = null;
        } else {
            existingRecipe = recipeRepository.findById(recipe.getId()).orElse(null);
        }

        if (existingRecipe != null) {
            existingRecipe.setName(recipe.getName());
            existingRecipe.setDescription(recipe.getDescription());
            existingRecipe.setIsDraft(isDraft);
            existingRecipe.setMultimedia(multimedia);
            List<RecipeProducts> updatedProducts = new ArrayList<>();

            recipeRepository.save(existingRecipe);
            for (RecipeProducts newProduct : recipe.getRecipeProducts()) {
                System.out.println("New product: " + newProduct.getProduct().getId() + " " + newProduct.getAmount());
                RecipeProducts existingProduct = existingRecipe.getRecipeProducts().stream()
                        .filter(p -> p.getProduct().getId().equals(newProduct.getProduct().getId()))
                        .findFirst()
                        .orElse(null);

                if (existingProduct != null) {
                    // Aktualizowanie ilości w istniejącym produkcie
                    existingProduct.setAmount(newProduct.getAmount());
                    updatedProducts.add(existingProduct);
                    recipeProductsRepository.save(existingProduct);

                } else {
                    // Dodawanie nowego produktu
                    System.out.println("New product: " + newProduct.getProduct().getId() + " " + newProduct.getAmount());
                    newProduct.setRecipe(existingRecipe);
                    updatedProducts.add(newProduct);
                    recipeProductsRepository.save(newProduct);
                }
            }
            existingRecipe.setRecipeProducts(updatedProducts);
            recipeRepository.save(existingRecipe);

        } else {
            recipeRepository.save(recipe);
            for (RecipeProducts product : recipe.getRecipeProducts()) {
                product.setRecipe(recipe);
                recipeProductsRepository.save(product);
            }
            recipeRepository.save(recipe);

        }


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

    public List<RecipeResponseDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipeMapper.toResponseDtoList(recipes);
    }

    public RecipeResponseDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe != null) {
            return recipeMapper.toResponseDto(recipe);
        }
        return null;
    }

    public Recipe getRecipeByID(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public RecipeCreationDTO getRecipeCreationDto(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe != null) {
            System.out.println(RecipeCreationDTO.fromEntity(recipe));
            return RecipeCreationDTO.fromEntity(recipe);
        } else {
            return new RecipeCreationDTO();
        }
    }

    @Transactional
    public void deleteRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        if (recipe.getMultimedia() != null) {
            Multimedia multimedia = recipe.getMultimedia();
            multimedia.setRecipe(null);
            recipe.setMultimedia(null);
        }

        recipe.getRecipeRates().forEach(rate -> rate.setRecipe(null));
        recipe.getRecipeRates().clear();

        recipe.getRecipeProducts().forEach(rp -> rp.setRecipe(null));
        recipe.getRecipeProducts().clear();

        recipeRepository.delete(recipe);
    }


    @Transactional
    public void updateRecipe(RecipeCreationDTO recipeDTO) {
        Recipe recipe = recipeRepository.getReferenceById(recipeDTO.getId());
        recipe.setName(recipeDTO.getName());
        recipe.setDescription(recipeDTO.getDescription());

        Multimedia multimedia = recipe.getMultimedia();
        multimedia.setUrl(recipeDTO.getMultimediaUrl());
        multimediaRepository.save(multimedia);

        recipe.getRecipeProducts().clear();
        recipeDTO.getRecipeProducts().forEach(ingredientDTO -> {
            Product product = productRepository.findById(ingredientDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produkt nie został znaleziony"));
            RecipeProducts recipeProduct = RecipeProducts.builder()
                    .product(product)
                    .recipe(recipe)
                    .amount(ingredientDTO.getAmount())
                    .weightUnit(ingredientDTO.getWeightUnit())
                    .build();
            recipe.getRecipeProducts().add(recipeProduct);
        });

        recipeRepository.save(recipe);
    }

    @Transactional
    public void addRecipe(RecipeCreationDTO recipeDTO) {
        Multimedia multimedia = Multimedia.builder()
                .url(recipeDTO.getMultimediaUrl())
                .type("image")
                .build();
        multimediaRepository.save(multimedia);

        Recipe recipe = Recipe.builder()
                .name(recipeDTO.getName())
                .description(recipeDTO.getDescription())
                .multimedia(multimedia)
                .build();

        recipeDTO.getRecipeProducts().forEach(ingredientDTO -> {
            Product product = productRepository.findById(ingredientDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produkt nie został znaleziony"));
            RecipeProducts recipeProduct = RecipeProducts.builder()
                    .product(product)
                    .recipe(recipe)
                    .amount(ingredientDTO.getAmount())
                    .weightUnit(ingredientDTO.getWeightUnit())
                    .build();
            recipe.getRecipeProducts().add(recipeProduct);
        });

        recipeRepository.save(recipe);
    }
}

