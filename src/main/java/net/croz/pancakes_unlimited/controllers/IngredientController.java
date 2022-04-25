package net.croz.pancakes_unlimited.controllers;

import liquibase.pro.packaged.R;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.services.IngredientService;
import org.springframework.web.bind.annotation.*;

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
    public List<IngredientEntity> findAll()
    {
        return ingredientService.findAll();
    }

    @PostMapping
    public IngredientEntity insert(@RequestBody IngredientDTO ingredient)
    {
        return ingredientService.insert(ingredient);
    }

    @PutMapping("/{id}")
    public IngredientEntity update(@PathVariable Integer id, @RequestBody IngredientDTO ingredientDTO) throws NotFoundException
    {
        return ingredientService.update(id, ingredientDTO);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        ingredientService.delete(id);
    }
}
