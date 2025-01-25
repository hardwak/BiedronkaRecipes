package com.biedronka.biedronka_recipes.utils;



import com.biedronka.biedronka_recipes.entity.*;
import com.biedronka.biedronka_recipes.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            ClientRepository clientRepository,
            RecipeRepository recipeRepository,
            ProductRepository productRepository,
            StoreroomItemRepository storeroomItemRepository,
            RecipeProductsRepository recipeProductsRepository,
            CommentRepository commentRepository
    ) {
        return args -> {
            // Tworzenie produktów
            Product mleko = Product.builder().name("Mleko").build();
            Product jaja = Product.builder().name("Jaja").build();
            Product chleb = Product.builder().name("Chleb").build();
            Product cukier = Product.builder().name("Cukier").build();
            Product maslo = Product.builder().name("Masło").build();

            productRepository.saveAll(Arrays.asList(mleko, jaja, chleb, cukier, maslo));

            // Tworzenie klienta
            Client client = Client.builder()
                    .firstName("Jan")
                    .lastName("Kowalski")
                    .email("jan.kowalski@example.com")
                    .password("password") // Pamiętaj o hashowaniu haseł w rzeczywistej aplikacji
                    .creationDate(LocalDate.now())
                    .build();

            clientRepository.save(client);

            // Tworzenie spiżarni klienta
            StoreroomItem storeroom1 = StoreroomItem.builder()
                    .client(client)
                    .product(mleko)
                    .amount(1.0) // Brakuje 1 jednostki
                    .build();
            StoreroomItem storeroom2 = StoreroomItem.builder()
                    .client(client)
                    .product(jaja)
                    .amount(6.0) // Brakuje 2 jednostek
                    .build();
            StoreroomItem storeroom3 = StoreroomItem.builder()
                    .client(client)
                    .product(chleb)
                    .amount(6.0)
                    .build();

            storeroomItemRepository.saveAll(Arrays.asList(storeroom1, storeroom2, storeroom3));

            // Tworzenie przepisu
            Recipe przepis = Recipe.builder()
                    .name("Omlet")
                    .multimedia(Multimedia.builder().url("https://example.com/images/omlet.jpg").build())
                    .description("1. Roztrzep jajka.\n2. Dodaj mleko.\n3. Wlej na patelnię.\n4. Smaż aż się zetną.")
                    .build();

            recipeRepository.save(przepis);

            // Tworzenie produktów do przepisu
            RecipeProducts rp1 = RecipeProducts.builder()
                    .recipe(przepis)
                    .product(jaja)
                    .amount(4.0)
                    .build();
            RecipeProducts rp2 = RecipeProducts.builder()
                    .recipe(przepis)
                    .product(mleko)
                    .amount(2.0)
                    .build();
            RecipeProducts rp3 = RecipeProducts.builder()
                    .recipe(przepis)
                    .product(maslo)
                    .amount(1.0)
                    .build();

            recipeProductsRepository.saveAll(Arrays.asList(rp1, rp2, rp3));

            // Tworzenie komentarzy
            Comment comment1 = Comment.builder()
                    .client(client)
                    .recipe(przepis)
                    .comment("Przepis jest świetny! Proszę dodać więcej zdjęć.")
                    .createdAt(LocalDateTime.from(LocalDate.now()))
                    .build();

            commentRepository.save(comment1);

            // Opcjonalnie: Dodawanie ulubionych przepisów, ocen itp.
            client.getFavoriteRecipes().add(przepis);
            clientRepository.save(client);
        };
    }
}

