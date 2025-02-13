package com.biedronka.biedronka_recipes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Multimedia {
    @Id
    @GeneratedValue
    private Long id;
    @Builder.Default
    private String url="\"https://example.com/images/omlet.jpg\"";
    private String type;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "multimedia")

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Recipe recipe;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "multimedia")

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Product product;
}
