package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.IngredientEntity;
import net.croz.pancakes_unlimited.models.PancakeEntity;
import net.croz.pancakes_unlimited.models.PancakeHasIngredient;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.IngredientService;
import net.croz.pancakes_unlimited.services.PancakeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PancakeServiceImpl implements PancakeService
{
    private final PancakeEntityRepository pancakeRepository;
    private final IngredientService ingredientService;

    public PancakeServiceImpl(PancakeEntityRepository pancakeRepository, IngredientService ingredientService)
    {
        this.pancakeRepository = pancakeRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public List<PancakeEntity> findAll()
    {
        return pancakeRepository.findAll();
    }

    @Override
    public PancakeEntity findById(Integer id)
    {
        return pancakeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public PancakeEntity insert(PancakeEntity pancake)
    {
        //TODO: check for base and fil

        PancakeEntity newPancake = new PancakeEntity();
        newPancake.getPancakeIngredients().addAll(pancake.getPancakeIngredients()
                .stream()
                .map(pancakeHasIngredient ->
                    {
                        IngredientEntity ingredient = ingredientService.findById(pancakeHasIngredient.getIngredient().getIngredientId());

                        PancakeHasIngredient newPancakeHasIngredient = new PancakeHasIngredient();
                        newPancakeHasIngredient.setPancake(newPancake);
                        newPancakeHasIngredient.setIngredient(ingredient);
                        newPancakeHasIngredient.setPrice(ingredient.getPrice());
                        return newPancakeHasIngredient;
                    }).collect(Collectors.toList())
        );
        return pancakeRepository.saveAndFlush(newPancake);
    }
}
