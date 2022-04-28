package net.croz.pancakes_unlimited.models.dtos;

import lombok.Data;
import net.croz.pancakes_unlimited.models.entities.PancakeHasIngredient;

import java.util.List;

@Data
public class PancakeDTO
{
    private Integer id;
    private List<PancakeHasIngredient> pancakeIngredients;
}
