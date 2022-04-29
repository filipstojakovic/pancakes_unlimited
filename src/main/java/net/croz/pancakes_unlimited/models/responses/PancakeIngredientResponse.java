package net.croz.pancakes_unlimited.models.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class PancakeIngredientResponse
{
    private Integer ingredientId;
    private String ingredientName;
    private BigDecimal price;
}
