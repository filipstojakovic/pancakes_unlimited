package net.croz.pancakes_unlimited.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.croz.pancakes_unlimited.models.entities.compositekeys.PancakeIngredientKey;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "pancake_has_ingredient")
public class PancakeHasIngredient
{
    @EmbeddedId
    PancakeIngredientKey id = new PancakeIngredientKey();

    @JsonIgnore
    @ManyToOne
    @MapsId("pancakeId")
    @JoinColumn(name = "pancake_id")
    PancakeEntity pancake;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    IngredientEntity ingredient;

    @Column(name = "price")
    private BigDecimal price;
}
