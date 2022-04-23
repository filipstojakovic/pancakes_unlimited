package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.models.CategoryEntity;
import net.croz.pancakes_unlimited.repositories.CategoryEntityRepository;
import net.croz.pancakes_unlimited.services.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryEntityRepository categoryRepository;

    public CategoryServiceImpl(CategoryEntityRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> findAll()
    {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity insert(CategoryEntity category)
    {
        return categoryRepository.saveAndFlush(category);
    }
}
