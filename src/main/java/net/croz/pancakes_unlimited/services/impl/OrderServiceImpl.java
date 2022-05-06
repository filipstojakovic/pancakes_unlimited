package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.exceptions.PancakeAlreadyOrderedException;
import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import net.croz.pancakes_unlimited.models.entities.OrderEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeHasIngredient;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;
import net.croz.pancakes_unlimited.models.responses.OrderedPancakeResp;
import net.croz.pancakes_unlimited.repositories.OrderEntityRepository;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.OrderService;
import net.croz.pancakes_unlimited.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService
{
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final BigDecimal TWENTY = new BigDecimal(20);
    public static final BigDecimal FIVE = new BigDecimal(5);
    public static final BigDecimal FIFTY = new BigDecimal(50);
    public static final BigDecimal TEN = new BigDecimal(10);
    public static final Integer SEVENTY_FIVE = new Integer(75);
    public static final BigDecimal FIFTEEN = new BigDecimal(15);

    private final PancakeEntityRepository pancakeRepository;
    private final OrderEntityRepository orderRepository;

    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    public OrderServiceImpl(PancakeEntityRepository pancakeRepository, OrderEntityRepository orderRepository, ModelMapper modelMapper)
    {
        this.pancakeRepository = pancakeRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<OrderDTO> findAll()
    {
        return orderRepository.findAll().stream().map(order -> mapToOrderDTO(order, modelMapper)).collect(Collectors.toList());
    }

    @Override
    public OrderDTO findById(Integer id)
    {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(NotFoundException::new);

        return mapToOrderDTO(orderEntity, modelMapper);
    }

    @Override
    public OrderDTO insert(OrderRequest orderRequest)
    {
        //TODO: check discount

        OrderEntity newOrderEntity = new OrderEntity();

        String description = orderRequest.getDescription() == null ? "" : orderRequest.getDescription();
        newOrderEntity.setDescription(description);

        List<PancakeEntity> requestedPancakes = getRequestedPancakeEntityList(orderRequest, newOrderEntity);

        newOrderEntity.setPancakeEntityList(requestedPancakes);

        String generatedUUID = UUID.randomUUID().toString();
        newOrderEntity.setOrderNumber(generatedUUID);

        Date currentDate = new Date();
        newOrderEntity.setOrderDate(currentDate);

        newOrderEntity = orderRepository.saveAndFlush(newOrderEntity);
        entityManager.refresh(newOrderEntity);

        return mapToOrderDTO(newOrderEntity, modelMapper);
    }

    private OrderDTO mapToOrderDTO(OrderEntity orderEntity, ModelMapper modelMapper)
    {
        OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);
        calculatePrices(orderEntity, orderDTO);

        return orderDTO;
    }

    private void calculatePrices(OrderEntity order, OrderDTO orderDTO)
    {
        BigDecimal totalPrice = new BigDecimal(0);
        List<PancakeEntity> orderedPancakes = order.getPancakeEntityList();
        List<OrderedPancakeResp> orderedPancakeResps = new ArrayList<>();

        for (PancakeEntity pancake : orderedPancakes)
        {
            List<PancakeHasIngredient> pancakeHasIngredients = pancake.getPancakeIngredients();
            BigDecimal currentPancakePrice = pancakeHasIngredients.stream()
                    .map(PancakeHasIngredient::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            OrderedPancakeResp orderedPancakeResp = new OrderedPancakeResp(pancake.getId(), currentPancakePrice);
            orderedPancakeResps.add(orderedPancakeResp);
            totalPrice = totalPrice.add(currentPancakePrice);
        }

        orderDTO.setTotalPrice(totalPrice);
        orderDTO.setOrderedPancakes(orderedPancakeResps);

        BigDecimal discount = calculateDiscount(orderedPancakes, totalPrice);
        orderDTO.setDiscount(discount);

        BigDecimal totalPriceWithDiscount = totalPrice.subtract(discount);
        orderDTO.setTotalPriceWithDiscount(totalPriceWithDiscount);

    }

    private BigDecimal calculateDiscount(List<PancakeEntity> orderedPancakes, BigDecimal totalPrice)
    {
        BigDecimal discount = new BigDecimal(0);

        if (totalPrice.compareTo(TWENTY) <= 0) // totalPrice <=20, every pancake with more than 75% healthy ingredients has discount 15%
        {
            discount = calculateHealthyPancakeDiscount(orderedPancakes);

        } else if (totalPrice.compareTo(FIFTY) <= 0) // 20 < totalPrice <= 50, discount is 5%
        {
            discount = Utils.percentage(totalPrice, FIVE);

        } else // totalPrice > 50, discount is 10%
        {
            discount = Utils.percentage(totalPrice, TEN);
        }

        return discount;
    }

    private BigDecimal calculateHealthyPancakeDiscount(List<PancakeEntity> orderedPancakes)
    {
        BigDecimal discount = new BigDecimal(0);
        for (var orderedPancake : orderedPancakes)
        {
            List<PancakeHasIngredient> pancakeHasIngredients = orderedPancake.getPancakeIngredients();

            int numOfIngredients = pancakeHasIngredients.size();
            long healthyIngredientPercentage = Math.round(Utils.percentage(numOfIngredients, SEVENTY_FIVE));

            long numOfHealthyIngredients = pancakeHasIngredients.stream()
                    .map(PancakeHasIngredient::getIngredient)
                    .filter(IngredientEntity::getIsHealthy)
                    .count();

            if (numOfHealthyIngredients > healthyIngredientPercentage)
            {
                BigDecimal pancakePrice = pancakeHasIngredients.stream()
                        .map(PancakeHasIngredient::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal pancakeDiscount = Utils.percentage(pancakePrice, FIFTEEN);
                discount = discount.add(pancakeDiscount);
            }
        }
        return discount;
    }


    private List<PancakeEntity> getRequestedPancakeEntityList(OrderRequest orderRequest, OrderEntity newOrderEntity)
    {
        return orderRequest.getPancakeIds().stream().map(pancakeId ->
            {
                PancakeEntity pancake = pancakeRepository.findById(pancakeId).orElseThrow(NotFoundException::new);
                if (pancake.getOrder() != null)
                    throw new PancakeAlreadyOrderedException("Pancake id " + pancake.getId() + " already ordered");
                pancake.setOrder(newOrderEntity);
                return pancake;
            }).collect(Collectors.toList());
    }

    @Override
    public OrderDTO findByOrderNumber(String orderNumber)
    {
        OrderEntity orderEntity = orderRepository.findOrderEntityByOrderNumber(orderNumber).orElseThrow(NotFoundException::new);
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

}
