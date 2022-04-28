package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.repositories.CategoryEntityRepository;
import net.croz.pancakes_unlimited.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        return categoryEntityList.stream()
                .map(x -> modelMapper.map(x, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(Integer id)
    {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(categoryEntity, CategoryDTO.class);
    }
}
