package com.biedronka.biedronka_recipes.utils;

import com.biedronka.biedronka_recipes.entity.*;
import com.biedronka.biedronka_recipes.entity.tags.*;
import com.biedronka.biedronka_recipes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataFillerConfig {

    private final AllergenRepository allergenRepository;
    private final ClientRepository clientRepository;
    private final TagRepository tagRepository;
    private final EmployeeRepository employeeRepository;
    private final MultimediaRepository multimediaRepository;
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeProductsRepository recipeProductsRepository;
    private final RecipeRateRepository recipeRateRepository;

    @Bean
    public CommandLineRunner dataFillerBean() {
        return args -> {
            //--------------------ALLERGENS-----------------------
            Allergen peanuts = Allergen.builder().name("Peanuts").build();
            Allergen gluten = Allergen.builder().name("Gluten").build();
            Allergen milk = Allergen.builder().name("Milk").build();
            Allergen seafood = Allergen.builder().name("Seafood").build();

            allergenRepository.saveAll(Arrays.asList(peanuts, gluten, milk, seafood));

            //--------------------CLIENT-----------------------
            //REFERENCE
            Client client = Client.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .password("securepassword")
                    .creationDate(LocalDate.now())
                    .allergens(List.of(peanuts, milk))
                    .build();

            clientRepository.save(client);

            //--------------------TAGS-----------------------
            Tag styleTag = StyleTag.builder().name("Family").build();
            Tag styleTag2 = StyleTag.builder().name("Romantic").build();
            Tag styleTag3 = StyleTag.builder().name("Festive").build();
            Tag styleTag4 = StyleTag.builder().name("Occasional").build();
            Tag mealTypeTag = MealTypeTag.builder().name("Breakfast").build();
            Tag mealTypeTag2 = MealTypeTag.builder().name("Lunch").build();
            Tag mealTypeTag3 = MealTypeTag.builder().name("Dinner").build();
            Tag mealTypeTag4 = MealTypeTag.builder().name("Snack").build();
            Tag kitchenTypeTag = KitchenTypeTag.builder().name("Italian").build();
            Tag kitchenTypeTag2 = KitchenTypeTag.builder().name("Chinese").build();
            Tag kitchenTypeTag3 = KitchenTypeTag.builder().name("Polish").build();
            Tag difficultyTag = DifficultyTag.builder().name("Easy").build();
            Tag difficultyTag2 = DifficultyTag.builder().name("Medium").build();
            Tag difficultyTag3 = DifficultyTag.builder().name("Hard").build();
            Tag timeTag = TimeTag.builder().name("Fast").build();
            Tag timeTag2 = TimeTag.builder().name("Medium").build();
            Tag timeTag3 = TimeTag.builder().name("Long").build();

            tagRepository.saveAll(Arrays.asList(
                    styleTag, styleTag2, styleTag3, styleTag4,
                    mealTypeTag, mealTypeTag2, mealTypeTag3,mealTypeTag4,
                    kitchenTypeTag, kitchenTypeTag2, kitchenTypeTag3,
                    difficultyTag, difficultyTag2, difficultyTag3,
                    timeTag, timeTag2, timeTag3
            ));

            //--------------------EMPLOYEE-----------------------
            Employee employee = Employee.builder()
                    .firstName("Sklep")
                    .lastName("Biedronka")
                    .email("sklep@biedronka.com")
                    .password("securepassword")
                    .creationDate(LocalDate.now())
                    .build();

            employeeRepository.save(employee);

            //--------------------PHOTOS-----------------------
            List<String> recipePhotos = Arrays.asList(
                    "https://naukajedzenia.pl/wp-content/uploads/2022/06/jajecznica-3-1024x768.jpg",
                    "https://staticsmaker.iplsc.com/smaker_production_2023_06_15/9b54c834b4e65ddd75e8f3053569f7fe-lg.jpg",
                    "https://natka-pietruszki.pl/wp-content/uploads/2020/10/kurczak-1024x781.jpg",
                    "https://www.allrecipes.com/thmb/jiV_4f8vXFle1RdFLgd8-_31J3M=/1500x0/filters:no_upscale():max_bytes(150000)" +
                            ":strip_icc()/229960-shrimp-scampi-with-pasta-DDMFS-4x3-e065ddef4e6d44479d37b4523808cc23.jpg"
            );

            List<Multimedia> mediaList = new ArrayList<>();
            for (String recipePhoto : recipePhotos) {
                Multimedia multimedia = Multimedia.builder()
                        .url(recipePhoto)
                        .type("image/jpeg")
                        .build();
                mediaList.add(multimedia);
            }
            multimediaRepository.saveAll(mediaList);

            //--------------------PRODUCTS-----------------------
            //REFERENCE
            Product product_milk = Product.builder()
                    .name("Milk")
                    .allergens(List.of(milk))
                    .build();

            Product product_chicken = Product.builder()
                    .name("Chicken")
                    .build();

            Product product_shrimps = Product.builder()
                    .name("Shrimps")
                    .allergens(List.of(seafood))
                    .build();

            Product product_spaghetti = Product.builder()
                    .name("Spaghetti")
                    .allergens(List.of(gluten))
                    .build();

            Product product_peanuts = Product.builder()
                    .name("Peanuts")
                    .allergens(List.of(peanuts))
                    .build();

            Product product_eggs = Product.builder()
                    .name("Eggs")
                    .build();

            productRepository.saveAll(List.of(product_milk, product_chicken, product_spaghetti, product_peanuts, product_shrimps, product_eggs));

            //--------------------RECIPES-----------------------
            //REFERENCE
            Recipe recipe_eggs = Recipe.builder()
                    .name("Scrambled Eggs with Chicken")
                    .description("A quick and easy way to start your day with delicious scrambled eggs. Tender chicken pieces combined with fluffy eggs, a touch of milk, salt, and pepper create the perfect breakfast for the whole family.")
                    .multimedia(mediaList.get(0))
                    .employee(employee)
                    .tags(List.of(mealTypeTag, difficultyTag, timeTag))
                    .build();

            Recipe recipe_alfredo = Recipe.builder()
                    .name("Pasta with Chicken and Alfredo Sauce")
                    .description("Indulge in a creamy, rich pasta dish. Tender chicken pieces in a velvety Alfredo sauce with a hint of Parmesan and spices make every bite unforgettable. Perfect for lunch or dinner.")
                    .multimedia(mediaList.get(1))
                    .employee(employee)
                    .tags(List.of(styleTag, mealTypeTag2, difficultyTag2, timeTag2))
                    .build();

            Recipe recipe_mushrooms = Recipe.builder()
                    .name("Chicken in Creamy Mushroom Sauce")
                    .description("Tender chicken pieces simmered in a flavorful, creamy mushroom sauce. A perfect dish for an evening meal—served with rice, potatoes, or pasta.")
                    .multimedia(mediaList.get(2))
                    .employee(employee)
                    .tags(List.of(kitchenTypeTag3, difficultyTag3, timeTag3))
                    .build();

            Recipe recipe_shrimps = Recipe.builder()
                    .name("Spaghetti with Shrimp in Butter Sauce")
                    .description("A light yet exquisite dish. Spaghetti paired with juicy shrimp in a buttery garlic sauce with a touch of fresh herbs. A perfect choice for a romantic dinner or an elegant lunch.")
                    .multimedia(mediaList.get(3))
                    .employee(employee)
                    .tags(List.of(kitchenTypeTag, styleTag4, difficultyTag2, timeTag3))
                    .build();

            recipeRepository.saveAll(List.of(recipe_eggs,recipe_alfredo, recipe_mushrooms, recipe_shrimps));

            //--------------------RECIPE_PRODUCTS-----------------------
            //REFERENCE
            RecipeProducts recipeProducts_eggs1 = RecipeProducts.builder()
                    .amount(5d)
                    .weightUnit("PSC")
                    .recipe(recipe_eggs)
                    .product(product_eggs)
                    .build();
            RecipeProducts recipeProducts_eggs2 = RecipeProducts.builder()
                    .amount(300d)
                    .weightUnit("G")
                    .recipe(recipe_eggs)
                    .product(product_chicken)
                    .build();

            RecipeProducts recipeProducts_alfredo = RecipeProducts.builder()
                    .amount(500d)
                    .weightUnit("G")
                    .recipe(recipe_alfredo)
                    .product(product_chicken)
                    .build();
            RecipeProducts recipeProducts_alfredo2 = RecipeProducts.builder()
                    .amount(300d)
                    .weightUnit("G")
                    .recipe(recipe_alfredo)
                    .product(product_spaghetti)
                    .build();

            RecipeProducts recipeProducts_mushrooms = RecipeProducts.builder()
                    .amount(200d)
                    .weightUnit("G")
                    .recipe(recipe_mushrooms)
                    .product(product_peanuts)
                    .build();
            RecipeProducts recipeProducts_mushrooms2 = RecipeProducts.builder()
                    .amount(500d)
                    .weightUnit("G")
                    .recipe(recipe_mushrooms)
                    .product(product_chicken)
                    .build();
            RecipeProducts recipeProducts_mushroom3 = RecipeProducts.builder()
                    .amount(200d)
                    .weightUnit("ML")
                    .recipe(recipe_mushrooms)
                    .product(product_milk)
                    .build();

            RecipeProducts recipeProducts_shrimps = RecipeProducts.builder()
                    .amount(150d)
                    .weightUnit("G")
                    .recipe(recipe_shrimps)
                    .product(product_shrimps)
                    .build();
            RecipeProducts recipeProducts_shrimps2 = RecipeProducts.builder()
                    .amount(200d)
                    .weightUnit("G")
                    .recipe(recipe_shrimps)
                    .product(product_spaghetti)
                    .build();

            recipeProductsRepository.saveAll(List.of(
                    recipeProducts_eggs1, recipeProducts_eggs2,
                    recipeProducts_alfredo, recipeProducts_alfredo2,
                    recipeProducts_mushrooms, recipeProducts_mushrooms2, recipeProducts_mushroom3,
                    recipeProducts_shrimps, recipeProducts_shrimps2
            ));

            //--------------------RECIPE_RATE-----------------------
            RecipeRate recipeRate1 = RecipeRate.builder()
                    .rate(5d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_eggs)
                    .client(client)
                    .build();
            RecipeRate recipeRate2 = RecipeRate.builder()
                    .rate(4d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_eggs)
                    .client(client)
                    .build();
            RecipeRate recipeRate8 = RecipeRate.builder()
                    .rate(4d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_eggs)
                    .client(client)
                    .build();

            RecipeRate recipeRate3 = RecipeRate.builder()
                    .rate(5d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_alfredo)
                    .client(client)
                    .build();
            RecipeRate recipeRate4 = RecipeRate.builder()
                    .rate(3d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_alfredo)
                    .client(client)
                    .build();

            RecipeRate recipeRate5 = RecipeRate.builder()
                    .rate(5d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_mushrooms)
                    .client(client)
                    .build();
            RecipeRate recipeRate6 = RecipeRate.builder()
                    .rate(2d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_mushrooms)
                    .client(client)
                    .build();

            RecipeRate recipeRate7 = RecipeRate.builder()
                    .rate(5d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_shrimps)
                    .client(client)
                    .build();
            RecipeRate recipeRate9 = RecipeRate.builder()
                    .rate(5d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_shrimps)
                    .client(client)
                    .build();
            RecipeRate recipeRate10 = RecipeRate.builder()
                    .rate(4d)
                    .createdAt(LocalDateTime.now())
                    .recipe(recipe_shrimps)
                    .client(client)
                    .build();

            recipeRateRepository.saveAll(List.of(
                    recipeRate1, recipeRate2, recipeRate8,
                    recipeRate3, recipeRate4,
                    recipeRate5, recipeRate6,
                    recipeRate7, recipeRate9, recipeRate10
            ));
        };
    }

}
