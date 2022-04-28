package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.requests.IngredientRequest;

import java.util.List;

public interface IngredientService
{
    List<IngredientDTO> findAll();

    IngredientDTO findById(Integer id);

    IngredientDTO insert(IngredientRequest ingredient);

    IngredientDTO update(Integer id, IngredientRequest ingredient);

    void delete(Integer id);
}
