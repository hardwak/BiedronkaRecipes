package com.biedronka.biedronka_recipes.entity.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data

@AllArgsConstructor
@Entity
@DiscriminatorValue("Style")
public class StyleTag extends Tag {
}
