package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.repositories.IngredientEntityRepository;
import net.croz.pancakes_unlimited.services.IngredientService;
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

    public IngredientServiceImpl(ModelMapper modelMapper, IngredientEntityRepository ingredientRepository)
    {
        this.modelMapper = modelMapper;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<IngredientEntity> findAll()
    {
        return ingredientRepository.findAll();
    }

    public IngredientEntity findById(Integer id)
    {
        return ingredientRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public IngredientEntity insert(IngredientDTO ingredient)
    {
        IngredientEntity ingredientEntity = modelMapper.map(ingredient, IngredientEntity.class);
        ingredientEntity.setIngredientId(null);
        ingredientEntity = ingredientRepository.saveAndFlush(ingredientEntity);
        entityManager.refresh(ingredientEntity);
        return findById(ingredientEntity.getIngredientId());
    }

    @Override
    public IngredientEntity update(Integer id, IngredientDTO ingredient)
    {
        if (!ingredientRepository.existsById(id))
            throw new NotFoundException();

        IngredientEntity ingredientEntity = modelMapper.map(ingredient, IngredientEntity.class);
        ingredientEntity.setIngredientId(id);
        ingredientEntity = ingredientRepository.saveAndFlush(ingredientEntity);
        entityManager.refresh(ingredientEntity);
        return findById(ingredientEntity.getIngredientId());
    }

    @Override
    public void delete(Integer id)
    {
        if(!ingredientRepository.existsById(id))
            throw new NotFoundException();
        ingredientRepository.deleteById(id);
    }
}
