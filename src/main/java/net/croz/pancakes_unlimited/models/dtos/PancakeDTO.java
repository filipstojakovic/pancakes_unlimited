package net.croz.pancakes_unlimited.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.croz.pancakes_unlimited.models.responses.PancakeIngredientResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PancakeDTO
{
    private Integer id;
    private List<PancakeIngredientResponse> pancakeIngredients;
}
