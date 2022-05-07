package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.PancakesUnlimitedApplication;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.exceptions.PancakeAlreadyOrderedException;
import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.entities.*;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;
import net.croz.pancakes_unlimited.models.responses.OrderedPancakeResp;
import net.croz.pancakes_unlimited.repositories.OrderEntityRepository;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.OrderService;
import net.croz.pancakes_unlimited.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PancakesUnlimitedApplication.class)
class OrderServiceImplTest
{
    @Mock
    private PancakeEntityRepository pancakeRepository;
    @Mock
    private OrderEntityRepository orderRepository;
    @InjectMocks
    private ModelMapper modelMapper;

    private OrderService serviceUnderTest;
    EntityManager entityManager;

    @BeforeEach
    public void setUp()
    {
        var ingredientServiceImp = new OrderServiceImpl(pancakeRepository, orderRepository, modelMapper);
        entityManager = mock(EntityManager.class);
        ingredientServiceImp.setEntityManager(entityManager);
        serviceUnderTest = ingredientServiceImp;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void findAll_returnResult_healthyPancakeDiscount()
    {
        Date date = new Date();
        String orderNumber = UUID.randomUUID().toString();
        PancakeEntity pancakeEntity = new PancakeEntity();
        BigDecimal ingredientPrice1 = new BigDecimal("10");
        BigDecimal ingredientPrice2 = new BigDecimal("10");
        IngredientEntity ingredientEntity1 = new IngredientEntity(1, "americka palacinka", true, ingredientPrice1, new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(2, "nutela", true, ingredientPrice2, new CategoryEntity(1, "baza"));

        List<PancakeHasIngredient> pancakeHasIngredient = List.of(
                new PancakeHasIngredient(pancakeEntity, ingredientEntity1, ingredientPrice1),
                new PancakeHasIngredient(pancakeEntity, ingredientEntity2, ingredientPrice2)
        );
        pancakeEntity = new PancakeEntity(1, pancakeHasIngredient, null);
        OrderEntity entity1 = new OrderEntity(1, date, "cool description", orderNumber, List.of(pancakeEntity));
        when(orderRepository.findAll()).thenReturn(List.of(entity1));

        var resultDTO = serviceUnderTest.findAll();

        BigDecimal pancakePrice = ingredientPrice1.add(ingredientPrice2);
        BigDecimal discount = new BigDecimal("3.00");
        List<OrderDTO> expectedResult = List.of(
                new OrderDTO(1, date, "cool description", orderNumber, List.of(new OrderedPancakeResp(1, pancakePrice)),
                        pancakePrice, discount, pancakePrice.subtract(discount)
                )
        );

        assertThat(resultDTO).isEqualTo(expectedResult);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void findById_returnResult_orderPrice_MoreThan20_LessOrEqualsThan50_healthyPancake()
    {
        int id = 1;
        Date date = new Date();
        String orderNumber = UUID.randomUUID().toString();
        PancakeEntity pancakeEntity = new PancakeEntity();
        BigDecimal ingredientPrice1 = new BigDecimal("10");
        BigDecimal ingredientPrice2 = new BigDecimal("10.1");
        IngredientEntity ingredientEntity1 = new IngredientEntity(1, "americka palacinka", true, ingredientPrice1, new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(2, "nutela", true, ingredientPrice2, new CategoryEntity(1, "baza"));

        List<PancakeHasIngredient> pancakeHasIngredient = List.of(
                new PancakeHasIngredient(pancakeEntity, ingredientEntity1, ingredientPrice1),
                new PancakeHasIngredient(pancakeEntity, ingredientEntity2, ingredientPrice2)
        );
        pancakeEntity = new PancakeEntity(1, pancakeHasIngredient, null);
        OrderEntity entity1 = new OrderEntity(id, date, "cool description", orderNumber, List.of(pancakeEntity));

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity1));

        var resultDTO = serviceUnderTest.findById(id);
        verify(orderRepository).findById(id);

        BigDecimal pancakePrice = ingredientPrice1.add(ingredientPrice2);
        BigDecimal discount = Utils.percentage(pancakePrice, new BigDecimal("5"));
        var expectedResult = new OrderDTO(1, date, "cool description", orderNumber,
                List.of(new OrderedPancakeResp(1, pancakePrice)),
                pancakePrice, discount, pancakePrice.subtract(discount));

        assertThat(resultDTO).isEqualTo(expectedResult);
    }


    @Test
    void findById_returnResult_orderPrice_MoreThan20_LessOrEqualsThan50_noDiscount()
    {
        int id = 1;
        Date date = new Date();
        String orderNumber = UUID.randomUUID().toString();
        PancakeEntity pancakeEntity = new PancakeEntity();
        BigDecimal ingredientPrice1 = new BigDecimal("10");
        BigDecimal ingredientPrice2 = new BigDecimal("10");
        IngredientEntity ingredientEntity1 = new IngredientEntity(1, "americka palacinka", true, ingredientPrice1, new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(2, "nutela", false, ingredientPrice2, new CategoryEntity(1, "baza"));

        List<PancakeHasIngredient> pancakeHasIngredient = List.of(
                new PancakeHasIngredient(pancakeEntity, ingredientEntity1, ingredientPrice1),
                new PancakeHasIngredient(pancakeEntity, ingredientEntity2, ingredientPrice2)
        );
        pancakeEntity = new PancakeEntity(1, pancakeHasIngredient, null);
        OrderEntity entity1 = new OrderEntity(id, date, "cool description", orderNumber, List.of(pancakeEntity));

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity1));

        var resultDTO = serviceUnderTest.findById(id);
        verify(orderRepository).findById(id);

        BigDecimal pancakePrice = ingredientPrice1.add(ingredientPrice2);
        BigDecimal discount = new BigDecimal(0);
        var expectedResult = new OrderDTO(1, date, "cool description", orderNumber,
                List.of(new OrderedPancakeResp(1, pancakePrice)),
                pancakePrice, discount, pancakePrice.subtract(discount));

        assertThat(resultDTO).isEqualTo(expectedResult);
    }

    @Test
    void findById_throwNotFoundException()
    {
        int id = -1;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> serviceUnderTest.findById(id))
                .isInstanceOf(NotFoundException.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void findByOrderNumber_returnResult_orderPrice_MoreThan20_LessOrEqualsThan50()
    {
        int id = 1;
        String orderNumber = UUID.randomUUID().toString();

        Date date = new Date();
        PancakeEntity pancakeEntity = new PancakeEntity();
        BigDecimal ingredientPrice1 = new BigDecimal("25");
        BigDecimal ingredientPrice2 = new BigDecimal("25");
        IngredientEntity ingredientEntity1 = new IngredientEntity(1, "americka palacinka", true, ingredientPrice1, new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(2, "nutela", true, ingredientPrice2, new CategoryEntity(1, "baza"));

        List<PancakeHasIngredient> pancakeHasIngredient = List.of(
                new PancakeHasIngredient(pancakeEntity, ingredientEntity1, ingredientPrice1),
                new PancakeHasIngredient(pancakeEntity, ingredientEntity2, ingredientPrice2)
        );
        pancakeEntity = new PancakeEntity(1, pancakeHasIngredient, null);
        OrderEntity entity1 = new OrderEntity(id, date, "cool description", orderNumber, List.of(pancakeEntity));

        when(orderRepository.findOrderEntityByOrderNumber(orderNumber)).thenReturn(Optional.of(entity1));

        var resultDTO = serviceUnderTest.findByOrderNumber(orderNumber);
        verify(orderRepository).findOrderEntityByOrderNumber(orderNumber);

        BigDecimal pancakePrice = ingredientPrice1.add(ingredientPrice2);
        BigDecimal discount = Utils.percentage(pancakePrice, new BigDecimal("5"));
        var expectedResult = new OrderDTO(1, date, "cool description", orderNumber,
                List.of(new OrderedPancakeResp(1, pancakePrice)),
                pancakePrice, discount, pancakePrice.subtract(discount));

        assertThat(resultDTO).isEqualTo(expectedResult);

    }

    @Test
    void findByOrderNumber_throwNotFoundException()
    {
        String orderNumber = UUID.randomUUID().toString();
        when(orderRepository.findOrderEntityByOrderNumber(orderNumber)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> serviceUnderTest.findByOrderNumber(orderNumber))
                .isInstanceOf(NotFoundException.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void insert_validRequest_returnResult_totalPrice_moreThan50()
    {
        int id = 1, id1 = 1;
        var request = new OrderRequest("cool description", new HashSet<>(List.of(id1)));

        PancakeEntity pancakeEntity = new PancakeEntity();
        var instant = Instant.now();
        Date date = Date.from(instant);
        var orderNumber = UUID.randomUUID();
        BigDecimal ingredientPrice1 = new BigDecimal("25");
        BigDecimal ingredientPrice2 = new BigDecimal("25.1");
        IngredientEntity ingredientEntity1 = new IngredientEntity(1, "americka palacinka", true, ingredientPrice1, new CategoryEntity(1, "baza"));
        IngredientEntity ingredientEntity2 = new IngredientEntity(2, "nutela", true, ingredientPrice2, new CategoryEntity(1, "baza"));

        List<PancakeHasIngredient> pancakeHasIngredient = List.of(
                new PancakeHasIngredient(pancakeEntity, ingredientEntity1, ingredientPrice1),
                new PancakeHasIngredient(pancakeEntity, ingredientEntity2, ingredientPrice2)
        );
        pancakeEntity.setPancakeIngredients(pancakeHasIngredient);
        pancakeEntity.setId(1);
        pancakeEntity = new PancakeEntity(1, pancakeHasIngredient, null);
        OrderEntity entity1 = new OrderEntity(null, date, "cool description", orderNumber.toString(), List.of(pancakeEntity));
        OrderEntity entityWithId = new OrderEntity(id, date, "cool description", orderNumber.toString(), List.of(pancakeEntity));


        try (MockedStatic<UUID> utilities = Mockito.mockStatic(UUID.class);
             MockedStatic<Instant> system = Mockito.mockStatic(Instant.class)
        )
        {
            utilities.when(UUID::randomUUID).thenReturn(orderNumber);
            system.when(Instant::now).thenReturn(instant);

            when(pancakeRepository.findById(id1)).thenReturn(Optional.of(pancakeEntity));
            when(orderRepository.saveAndFlush(entity1)).thenReturn(entityWithId);

            var resultDTO = serviceUnderTest.insert(request); //test


            ArgumentCaptor<OrderEntity> orderEntityArgumentCaptor = ArgumentCaptor.forClass(OrderEntity.class);
            verify(orderRepository).saveAndFlush(orderEntityArgumentCaptor.capture());
            OrderEntity capturedOrder = orderEntityArgumentCaptor.getValue();


            BigDecimal pancakePrice = ingredientPrice1.add(ingredientPrice2);
            BigDecimal discount = Utils.percentage(pancakePrice, new BigDecimal("10"));
            var expectedResult = new OrderDTO(1, capturedOrder.getOrderDate(), "cool description", capturedOrder.getOrderNumber(),
                    List.of(new OrderedPancakeResp(1, pancakePrice)),
                    pancakePrice, discount, pancakePrice.subtract(discount));

            assertThat(resultDTO).isEqualTo(expectedResult);
        }
    }

    @Test
    void insert_invalidRequest_throwPancakeNotFoundException()
    {
        int id1 = 1;
        var request = new OrderRequest("cool description", new HashSet<>(List.of(id1)));

        when(pancakeRepository.findById(id1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> serviceUnderTest.insert(request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void insert_invalidRequest_throwPancakeAlreadyOrderedException()
    {
        int id1 = 1;
        var request = new OrderRequest("cool description", new HashSet<>(List.of(id1)));

        PancakeEntity pancakeEntity = new PancakeEntity();
        pancakeEntity.setOrder(new OrderEntity());

        when(pancakeRepository.findById(id1)).thenReturn(Optional.of(pancakeEntity));
        assertThatThrownBy(() -> serviceUnderTest.insert(request))
                .isInstanceOf(PancakeAlreadyOrderedException.class);
    }

}