package com.biedronka.biedronka_recipes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Multimedia {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private String type;

    @OneToOne(mappedBy = "multimedia")
    private Recipe recipe;

    @OneToOne(mappedBy = "multimedia")
    private Product product;
}
