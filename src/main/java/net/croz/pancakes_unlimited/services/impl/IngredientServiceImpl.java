package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.models.requests.IngredientRequest;
import net.croz.pancakes_unlimited.repositories.CategoryEntityRepository;
import net.croz.pancakes_unlimited.repositories.IngredientEntityRepository;
import net.croz.pancakes_unlimited.services.IngredientService;
import net.croz.pancakes_unlimited.utils.MapUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService
{
    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;
    private final IngredientEntityRepository ingredientRepository;
    private final CategoryEntityRepository categoryRepository;

    public IngredientServiceImpl(ModelMapper modelMapper, IngredientEntityRepository ingredientRepository, CategoryEntityRepository categoryRepository)
    {
        this.modelMapper = modelMapper;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<IngredientDTO> findAll()
    {
        List<IngredientEntity> ingredientEntityList = ingredientRepository.findAll();
        return MapUtils.mapList(ingredientEntityList,IngredientDTO.class,modelMapper);
    }

    @Override
    public IngredientDTO findById(Integer id)
    {
        IngredientEntity ingredientEntity = ingredientRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(ingredientEntity, IngredientDTO.class);
    }

    @Override
    public IngredientDTO insert(IngredientRequest ingredient)
    {
        CategoryEntity categoryEntity = categoryRepository.findByName(ingredient.getCategoryName()).orElseThrow(NotFoundException::new);

        IngredientEntity ingredientEntity = modelMapper.map(ingredient, IngredientEntity.class);
        ingredientEntity.setIngredientId(null);
        ingredientEntity.setIngredientCategory(categoryEntity);
        ingredientEntity = ingredientRepository.saveAndFlush(ingredientEntity);
        entityManager.refresh(ingredientEntity);
        return modelMapper.map(ingredientEntity, IngredientDTO.class);
    }

    @Override
    public IngredientDTO update(Integer id, IngredientRequest ingredient)
    {
        if (!ingredientRepository.existsById(id))
            throw new NotFoundException();

        CategoryEntity categoryEntity = categoryRepository.findByName(ingredient.getCategoryName()).orElseThrow(NotFoundException::new);
        IngredientEntity ingredientEntity = modelMapper.map(ingredient, IngredientEntity.class);

        ingredientEntity.setIngredientId(id);
        ingredientEntity.setIngredientCategory(categoryEntity);
        ingredientEntity = ingredientRepository.saveAndFlush(ingredientEntity);
        entityManager.refresh(ingredientEntity);
        return modelMapper.map(ingredientEntity, IngredientDTO.class);
    }

    @Override
    public void delete(Integer id)
    {
        if (!ingredientRepository.existsById(id))
            throw new NotFoundException();
        ingredientRepository.deleteById(id);
    }

    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
}
