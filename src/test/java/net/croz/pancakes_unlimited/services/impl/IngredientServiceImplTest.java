package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.PancakesUnlimitedApplication;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.models.requests.IngredientRequest;
import net.croz.pancakes_unlimited.repositories.CategoryEntityRepository;
import net.croz.pancakes_unlimited.repositories.IngredientEntityRepository;
import net.croz.pancakes_unlimited.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PancakesUnlimitedApplication.class)
class IngredientServiceImplTest
{
    @Mock
    private IngredientEntityRepository ingredientRepository;
    @Mock
    private CategoryEntityRepository categoryRepository;
    @InjectMocks
    private ModelMapper modelMapper;

    private IngredientService serviceUnderTest;
    EntityManager entityManager;

    @BeforeEach
    public void setUp()
    {
        var ingredientServiceImp = new IngredientServiceImpl(modelMapper, ingredientRepository, categoryRepository);
        entityManager = mock(EntityManager.class);
        ingredientServiceImp.setEntityManager(entityManager);
        serviceUnderTest = ingredientServiceImp;
    }

    @Test
    void findAll_returnResult()
    {
        IngredientEntity entity1 = new IngredientEntity(1, "americka palacinka", true, new BigDecimal("1.2"), new CategoryEntity(1, "baza"));
        IngredientEntity entity2 = new IngredientEntity(2, "nutela", false, new BigDecimal("2.2"), new CategoryEntity(2, "fil"));
        when(ingredientRepository.findAll()).thenReturn(List.of(entity1, entity2));

        var resultDTO = serviceUnderTest.findAll();

        List<IngredientDTO> expectedResult = List.of(
                new IngredientDTO(1, "americka palacinka", true, new BigDecimal("1.2"), new CategoryDTO(1, "baza")),
                new IngredientDTO(2, "nutela", false, new BigDecimal("2.2"), new CategoryDTO(2, "fil")));

        assertThat(resultDTO).isEqualTo(expectedResult);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void findById_returnResult()
    {
        int id = 1;
        IngredientEntity entity1 = new IngredientEntity(1, "americka palacinka", true, new BigDecimal("1.2"), new CategoryEntity(1, "baza"));

        when(ingredientRepository.findById(id)).thenReturn(Optional.of(entity1));

        var returnedValue = serviceUnderTest.findById(id);
        verify(ingredientRepository).findById(id);

        IngredientDTO expectedResult = new IngredientDTO(1, "americka palacinka", true, new BigDecimal("1.2"), new CategoryDTO(1, "baza"));

        assertThat(returnedValue).isEqualTo(expectedResult);
    }

    @Test
    void findById_throwNotFoundException()
    {
        int id = -1;
        when(ingredientRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> serviceUnderTest.findById(id))
                .isInstanceOf(NotFoundException.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void insert_validRequest_returnResult()
    {
        var request = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.2"));
        var categoryEntity = new CategoryEntity(2, "fil");
        var ingredientEntity = new IngredientEntity(null, "nutela", true, new BigDecimal("1.2"), categoryEntity);
        var ingredientEntityWithId = new IngredientEntity(1, "nutela", true, new BigDecimal("1.2"), categoryEntity);

        when(categoryRepository.findByName(request.getCategoryName())).thenReturn(Optional.of(categoryEntity));
        when(ingredientRepository.saveAndFlush(ingredientEntity)).thenReturn(ingredientEntityWithId);

        var resultDTO = serviceUnderTest.insert(request);

        verify(ingredientRepository).saveAndFlush(ingredientEntity);
        verify(categoryRepository).findByName(request.getCategoryName());

        var expectedDTO = new IngredientDTO(1, "nutela", true, new BigDecimal("1.2"), new CategoryDTO(2, "fil"));
        assertThat(resultDTO).isEqualTo(expectedDTO);
    }

    @Test
    void insert_invalidRequest_categoryDoesntExist_throwNotFoundException()
    {
        var request = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.2"));

        when(categoryRepository.findByName(request.getCategoryName())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceUnderTest.insert(request))
                .isInstanceOf(NotFoundException.class);

    }

    @Test
    void insert_invalidRequest_mandatoryNotNullField()
    {
        var request = new IngredientRequest("nutela", "null", true, null);

        var categoryEntity = new CategoryEntity(2, "fil");

        when(categoryRepository.findByName(request.getCategoryName())).thenReturn(Optional.of(categoryEntity));

        assertThatThrownBy(() -> serviceUnderTest.insert(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void update_validRequest_returnResult()
    {
        int id = 1;
        when(ingredientRepository.existsById(id)).thenReturn(true);

        var request = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.2"));
        var categoryEntity = new CategoryEntity(2, "fil");
        var ingredientEntityWithId = new IngredientEntity(1, "nutela", true, new BigDecimal("1.2"), categoryEntity);

        when(categoryRepository.findByName(request.getCategoryName())).thenReturn(Optional.of(categoryEntity));
        when(ingredientRepository.saveAndFlush(ingredientEntityWithId)).thenReturn(ingredientEntityWithId);

        var resultDTO = serviceUnderTest.update(id, request);

        verify(ingredientRepository).saveAndFlush(ingredientEntityWithId);
        verify(categoryRepository).findByName(request.getCategoryName());

        var expectedDTO = new IngredientDTO(1, "nutela", true, new BigDecimal("1.2"), new CategoryDTO(2, "fil"));
        assertThat(resultDTO).isEqualTo(expectedDTO);
    }

    @Test
    void update_invalidRequest_ingredientDoesntExist_throwNotFoundException()
    {
        int id = 1;
        when(ingredientRepository.existsById(id)).thenReturn(false);

        var request = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.2"));

        assertThatThrownBy(() -> serviceUnderTest.update(id,request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_invalidRequest_categoryDoesntExist_throwNotFoundException()
    {
        int id = 1;
        when(ingredientRepository.existsById(id)).thenReturn(true);

        var request = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.2"));
        when(categoryRepository.findByName(request.getCategoryName())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceUnderTest.update(id,request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_invalidRequest_mandatoryNotNullField()
    {
        int id = 1;
        var request = new IngredientRequest("nutela", "null", true, null);

        var categoryEntity = new CategoryEntity(2, "fil");

        when(ingredientRepository.existsById(id)).thenReturn(true);
        when(categoryRepository.findByName(request.getCategoryName())).thenReturn(Optional.of(categoryEntity));

        assertThatThrownBy(() -> serviceUnderTest.update(id,request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void delete_successful()
    {
        int id = 1;
        when(ingredientRepository.existsById(id)).thenReturn(true);

        serviceUnderTest.delete(id);

        verify(ingredientRepository).deleteById(id);
    }

    @Test
    void delete_throwException()
    {
        int id = 1;
        when(ingredientRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> serviceUnderTest.delete(id))
                .isInstanceOf(NotFoundException.class);
    }


}