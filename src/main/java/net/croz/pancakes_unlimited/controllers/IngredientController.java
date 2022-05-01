package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.controllers.interfaces.ICrudController;
import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.requests.IngredientRequest;
import net.croz.pancakes_unlimited.services.IngredientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController implements ICrudController<Integer, IngredientRequest, IngredientDTO>
{
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService)
    {
        this.ingredientService = ingredientService;
    }

    @Override
    public IngredientDTO findById(Integer id)
    {
        return ingredientService.findById(id);
    }

    @Override
    public List<IngredientDTO> findAll()
    {
        return ingredientService.findAll();
    }

    @Override
    public IngredientDTO insert(IngredientRequest ingredient)
    {
        return ingredientService.insert(ingredient);
    }

    @Override
    public IngredientDTO update(Integer id, IngredientRequest ingredientDTO)
    {
        return ingredientService.update(id, ingredientDTO);
    }

    @Override
    public void delete(Integer id)
    {
        ingredientService.delete(id);
    }
}
