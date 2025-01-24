package com.biedronka.biedronka_recipes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String comment;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(
            name = "recipe_id",
            nullable = false
    )
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(
            name = "client_id",
            nullable = false
    )
    private Client client;
}
