package net.croz.pancakes_unlimited.services.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PancakeServiceImpl implements PancakeService
{
    private final PancakeEntityRepository pancakeRepository;
    private final IngredientEntityRepository ingredientRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private final ModelMapper modelMapper;

    public PancakeServiceImpl(PancakeEntityRepository pancakeRepository,
                              IngredientEntityRepository ingredientRepository,
                              ModelMapper modelMapper)
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


    // merging same  (making distinct) ingredients
    @Override
    public PancakeDTO insert(PancakeRequest pancakeRequest)
    {
        List<IngredientEntity> ingredientEntityList = getIngredientEntitiesByIds(pancakeRequest.getIngredientsId());
        ingredientEntityList = ingredientEntityList.stream().distinct().collect(Collectors.toList());

        PancakeEntity newPancake = new PancakeEntity();
        PancakeEntity finalNewPancake = newPancake; // Variable used in lambda expression should be final or effectively final
        List<PancakeHasIngredient> pancakeHasIngredients = createPancakeHasIngredients(ingredientEntityList, finalNewPancake);

        newPancake.setPancakeIngredients(pancakeHasIngredients);
        newPancake = pancakeRepository.saveAndFlush(newPancake);
        entityManager.refresh(newPancake);
        return mapPancakeEntityToPancakeDTO(newPancake);
    }

    @Override
    public PancakeDTO update(Integer id, PancakeRequest pancakeRequest)
    {
        if (!pancakeRepository.existsById(id))
            throw new NotFoundException();

        List<IngredientEntity> ingredientEntityList = getIngredientEntitiesByIds(pancakeRequest.getIngredientsId());
        PancakeEntity newPancake = pancakeRepository.findById(id).orElseThrow(NotFoundException::new);

        var newPancakeHasIngredient = createPancakeHasIngredients(ingredientEntityList, newPancake);

        newPancake.setId(id);
        newPancake.getPancakeIngredients().clear();
        newPancake.getPancakeIngredients().addAll(newPancakeHasIngredient);

        newPancake = pancakeRepository.saveAndFlush(newPancake);
        entityManager.refresh(newPancake);

        return mapPancakeEntityToPancakeDTO(newPancake);
    }

    @Override
    public void delete(Integer id)
    {
        if (!pancakeRepository.existsById(id))
            throw new NotFoundException();
        pancakeRepository.deleteById(id);
    }

    private List<PancakeHasIngredient> createPancakeHasIngredients(List<IngredientEntity> ingredientEntityList, PancakeEntity pancake)
    {
        return ingredientEntityList.stream().map(ingredientEntity ->
            {
                PancakeHasIngredient pancakeHasIngredient = new PancakeHasIngredient();
                pancakeHasIngredient.setPancake(pancake);
                pancakeHasIngredient.setIngredient(ingredientEntity);
                pancakeHasIngredient.setPrice(ingredientEntity.getPrice());
                return pancakeHasIngredient;
            }).collect(Collectors.toList());
    }

    private List<IngredientEntity> getIngredientEntitiesByIds(List<Integer> ingredientsId)
    {
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        for (Integer ingredientId : ingredientsId)
        {
            ingredientEntityList.add(ingredientRepository.findById(ingredientId).orElseThrow(() -> new NotFoundException("ingredient with id \"" + ingredientId + "\" does not exist")));
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

                        CategoryEntity categoryEntity = ingredientEntity.getIngredientCategory();
                        pancakeIngredientResponse.setIngredientCategory(modelMapper.map(categoryEntity, CategoryDTO.class));

                        return pancakeIngredientResponse;
                    })
                .collect(Collectors.toList());
    }
}
