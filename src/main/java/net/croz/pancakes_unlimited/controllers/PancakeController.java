package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.models.IngredientEntity;
import net.croz.pancakes_unlimited.models.PancakeEntity;
import net.croz.pancakes_unlimited.services.PancakeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pancakes")
public class PancakeController
{
    private final PancakeService pancakeService;


    public PancakeController(PancakeService pancakeService)
    {
        this.pancakeService = pancakeService;
    }

    @GetMapping
    public List<PancakeEntity> findAll()
    {
        return pancakeService.findAll();
    }

    @PostMapping
    public PancakeEntity insert(@RequestBody PancakeEntity pancake)
    {
        return pancakeService.insert(pancake);
    }
}
