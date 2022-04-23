package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.CategoryEntity;

import java.util.List;

public interface CategoryService
{
    List<CategoryEntity> findAll();
    public CategoryEntity findById(Integer id);
    CategoryEntity insert(CategoryEntity category);
}
