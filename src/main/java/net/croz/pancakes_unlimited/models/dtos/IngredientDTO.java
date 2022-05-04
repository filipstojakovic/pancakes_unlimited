package net.croz.pancakes_unlimited.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO
{
    private Integer ingredientId;
    private String name;
    private Boolean isHealthy;
    private BigDecimal price;
    private CategoryDTO ingredientCategory;
}
