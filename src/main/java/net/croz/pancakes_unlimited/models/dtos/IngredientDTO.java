package net.croz.pancakes_unlimited.models.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class IngredientDTO
{
    private Integer categoryId;
    private String name;
    private Boolean isHealthy;
    private BigDecimal price;
}
