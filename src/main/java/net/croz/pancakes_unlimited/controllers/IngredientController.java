package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.models.IngredientEntity;
import net.croz.pancakes_unlimited.services.IngredientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
