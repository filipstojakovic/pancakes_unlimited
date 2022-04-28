package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeHasIngredient;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.IngredientService;
import net.croz.pancakes_unlimited.services.PancakeService;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    public PancakeServiceImpl(PancakeEntityRepository pancakeRepository, IngredientService ingredientService, ModelMapper modelMapper)
    {
        this.pancakeRepository = pancakeRepository;
        this.ingredientService = ingredientService;
        this.modelMapper = modelMapper;
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
                        IngredientDTO ingredientDto = ingredientService.findById(pancakeHasIngredient.getIngredient().getIngredientId());
                        IngredientEntity ingredient = modelMapper.map(ingredientDto, IngredientEntity.class);

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
