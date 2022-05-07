package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.PancakesUnlimitedApplication;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.exceptions.PancakeAlreadyOrderedException;
import net.croz.pancakes_unlimited.exceptions.PancakeNotValidException;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.entities.*;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;
import net.croz.pancakes_unlimited.models.responses.PancakeIngredientResponse;
import net.croz.pancakes_unlimited.repositories.IngredientEntityRepository;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.PancakeService;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PancakesUnlimitedApplication.class)
class PancakeServiceImplTest
{
    @Mock
    private IngredientEntityRepository ingredientRepository;
    @Mock
    private PancakeEntityRepository pancakeRepository;
    @InjectMocks
    private ModelMapper modelMapper;

    private PancakeService serviceUnderTest;
    EntityManager entityManager;

    @BeforeEach
    public void setUp()
    {
        var pancakeServiceImpl = new PancakeServiceImpl(pancakeRepository, ingredientRepository, modelMapper);
        entityManager = mock(EntityManager.class);
        pancakeServiceImpl.setEntityManager(entityManager);
        serviceUnderTest = pancakeServiceImpl;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void findAll_returnResult()
    {
        PancakeEntity entity1 = new PancakeEntity();
        BigDecimal ingredientPrice = new BigDecimal("2.12");
        IngredientEntity ingredientEntity = new IngredientEntity(1, "americka palacinka", true, new BigDecimal("1.2"), new CategoryEntity(1, "baza"));
        List<PancakeHasIngredient> pancakeHasIngredient = List.of(new PancakeHasIngredient(entity1, ingredientEntity, ingredientPrice));
        entity1 = new PancakeEntity(1, pancakeHasIngredient, null);
        when(pancakeRepository.findAll()).thenReturn(List.of(entity1));

        var resultDTO = serviceUnderTest.findAll();

        var expectedResult = List.of(new PancakeDTO(1, List.of(new PancakeIngredientResponse(1, "americka palacinka", ingredientPrice, new CategoryDTO(1, "baza")))));

        assertThat(resultDTO).isEqualTo(expectedResult);

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void findById_returnResult()
    {
        int id = 1;
        PancakeEntity entity1 = new PancakeEntity();
        BigDecimal ingredientPrice = new BigDecimal("2.12");
        IngredientEntity ingredientEntity = new IngredientEntity(1, "americka palacinka", true, new BigDecimal("1.2"), new CategoryEntity(1, "baza"));
        List<PancakeHasIngredient> pancakeHasIngredient = List.of(new PancakeHasIngredient(entity1, ingredientEntity, ingredientPrice));
        entity1 = new PancakeEntity(id, pancakeHasIngredient, null);

        when(pancakeRepository.findById(id)).thenReturn(Optional.of(entity1));

        var returnedValue = serviceUnderTest.findById(id);
        verify(pancakeRepository).findById(id);

        var expectedResult = new PancakeDTO(id, List.of(new PancakeIngredientResponse(1, "americka palacinka", ingredientPrice, new CategoryDTO(1, "baza"))));

        assertThat(returnedValue).isEqualTo(expectedResult);
    }

    @Test
    void findById_throwNotFoundException()
    {
        int id = -1;
        when(pancakeRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> serviceUnderTest.findById(id))
                .isInstanceOf(NotFoundException.class);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void insert_validRequest_returnResult()
    {
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));

        BigDecimal ingredientPrice1 = new BigDecimal("2.12");
        BigDecimal ingredientPrice2 = new BigDecimal("3.69");
        IngredientEntity ingredientEntity1 = new IngredientEntity(id1, "americka palacinka", true, ingredientPrice1, new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(id2, "nutela", false, ingredientPrice2, new CategoryEntity(2, "fil"));

        when(ingredientRepository.findById(id1)).thenReturn(Optional.of(ingredientEntity1));
        when(ingredientRepository.findById(id2)).thenReturn(Optional.of(ingredientEntity2));

        PancakeEntity entity1 = new PancakeEntity();
        var pancakeHasIngredient1 = new PancakeHasIngredient(entity1, ingredientEntity1, ingredientPrice1);
        var pancakeHasIngredient2 = new PancakeHasIngredient(entity1, ingredientEntity2, ingredientPrice2);

        List<PancakeHasIngredient> pancakeHasIngredientList = Arrays.asList(
                pancakeHasIngredient1, pancakeHasIngredient2
        );
        entity1.setPancakeIngredients(pancakeHasIngredientList);

        PancakeEntity entityWithId = new PancakeEntity();
        entityWithId.setPancakeIngredients(pancakeHasIngredientList);
        entityWithId.setId(1);

        when(pancakeRepository.saveAndFlush(entity1)).thenReturn(entityWithId);

        var resultDTO = serviceUnderTest.insert(request);

        var expectedDTO = new PancakeDTO(1, List.of(
                new PancakeIngredientResponse(1, "americka palacinka", ingredientPrice1, new CategoryDTO(1, "baza")),
                new PancakeIngredientResponse(2, "nutela", ingredientPrice2, new CategoryDTO(2, "fil"))
        ));
        assertThat(resultDTO).isEqualTo(expectedDTO);
    }

    @Test
    void insert_invalidRequest_IngredientDoesntExist_throwNotFoundException()
    {
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));
        when(ingredientRepository.findById(id1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> serviceUnderTest.insert(request))
                .isInstanceOf(NotFoundException.class);

    }

    @Test
    void insert_invalidRequest_noBase_throwPancakeNotValidException()
    {
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));
        IngredientEntity ingredientEntity1 = new IngredientEntity(id1, "eurokrem", true, new BigDecimal(0), new CategoryEntity(1, "fil"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(id2, "nutela", false, new BigDecimal(0), new CategoryEntity(1, "fil"));

        when(ingredientRepository.findById(id1)).thenReturn(Optional.of(ingredientEntity1));
        when(ingredientRepository.findById(id2)).thenReturn(Optional.of(ingredientEntity2));

        assertThatThrownBy(() -> serviceUnderTest.insert(request))
                .isInstanceOf(PancakeNotValidException.class)
                .hasMessageContaining(PancakeNotValidException.ONLY_ONE_BASE_ALLOWED);
    }

    @Test
    void insert_invalidRequest_moreThanOneBase_throwPancakeNotValidException()
    {
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));
        IngredientEntity ingredientEntity1 = new IngredientEntity(id1, "eurokrem", true, new BigDecimal(0), new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(id2, "nutela", false, new BigDecimal(0), new CategoryEntity(1, "baza"));

        when(ingredientRepository.findById(id1)).thenReturn(Optional.of(ingredientEntity1));
        when(ingredientRepository.findById(id2)).thenReturn(Optional.of(ingredientEntity2));

        assertThatThrownBy(() -> serviceUnderTest.insert(request))
                .isInstanceOf(PancakeNotValidException.class)
                .hasMessageContaining(PancakeNotValidException.ONLY_ONE_BASE_ALLOWED);
    }

