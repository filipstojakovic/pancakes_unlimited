package net.croz.pancakes_unlimited.models.dtos;

import lombok.Data;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;

import java.math.BigDecimal;

@Data
public class IngredientDTO
{
    private Integer ingredientId;
    private String name;
    private Boolean isHealthy;
    private BigDecimal price;
    private CategoryEntity ingredientCategory;
}
