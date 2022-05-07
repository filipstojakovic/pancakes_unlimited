package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.repositories.CategoryEntityRepository;
import net.croz.pancakes_unlimited.services.CategoryService;
import net.croz.pancakes_unlimited.utils.MapUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryEntityRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryEntityRepository categoryRepository, ModelMapper modelMapper)
    {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDTO> findAll()
    {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        return MapUtils.mapList(categoryEntityList, CategoryDTO.class, modelMapper);
    }

    @Override
    public CategoryDTO findById(Integer id)
    {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(()->new NotFoundException(CategoryEntity.class,id));
        return modelMapper.map(categoryEntity, CategoryDTO.class);
    }
}
