package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.IngredientEntity;

import java.util.List;

public interface IngredientService
{
    List<IngredientEntity> findAll();
    IngredientEntity insert(IngredientEntity ingredient);
}
