package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.models.entities.CategoryEntity;

import java.util.List;

public interface ReportRepository
{
    public List<CategoryEntity> getCategories();
}
