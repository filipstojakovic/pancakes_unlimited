package net.croz.pancakes_unlimited.models.requests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class IngredientRequest
{
    @NotBlank
    private String name;
    @NotBlank
    private String categoryName;
    @NotNull
    private Boolean isHealthy;
    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
}
