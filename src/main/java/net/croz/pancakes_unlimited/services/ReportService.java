package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.entities.CategoryEntity;

import java.util.List;

public interface ReportService
{
    List<CategoryEntity> getCategories();
}
