package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.controllers.interfaces.IFindController;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.services.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController implements IFindController<Integer, CategoryDTO>
{
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @Override
    public List<CategoryDTO> findAll()
    {
        return categoryService.findAll();
    }

    @Override
    public CategoryDTO findById(Integer id)
    {
        return categoryService.findById(id);
    }

}