    @Test
    void insert_invalidRequest_noFil_throwPancakeNotValidException()
    {
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));
        IngredientEntity ingredientEntity1 = new IngredientEntity(id1, "eurokrem", true, new BigDecimal(0), new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(id2, "nutela", false, new BigDecimal(0), new CategoryEntity(1, "preliv"));

        when(ingredientRepository.findById(id1)).thenReturn(Optional.of(ingredientEntity1));
        when(ingredientRepository.findById(id2)).thenReturn(Optional.of(ingredientEntity2));

        assertThatThrownBy(() -> serviceUnderTest.insert(request))
                .isInstanceOf(PancakeNotValidException.class)
                .hasMessageContaining(PancakeNotValidException.AT_LEAST_ONE_FIL_ALLOWED);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void update_validRequest_returnResult()
    {
        int id = 1;
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));

        BigDecimal ingredientPrice1 = new BigDecimal("2.12");
        BigDecimal ingredientPrice2 = new BigDecimal("3.69");
        IngredientEntity ingredientEntity1 = new IngredientEntity(id1, "americka palacinka", true, ingredientPrice1, new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(id2, "nutela", false, ingredientPrice2, new CategoryEntity(2, "fil"));

        PancakeEntity entity1 = new PancakeEntity();
        var pancakeHasIngredient1 = new PancakeHasIngredient(entity1, ingredientEntity1, ingredientPrice1);
        var pancakeHasIngredient2 = new PancakeHasIngredient(entity1, ingredientEntity2, ingredientPrice2);
        entity1.setId(id);

        PancakeEntity entity2 = new PancakeEntity();
        var pancakeHasIngredient3 = new PancakeHasIngredient(entity1, ingredientEntity2, ingredientPrice2);

        List<PancakeHasIngredient> pancakeHasIngredientList2 = List.of(
                pancakeHasIngredient1, pancakeHasIngredient2, pancakeHasIngredient3
        );
        entity2.setPancakeIngredients(pancakeHasIngredientList2);
        entity2.setId(id);

        when(pancakeRepository.existsById(id)).thenReturn(true);
        when(pancakeRepository.findById(id)).thenReturn(Optional.of(entity1));
        when(ingredientRepository.findById(id1)).thenReturn(Optional.of(ingredientEntity1));
        when(ingredientRepository.findById(id2)).thenReturn(Optional.of(ingredientEntity2));
        when(pancakeRepository.saveAndFlush(entity1)).thenReturn(entity2);

        var resultDTO = serviceUnderTest.update(id, request);

        var expectedDTO = new PancakeDTO(1, List.of(
                new PancakeIngredientResponse(1, "americka palacinka", ingredientPrice1, new CategoryDTO(1, "baza")),
                new PancakeIngredientResponse(2, "nutela", ingredientPrice2, new CategoryDTO(2, "fil")),
                new PancakeIngredientResponse(2, "nutela", ingredientPrice2, new CategoryDTO(2, "fil"))

        ));
        assertThat(resultDTO).isEqualTo(expectedDTO);
    }

    @Test
    void update_invalidRequest_pancakeDoesntExist_throwNotFoundException()
    {
        int id = 1;
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));

        when(pancakeRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> serviceUnderTest.update(id, request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_invalidRequest_noBase_throwPancakeNotValidException()
    {
        int id = 1;
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));
        IngredientEntity ingredientEntity1 = new IngredientEntity(id1, "eurokrem", true, new BigDecimal(0), new CategoryEntity(1, "fil"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(id2, "nutela", false, new BigDecimal(0), new CategoryEntity(1, "fil"));

        when(pancakeRepository.existsById(id)).thenReturn(true);
        when(ingredientRepository.findById(id1)).thenReturn(Optional.of(ingredientEntity1));
        when(ingredientRepository.findById(id2)).thenReturn(Optional.of(ingredientEntity2));

        assertThatThrownBy(() -> serviceUnderTest.update(id, request))
                .isInstanceOf(PancakeNotValidException.class)
                .hasMessageContaining(PancakeNotValidException.ONLY_ONE_BASE_ALLOWED);
    }

    @Test
    void update_invalidRequest_moreThanOneBase_throwPancakeNotValidException()
    {
        int id = 1;
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));
        IngredientEntity ingredientEntity1 = new IngredientEntity(id1, "eurokrem", true, new BigDecimal(0), new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(id2, "nutela", false, new BigDecimal(0), new CategoryEntity(1, "baza"));

        when(pancakeRepository.existsById(id)).thenReturn(true);
        when(ingredientRepository.findById(id1)).thenReturn(Optional.of(ingredientEntity1));
        when(ingredientRepository.findById(id2)).thenReturn(Optional.of(ingredientEntity2));

        assertThatThrownBy(() -> serviceUnderTest.update(id, request))
                .isInstanceOf(PancakeNotValidException.class)
                .hasMessageContaining(PancakeNotValidException.ONLY_ONE_BASE_ALLOWED);
    }

    @Test
    void update_invalidRequest_noFil_throwPancakeNotValidException()
    {
        int id = 1;
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));
        IngredientEntity ingredientEntity1 = new IngredientEntity(id1, "eurokrem", true, new BigDecimal(0), new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(id2, "nutela", false, new BigDecimal(0), new CategoryEntity(1, "preliv"));

        when(pancakeRepository.existsById(id)).thenReturn(true);
        when(ingredientRepository.findById(id1)).thenReturn(Optional.of(ingredientEntity1));
        when(ingredientRepository.findById(id2)).thenReturn(Optional.of(ingredientEntity2));

        assertThatThrownBy(() -> serviceUnderTest.update(id, request))
                .isInstanceOf(PancakeNotValidException.class)
                .hasMessageContaining(PancakeNotValidException.AT_LEAST_ONE_FIL_ALLOWED);
    }

    @Test
    void update_invalidRequest_alreadyOrdered_throwPancakeAlreadyOrderedException()
    {
        int id = 1;
        int id1 = 1, id2 = 2;
        PancakeRequest request = new PancakeRequest(new HashSet<>(Arrays.asList(id1, id2)));
        IngredientEntity ingredientEntity1 = new IngredientEntity(id1, "eurokrem", true, new BigDecimal(0), new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(id2, "nutela", false, new BigDecimal(0), new CategoryEntity(1, "fil"));

        PancakeEntity entity1 = new PancakeEntity();
        entity1.setId(id);
        entity1.setOrder(new OrderEntity());

        when(ingredientRepository.findById(id1)).thenReturn(Optional.of(ingredientEntity1));
        when(ingredientRepository.findById(id2)).thenReturn(Optional.of(ingredientEntity2));
        when(pancakeRepository.existsById(id)).thenReturn(true);
        when(pancakeRepository.findById(id)).thenReturn(Optional.of(entity1));

        assertThatThrownBy(() -> serviceUnderTest.update(id, request))
                .isInstanceOf(PancakeAlreadyOrderedException.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void delete_successful()
    {
        int id = 1;
        PancakeEntity entity1 = new PancakeEntity();
        entity1.setId(id);
        entity1.setOrder(null);

        when(pancakeRepository.findById(id)).thenReturn(Optional.of(entity1));

        serviceUnderTest.delete(id);

        verify(pancakeRepository).deleteById(id);
    }

    @Test
    void delete_throwAlreadyOrderedException()
    {
        int id = 1;
        PancakeEntity entity1 = new PancakeEntity();
        entity1.setId(id);
        entity1.setOrder(new OrderEntity());
        when(pancakeRepository.findById(id)).thenReturn(Optional.of(entity1));

        assertThatThrownBy(() -> serviceUnderTest.delete(id))
                .isInstanceOf(PancakeAlreadyOrderedException.class);
    }

    @Test
    void delete_throwNotFoundException()
    {
        int id = 1;
        when(pancakeRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceUnderTest.delete(id))
                .isInstanceOf(NotFoundException.class);
    }
}