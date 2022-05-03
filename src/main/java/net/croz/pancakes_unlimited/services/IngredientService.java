package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.requests.IngredientRequest;
import net.croz.pancakes_unlimited.services.crudinterfaces.ICrudService;

public interface IngredientService extends ICrudService<Integer, IngredientRequest, IngredientDTO>
{
}
