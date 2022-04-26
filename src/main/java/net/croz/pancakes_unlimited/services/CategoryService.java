package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.entities.CategoryEntity;

import java.util.List;

public interface CategoryService
{
    List<CategoryEntity> findAll();

    CategoryEntity findById(Integer id);
}
