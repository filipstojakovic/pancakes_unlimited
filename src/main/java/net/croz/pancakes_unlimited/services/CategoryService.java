package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService
{
    List<CategoryDTO> findAll();

    CategoryDTO findById(Integer id);
}
