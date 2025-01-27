package com.biedronka.biedronka_recipes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShoppingListItem {
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;
    private Double finalPrice;
    private LocalDate confirmationDate;
    private Boolean promo;

    @ManyToOne
    @JoinColumn(
            name = "client_id",
            nullable = false
    )
    private Client client;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;



}
