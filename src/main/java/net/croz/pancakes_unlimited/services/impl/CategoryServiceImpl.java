package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.BadRequestException;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
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

    public CategoryEntity findById(Integer id) throws NotFoundException
    {
        return categoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
