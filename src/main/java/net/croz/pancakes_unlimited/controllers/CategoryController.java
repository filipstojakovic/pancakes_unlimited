package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.exceptions.BadRequestException;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController
{
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryEntity> findAll()
    {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryEntity findById(@PathVariable Integer id)
    {
        return categoryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryEntity insert(@RequestBody CategoryEntity category) throws BadRequestException
    {
        //TODO: check if unique
        return categoryService.insert(category);
    }


}
