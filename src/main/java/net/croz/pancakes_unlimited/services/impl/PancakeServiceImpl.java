package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeHasIngredient;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;
import net.croz.pancakes_unlimited.models.responses.PancakeIngredientResponse;
import net.croz.pancakes_unlimited.repositories.IngredientEntityRepository;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.PancakeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PancakeServiceImpl implements PancakeService
{
    private final PancakeEntityRepository pancakeRepository;
    private final IngredientEntityRepository ingredientRepository;

    private final ModelMapper modelMapper;

    public PancakeServiceImpl(PancakeEntityRepository pancakeRepository, IngredientEntityRepository ingredientRepository, ModelMapper modelMapper)
    {
        this.pancakeRepository = pancakeRepository;
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PancakeDTO> findAll()
    {
        List<PancakeEntity> pancakeEntityList = pancakeRepository.findAll();
        return pancakeEntityList.stream().map(this::mapPancakeEntityToPancakeDTO).collect(Collectors.toList());
    }

    @Override
    public PancakeDTO findById(Integer id)
    {
        PancakeEntity pancakeEntity = pancakeRepository.findById(id).orElseThrow(NotFoundException::new);
        return mapPancakeEntityToPancakeDTO(pancakeEntity);
    }

    @Override
    public PancakeDTO insert(PancakeRequest pancakeRequest)
    {
        //        //TODO: check for base and fil
        List<IngredientEntity> ingredientEntityList = getIngredientEntitiesByNames(pancakeRequest);
        //        TODO: merge same ingredients

        PancakeEntity newPancake = new PancakeEntity();
        PancakeEntity finalNewPancake = newPancake;
        List<PancakeHasIngredient> pancakeHasIngredients = ingredientEntityList.stream()
                .map(ingredient ->
                    {
                        PancakeHasIngredient newPancakeHasIngredient = new PancakeHasIngredient();

                        newPancakeHasIngredient.setPancake(finalNewPancake);
                        newPancakeHasIngredient.setIngredient(ingredient);
                        newPancakeHasIngredient.setPrice(ingredient.getPrice());

                        return newPancakeHasIngredient;
                    }).collect(Collectors.toList());

        newPancake.setPancakeIngredients(pancakeHasIngredients);
        newPancake = pancakeRepository.saveAndFlush(finalNewPancake);
        return modelMapper.map(newPancake, PancakeDTO.class);
    }

    private List<IngredientEntity> getIngredientEntitiesByNames(PancakeRequest pancakeRequest)
    {
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        List<String> ingredientsName = pancakeRequest.getIngredientNames();
        for (String ingredientName : ingredientsName)
        {
            ingredientEntityList.add(ingredientRepository.findByName(ingredientName).orElseThrow(NotFoundException::new));
        }
        return ingredientEntityList;
    }

    private PancakeDTO mapPancakeEntityToPancakeDTO(PancakeEntity pancake)
    {
        List<PancakeIngredientResponse> pancakeIngredientResponses =
                mapPancakeHasIngredientToPancakeIngredientResponse(pancake.getPancakeIngredients());

        PancakeDTO pancakeDTO = new PancakeDTO();
        pancakeDTO.setId(pancake.getId());
        pancakeDTO.setPancakeIngredients(pancakeIngredientResponses);
        return pancakeDTO;
    }

    private List<PancakeIngredientResponse> mapPancakeHasIngredientToPancakeIngredientResponse(List<PancakeHasIngredient> pancakeHasIngredients)
    {
        return pancakeHasIngredients.stream()
                .map(pancakeHasIngredient ->
                    {
                        IngredientEntity ingredientEntity = pancakeHasIngredient.getIngredient();
                        BigDecimal price = pancakeHasIngredient.getPrice();
                        PancakeIngredientResponse pancakeIngredientResponse = new PancakeIngredientResponse();
                        pancakeIngredientResponse.setIngredientId(ingredientEntity.getIngredientId());
                        pancakeIngredientResponse.setIngredientName(ingredientEntity.getName());
                        pancakeIngredientResponse.setPrice(price);
                        return pancakeIngredientResponse;
                    })
                .collect(Collectors.toList());
    }
}
