package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.exceptions.PancakeAlreadyOrderedException;
import net.croz.pancakes_unlimited.exceptions.PancakeNotValidException;
import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeHasIngredient;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;
import net.croz.pancakes_unlimited.repositories.IngredientEntityRepository;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.PancakeService;
import net.croz.pancakes_unlimited.utils.MapUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.croz.pancakes_unlimited.exceptions.PancakeNotValidException.AT_LEAST_ONE_FIL_ALLOWED;
import static net.croz.pancakes_unlimited.exceptions.PancakeNotValidException.ONLY_ONE_BASE_ALLOWED;

@Service
@Transactional
public class PancakeServiceImpl implements PancakeService
{
    public static final int NUMBER_OF_ALLOWED_BASES = 1;
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
        return pancakeEntityList.stream()
                .map(pancakeEntity -> MapUtils.mapPancakeEntityToPancakeDTO(pancakeEntity, modelMapper))
                .collect(Collectors.toList());
    }

    @Override
    public PancakeDTO findById(Integer id)
    {
        PancakeEntity pancakeEntity = pancakeRepository.findById(id).orElseThrow(()->new NotFoundException(PancakeEntity.class,id));
        return MapUtils.mapPancakeEntityToPancakeDTO(pancakeEntity, modelMapper);
    }

    @Override
    public PancakeDTO insert(PancakeRequest pancakeRequest)
    {
        List<Integer> requestIngredientsId = new ArrayList(pancakeRequest.getIngredientsId());
        List<IngredientEntity> ingredientEntityList = getIngredientEntitiesByIds(requestIngredientsId);
        this.validatePancakeIngredients(ingredientEntityList);

        ingredientEntityList = ingredientEntityList.stream().distinct().collect(Collectors.toList());

        PancakeEntity newPancake = new PancakeEntity();
        List<PancakeHasIngredient> pancakeHasIngredients = MapUtils.createPancakeHasIngredients(ingredientEntityList, newPancake);

        newPancake.setPancakeIngredients(pancakeHasIngredients);
        newPancake = pancakeRepository.saveAndFlush(newPancake);
        entityManager.refresh(newPancake);
        return MapUtils.mapPancakeEntityToPancakeDTO(newPancake, modelMapper);
    }

    @Override
    public PancakeDTO update(Integer id, PancakeRequest pancakeRequest)
    {
        if (!pancakeRepository.existsById(id))
            throw new NotFoundException(PancakeEntity.class,id);

        List<Integer> requestIngredientsId = new ArrayList(pancakeRequest.getIngredientsId());
        List<IngredientEntity> ingredientEntityList = getIngredientEntitiesByIds(requestIngredientsId);
        this.validatePancakeIngredients(ingredientEntityList);

        PancakeEntity pancakeToUpdate = pancakeRepository.findById(id).orElseThrow(()->new NotFoundException(PancakeEntity.class,id));
        if (pancakeToUpdate.getOrder() != null)
            throw new PancakeAlreadyOrderedException();

        var newPancakeHasIngredient = MapUtils.createPancakeHasIngredients(ingredientEntityList, pancakeToUpdate);

        pancakeToUpdate.setId(id);
        pancakeToUpdate.getPancakeIngredients().clear();
        pancakeToUpdate.getPancakeIngredients().addAll(newPancakeHasIngredient);

        pancakeToUpdate = pancakeRepository.saveAndFlush(pancakeToUpdate);
        entityManager.refresh(pancakeToUpdate);

        return MapUtils.mapPancakeEntityToPancakeDTO(pancakeToUpdate, modelMapper);
    }

    @Override
    public void delete(Integer id)
    {
        PancakeEntity pancake = pancakeRepository.findById(id).orElseThrow(()->new NotFoundException(PancakeEntity.class,id));
        if (pancake.getOrder() != null)
            throw new PancakeAlreadyOrderedException();

        pancakeRepository.deleteById(id);
    }

    public void validatePancakeIngredients(List<IngredientEntity> ingredientEntityList)
    {
        long numOfBases = ingredientEntityList.stream()
                .filter(ingredient -> "baza".equals(ingredient.getIngredientCategory().getName().toLowerCase().trim()))
                .count();
        if (numOfBases != NUMBER_OF_ALLOWED_BASES)
            throw new PancakeNotValidException(ONLY_ONE_BASE_ALLOWED);

        boolean hasIngredientOfTypeFil = ingredientEntityList.stream()
                .anyMatch(ingredient -> "fil".equals(ingredient.getIngredientCategory().getName().toLowerCase().trim()));
        if (!hasIngredientOfTypeFil)
            throw new PancakeNotValidException(AT_LEAST_ONE_FIL_ALLOWED);
    }

    private List<IngredientEntity> getIngredientEntitiesByIds(List<Integer> ingredientsId)
    {
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        for (Integer ingredientId : ingredientsId)
        {
            ingredientEntityList.add(ingredientRepository
                    .findById(ingredientId)
                    .orElseThrow(() -> new NotFoundException(IngredientEntity.class, ingredientId)));
        }
        return ingredientEntityList;
    }

    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
}
