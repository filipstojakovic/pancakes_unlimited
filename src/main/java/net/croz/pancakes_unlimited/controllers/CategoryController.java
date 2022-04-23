package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.models.CategoryEntity;
import net.croz.pancakes_unlimited.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody CategoryEntity category)
    {
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
