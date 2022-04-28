package net.croz.pancakes_unlimited.models.entities.compositekeys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Data
@Embeddable
public class PancakeIngredientKey implements Serializable
{
    @JsonIgnore // we can comment this, depends on what we want
    @Column(name="pancake_id")
    private Integer pancakeId;

    @Column(name="ingredient_id")
    private Integer ingredientId;
}
