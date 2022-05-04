package net.croz.pancakes_unlimited.models.responses;

import lombok.*;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PancakeIngredientResponse
{
    private Integer ingredientId;
    private String ingredientName;
    private BigDecimal price;
    private CategoryDTO ingredientCategory;
}
