package net.croz.pancakes_unlimited.models.entities.compositekeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class PancakeIngredientKey implements Serializable
{
    private PancakeEntity pancake;
    private IngredientEntity ingredient;
}
