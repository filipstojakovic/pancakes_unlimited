package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.repositories.IngredientEntityRepository;
import net.croz.pancakes_unlimited.services.IngredientService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService
{
    private final IngredientEntityRepository ingredientRepository;

    public IngredientServiceImpl(IngredientEntityRepository ingredientRepository)
    {
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
    public IngredientEntity insert(IngredientEntity ingredient)
    {
        return ingredientRepository.saveAndFlush(ingredient);
    }
}
