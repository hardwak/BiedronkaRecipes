package com.biedronka.biedronka_recipes.entity.tags;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue("Meal Type")
public class MealTypeTag extends Tag{
}
