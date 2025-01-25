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
            CommentRepository commentRepository,
            MultimediaRepository multimediaRepository
    ) {
        return args -> {
            // Tworzenie produktów
            Product mleko = Product.builder().name("Mleko").build();
            Product jaja = Product.builder().name("Jaja").build();
            Product chleb = Product.builder().name("Chleb").build();
            Product cukier = Product.builder().name("Cukier").build();
            Product maslo = Product.builder().name("Masło").build();
            Product szynka = Product.builder().name("Szynka").build();
            Product ser = Product.builder().name("Ser").build();
            Product banany = Product.builder().name("Banany").build();
            Product maka = Product.builder().name("Mąka").build();
            Product sol = Product.builder().name("Sól").build();


            productRepository.saveAll(Arrays.asList(mleko, jaja, chleb, cukier, maslo,banany,maka,sol,ser,szynka));

            // Tworzenie klienta
            Client client = Client.builder()
                    .firstName("Jan")
                    .lastName("Kowalski")
                    .email("jan.kowalski11@example.com")
                    .password("password") // Pamiętaj o hashowaniu haseł w rzeczywistej aplikacji
                    .creationDate(LocalDate.now())
                    .build();
            clientRepository.save(client);
            Client client2 = Client.builder()
                    .firstName("Anna")
                    .lastName("Nowak")
                    .email("anna.nowak@example.com")
                    .password("passanna")
                    .creationDate(LocalDate.now())
                    .build();
            clientRepository.save(client2);
            Client client3 = Client.builder()
                    .firstName("Piotr")
                    .lastName("Witkowski")
                    .email("piotr.witkowski@example.com")
                    .password("passwordpiotr")
                    .creationDate(LocalDate.now().minusDays(2))
                    .build();
            clientRepository.save(client3);

            // Tworzenie spiżarni klienta
            StoreroomItem storeroom1 = StoreroomItem.builder()
                    .client(client)
                    .product(mleko)
                    .amount(1.0) // Brakuje 1 jednostki
                    .build();
            StoreroomItem storeroom2 = StoreroomItem.builder()
                    .client(client)
                    .product(jaja)
                    .amount(7.0) // Brakuje 2 jednostek
                    .build();
            StoreroomItem storeroom3 = StoreroomItem.builder()
                    .client(client)
                    .product(chleb)
                    .amount(6.0)
                    .build();

            storeroomItemRepository.saveAll(Arrays.asList(storeroom1, storeroom2, storeroom3));

            StoreroomItem s2_1 = StoreroomItem.builder().client(client2).product(banany).amount(5.0).build();
            StoreroomItem s2_2 = StoreroomItem.builder().client(client2).product(maka).amount(2.0).build();
            storeroomItemRepository.saveAll(Arrays.asList(s2_1, s2_2));

            // Tworzenie przepisu
            Multimedia multimedia = Multimedia.builder()
                    .url("https://example.com/images/omlet.jpg")
                    .type("image")
                    .build();
            multimediaRepository.save(multimedia);
            Recipe przepis = Recipe.builder()
                    .name("Omlet")
                    .multimedia(multimedia)
                    .description("1. Roztrzep jajka.\n2. Dodaj mleko.\n3. Wlej na patelnię.\n4. Smaż aż się zetną.")
                    .build();

            recipeRepository.save(przepis);



            Multimedia multimedia2 = Multimedia.builder()
                    .url("https://example.com/images/nalesniki.jpg")
                    .type("image")
                    .build();
            multimediaRepository.save(multimedia2);

            // Nowy przepis - "Naleśniki"
            Recipe nalesniki = Recipe.builder()
                    .name("Naleśniki")
                    .multimedia(multimedia2)
                    .description("1. Wymieszaj mąkę z jajkami i mlekiem.\n2. Dodaj szczyptę soli.\n3. Smaż na patelni.")
                    .build();
            recipeRepository.save(nalesniki);

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



            RecipeProducts rpN1 = RecipeProducts.builder().recipe(nalesniki).product(maka).amount(2.0).build();
            RecipeProducts rpN2 = RecipeProducts.builder().recipe(nalesniki).product(jaja).amount(2.0).build();
            RecipeProducts rpN3 = RecipeProducts.builder().recipe(nalesniki).product(sol).amount(0.5).build();
            recipeProductsRepository.saveAll(Arrays.asList(rpN1, rpN2, rpN3));


            // Tworzenie komentarzy
            Comment comment1 = Comment.builder()
                    .client(client)
                    .recipe(przepis)
                    .comment("Przepis jest świetny! Proszę dodać więcej zdjęć.")
                    .createdAt(LocalDateTime.now())
                    .build();

            commentRepository.save(comment1);

            Comment comment2 = Comment.builder()
                    .client(client2)
                    .recipe(nalesniki)
                    .comment("Najlepsze naleśniki ever!")
                    .createdAt(LocalDateTime.now().minusHours(2))
                    .build();
            commentRepository.save(comment2);

            // Opcjonalnie: Dodawanie ulubionych przepisów, ocen itp.
//            client.getFavoriteRecipes().add(przepis);
//            clientRepository.save(client);
        };
    }
}

