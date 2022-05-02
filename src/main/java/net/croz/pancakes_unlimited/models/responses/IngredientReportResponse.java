package net.croz.pancakes_unlimited.models.responses;

import lombok.Data;

import java.math.BigInteger;

@Data
public class IngredientReportResponse
{
    private Integer ingredientId;
    private String name;
    private Boolean isHealthy;
    private BigInteger orderedTimes;
}
