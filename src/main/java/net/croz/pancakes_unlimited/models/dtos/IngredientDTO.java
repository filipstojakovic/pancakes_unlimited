package net.croz.pancakes_unlimited.models.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class IngredientDTO
{
    private Integer ingredientId;
    private String name;
    private Boolean isHealthy;
    private BigDecimal price;
    private CategoryDTO ingredientCategory;
}
