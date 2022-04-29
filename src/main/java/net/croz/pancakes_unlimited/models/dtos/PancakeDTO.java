package net.croz.pancakes_unlimited.models.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.croz.pancakes_unlimited.models.responses.PancakeIngredientResponse;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class PancakeDTO
{
    private Integer id;
    private List<PancakeIngredientResponse> pancakeIngredients;
}
