package net.croz.pancakes_unlimited.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.croz.pancakes_unlimited.models.entities.compositekeys.PancakeIngredientKey;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "pancake_has_ingredient")
public class PancakeHasIngredient
{
    @EmbeddedId
    PancakeIngredientKey id = new PancakeIngredientKey();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pancakeId")
    @JoinColumn(name = "pancake_id")
    private PancakeEntity pancake;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private IngredientEntity ingredient;

    @Column(name = "price")
    private BigDecimal price;
}
