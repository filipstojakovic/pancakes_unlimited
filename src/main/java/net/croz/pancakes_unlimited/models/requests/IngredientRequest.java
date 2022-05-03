package net.croz.pancakes_unlimited.models.requests;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class IngredientRequest
{
    @NotBlank
    private String name;
    @NotBlank
    private String categoryName; // this could be categoryId
    @NotNull
    private Boolean isHealthy;
    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
}
