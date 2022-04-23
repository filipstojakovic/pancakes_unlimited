package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.IngredientEntity;

import java.util.List;
import java.util.Optional;

public interface IngredientService
{
    List<IngredientEntity> findAll();
    public IngredientEntity findById(Integer id);
    IngredientEntity insert(IngredientEntity ingredient);
}
