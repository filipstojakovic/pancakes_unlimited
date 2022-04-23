package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.models.IngredientEntity;
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

    @Override
    public IngredientEntity insert(IngredientEntity ingredient)
    {
        return ingredientRepository.saveAndFlush(ingredient);
    }
}
