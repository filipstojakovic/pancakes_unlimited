package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.CategoryEntity;

import java.util.List;

public interface CategoryService
{
    List<CategoryEntity> findAll();
    CategoryEntity insert(CategoryEntity category);
}
