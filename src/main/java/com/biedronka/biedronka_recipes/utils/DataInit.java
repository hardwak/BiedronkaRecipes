package com.biedronka.biedronka_recipes.utils;

import com.biedronka.biedronka_recipes.entity.*;
import com.biedronka.biedronka_recipes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class DataInit {

   @Bean
   @Order(3)
    CommandLineRunner initDataa(
            RecipeRepository recipeRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository,
            StoreroomItemRepository storeroomItemRepository,
            RecipeProductsRepository recipeProductsRepository,
            MultimediaRepository multimediaRepository,
            ShoppingListItemRepository shoppingListItemRepository,
            EmployeeRepository employeeRepository,
            RecipeRateRepository recipeRateRepository
    ) {
        return args -> {

            // Create employee
            Employee employee = Employee.builder()
                    .firstName("Maciej")
                    .lastName("Nowak")
                    .creationDate(LocalDate.now())
                    .email("mnowak@example.com")
                    .password("qwerty")
                    .build();

            employeeRepository.save(employee);

            // Create clients
            Client client = Client.builder()
                    .firstName("Jan")
                    .lastName("Kowalski")
                    .email("jkowalski@example.com")
                    .password("maslo")
                    .creationDate(LocalDate.now())
                    .build();

            clientRepository.save(client);

            // Create multimedia
            Multimedia m1 = Multimedia.builder()
                    .url("https://cdn.biedronka.pl/newsletter/assets-glovo/_cz33/1974431-n-w41.jpg")
                    .type("image")
                    .build();
            Multimedia m2 = Multimedia.builder()
                    .url("https://media.wired.com/photos/5b493b6b0ea5ef37fa24f6f6/master/pass/meat-80049790.jpg")
                    .type("image")
                    .build();
            Multimedia m3 = Multimedia.builder()
                    .url("https://bazarekpolski.pl/wp-content/uploads/2020/11/papryka-czerwona.jpg")
                    .type("image")
                    .build();
            Multimedia m4 = Multimedia.builder()
                    .url("https://promienieslonca.pl/6776-large_default/makaron-klasyczny-kolanka-400-g.jpg")
                    .type("image")
                    .build();
            Multimedia m5 = Multimedia.builder()
                    .url("https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/ras/Assets/658A0A74-039A-487C-A07A-CAAF61B4615D/Derivates/A230DF28-60DF-429D-ABDA-96ED64E9EE10.jpg")
                    .type("image")
                    .build();
            Multimedia m6 = Multimedia.builder()
                    .url("https://az.przepisy.pl/www-przepisy-pl/www.przepisy.pl/przepisy3ii/img/variants/800x0/65-makaron-linque8706.jpg")
                    .type("image")
                    .build();

            multimediaRepository.saveAll(Arrays.asList(m1, m2, m3, m4, m5, m6));

            // Create products
            Product pr1 = Product.builder()
                    .name("Sos Culineo Spaghetti, 520g")
                    .price(5.19)
                    .brand("Culineo")
                    .multimedia(m1)
                    .build();
            Product pr2 = Product.builder()
                    .name("Podudzie z kurczaka, 1,20kg")
                    .brand("Kraina Mięs")
                    .price(24.45)
                    .multimedia(m2)
                    .build();
            Product pr3 = Product.builder()
                    .name("Papryka słodka luz")
                    .brand("")
                    .price(2.41)
                    .multimedia(m3)
                    .build();

            Product pr4 = Product.builder()
                    .name("Makraon Spaghetti")
                    .brand("Biedronka")
                    .price(13.34)
                    .multimedia(m4)
                    .build();

            productRepository.saveAll(Arrays.asList(pr1, pr2, pr3, pr4));

            // Create shopping list
            ShoppingListItem sli1 = ShoppingListItem.builder()
                    .quantity(3)
                    .finalPrice(pr1.getPrice() * 3)
                    .promo(false)
                    .client(clientRepository.getReferenceById(2L))
                    .product(pr1)
                    .build();
            ShoppingListItem sli2 = ShoppingListItem.builder()
                    .quantity(1)
                    .finalPrice(pr2.getPrice())
                    .promo(false)
                    .product(pr2)
                    .client(clientRepository.getReferenceById(2L))
                    .build();
            ShoppingListItem sli3 = ShoppingListItem.builder()
                    .quantity(3)
                    .finalPrice(pr3.getPrice() * 3)
                    .promo(false)
                    .client(clientRepository.getReferenceById(2L))
                    .product(pr3)
                    .build();

            shoppingListItemRepository.saveAll(Arrays.asList(sli1, sli2, sli3));

            m5 = multimediaRepository.findById(m5.getId()).orElseThrow();
            m6 = multimediaRepository.findById(m6.getId()).orElseThrow();
            System.out.print(m5);
            System.out.print(m6);

            // Create recipes
            Recipe recipe1 = Recipe.builder()
                    .employee(employee)
                    .description("Zagotuj wode\nPokrój paprykę")
                    .name("Spaghetti")
                    .multimedia(m5)
                    .build();

            Recipe recipe2 = Recipe.builder()
                    .employee(employee)
                    .description("Pokrój papryke")
                    .multimedia(m6)
                    .name("Makaron z papryką")
                    .build();

            recipeRepository.saveAll(Arrays.asList(recipe1, recipe2));

//            m5 = multimediaRepository.findById(m5.getId()).orElseThrow();
//            m6 = multimediaRepository.findById(m6.getId()).orElseThrow();
//            System.out.print(m5);
////            System.out.print(m6);
//            System.out.println(recipe1);
//            System.out.println(recipe2);

            // Create RecipeProducts
            RecipeProducts rp1 = RecipeProducts.builder()
                    .product(pr4)
                    .recipe(recipe1)
                    .amount(400.0)
                    .weightUnit("g")
                    .build();
            RecipeProducts rp2 = RecipeProducts.builder()
                    .product(pr3)
                    .recipe(recipe1)
                    .amount(3.0)
                    .weightUnit("szt")
                    .build();
            RecipeProducts rp3 = RecipeProducts.builder()
                    .product(pr4)
                    .recipe(recipe2)
                    .amount(100.0)
                    .weightUnit("g")
                    .build();
            RecipeProducts rp4 = RecipeProducts.builder()
                    .product(pr3)
                    .recipe(recipe2)
                    .amount(2.0)
                    .weightUnit("szt")
                    .build();

            recipeProductsRepository.saveAll(Arrays.asList(rp1, rp2, rp3, rp4));

            // Create rates
            RecipeRate rate1 = RecipeRate.builder()
                    .rate(4.0)
                    .recipe(recipe1)
                    .client(client)
                    .build();
            RecipeRate rate2 = RecipeRate.builder()
                    .rate(5.0)
                    .recipe(recipe1)
                    .client(client)
                    .build();

            recipeRateRepository.saveAll(Arrays.asList(rate1, rate2));

            //System.out.println(recipeRepository.findById(recipe1.getId()).orElseThrow());
            //System.out.print(multimediaRepository.findById(m5.getId()).orElseThrow());

        };
    }
}
