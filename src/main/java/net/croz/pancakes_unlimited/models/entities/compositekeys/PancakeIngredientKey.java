package net.croz.pancakes_unlimited.models.entities.compositekeys;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Data
@Embeddable
public class PancakeIngredientKey implements Serializable
{
    @Column(name="pancake_id")
    private Integer pancakeId;

    @Column(name="ingredient_id")
    private Integer ingredientId;
}