package net.croz.pancakes_unlimited.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientReportResponse
{
    private Integer ingredientId;
    private String name;
    private Boolean isHealthy;
    private BigInteger orderedTimes;
}
