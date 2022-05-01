package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.requests.IngredientRequest;
import net.croz.pancakes_unlimited.services.IngredientService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController
{
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService)
    {
        this.ingredientService = ingredientService;
    }


    @GetMapping
    public List<IngredientDTO> findAll()
    {
        return ingredientService.findAll();
    }

    @PostMapping
    public IngredientDTO insert(@Valid @RequestBody IngredientRequest ingredient)
    {
        return ingredientService.insert(ingredient);
    }

    @PutMapping("/{id}")
    public IngredientDTO update(@PathVariable Integer id, @Valid @RequestBody IngredientRequest ingredientDTO)
    {
        return ingredientService.update(id, ingredientDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id)
    {
        ingredientService.delete(id);
    }
}
