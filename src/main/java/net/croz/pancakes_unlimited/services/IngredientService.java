package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;

import java.util.List;

public interface IngredientService
{
    List<IngredientEntity> findAll();
    public IngredientEntity findById(Integer id);
    IngredientEntity insert(IngredientDTO ingredient);

    IngredientEntity update(Integer id, IngredientDTO ingredient);

    void delete(Integer id);
}
