package com.biedronka.biedronka_recipes.utils;


import com.biedronka.biedronka_recipes.entity.*;
import com.biedronka.biedronka_recipes.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    @Order(2)
    CommandLineRunner initData(
            ClientRepository clientRepository,
            RecipeRepository recipeRepository,
            ProductRepository productRepository,
            StoreroomItemRepository storeroomItemRepository,
            RecipeProductsRepository recipeProductsRepository,
            CommentRepository commentRepository,
            MultimediaRepository multimediaRepository,
            EmployeeRepository employeeRepository,
            AllergenRepository allergenRepository) {
        return args -> {

            Multimedia nophoto = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();
            Multimedia nophoto2 = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();
            Multimedia nophoto3 = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();
            Multimedia nophoto4 = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();
            Multimedia nophoto5 = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();
            Multimedia nophoto6 = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();
            Multimedia nophoto7 = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();
            Multimedia nophoto8 = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();
            Multimedia nophoto9 = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();
            Multimedia nophoto10 = Multimedia.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png")
                    .type("image")
                    .build();

            multimediaRepository.saveAll(List.of(nophoto, nophoto2, nophoto3, nophoto4, nophoto5, nophoto6, nophoto7, nophoto8, nophoto9, nophoto10));

            // Tworzenie produktów
            Product mleko = Product.builder()
                    .name("Mleko")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto)
                    .allergens(List.of(allergenRepository.getReferenceById(3L)))
                    .build();
            Product jaja = Product.builder()
                    .name("Jaja")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto2)
                    .build();
            Product chleb = Product.builder()
                    .name("Chleb")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto3)
                    .allergens(List.of(allergenRepository.getReferenceById(2L)))
                    .build();
            Product cukier = Product.builder()
                    .name("Cukier")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto4)
                    .build();
            Product maslo = Product.builder()
                    .name("Masło")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto5)
                    .build();
            Product szynka = Product.builder()
                    .name("Szynka")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto6)
                    .build();
            Product ser = Product.builder()
                    .name("Ser")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto7)
                    .allergens(List.of(allergenRepository.getReferenceById(3L)))
                    .build();
            Product banany = Product.builder()
                    .name("Banany")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto8)
                    .build();
            Product maka = Product.builder()
                    .name("Mąka")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto9)
                    .build();
            Product sol = Product.builder()
                    .name("Sól")
                    .price(5.99)
                    .brand("Biedronka")
                    .multimedia(nophoto10)
                    .build();


            productRepository.saveAll(Arrays.asList(mleko, jaja, chleb, cukier, maslo, banany, maka, sol, ser, szynka));

            // Tworzenie klienta
            Client client = Client.builder()
                    .firstName("Jan")
                    .lastName("Kowalski")
                    .email("jan.kowalski11@example.com")
                    .allergens(List.of(allergenRepository.getReferenceById(1L), allergenRepository.getReferenceById(3L)))
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
                    .url("https://cdn.galleries.smcloud.net/t/galleries/gf-rkTU-5UvR-Kzzc_jak-zrobic-omlet-klasyczny-przepis-664x442.jpg")
                    .type("image")
                    .build();
            multimediaRepository.save(multimedia);
            Recipe przepis = Recipe.builder()
                    .name("Omlet")
                    .multimedia(multimedia)
                    .employee(employeeRepository.getReferenceById(1L))
                    .description("1. Roztrzep jajka.\n2. Dodaj mleko.\n3. Wlej na patelnię.\n4. Smaż aż się zetną.")
                    .client(client)
                    .build();

            recipeRepository.save(przepis);


            Multimedia multimedia2 = Multimedia.builder()
                    .url("https://www.blwpapu.pl/img/blog/nalesniki-pszenne-blw-przepisy_45_original.jpg")
                    .type("image")
                    .build();
            multimediaRepository.save(multimedia2);

            // Nowy przepis - "Naleśniki"
            Recipe nalesniki = Recipe.builder()
                    .name("Naleśniki")
                    .multimedia(multimedia2)
                    .employee(employeeRepository.getReferenceById(1L))
                    .description("")
                    .client(client)
                    .isDraft(true)
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

