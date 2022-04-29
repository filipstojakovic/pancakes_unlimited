package net.croz.pancakes_unlimited.models.entities.compositekeys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PancakeIngredientKey implements Serializable
{
    @JsonIgnore // we can comment this, depends on what we want
    @Column(name="pancake_id")
    private Integer pancakeId;

    @Column(name="ingredient_id")
    private Integer ingredientId;
}
