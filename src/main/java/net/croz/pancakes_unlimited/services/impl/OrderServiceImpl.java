package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.entities.OrderEntity;
import net.croz.pancakes_unlimited.models.entities.OrderHasPancake;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;
import net.croz.pancakes_unlimited.repositories.OrderEntityRepository;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService
{
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
        return orderRepository.findAll().stream().map(this::mapToOrderDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO findById(Integer id)
    {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        return mapToOrderDTO(orderEntity);
    }

    @Override
    public OrderDTO insert(OrderRequest orderRequest)
    {
        //TODO: check if pancake is valid
        //TODO: check discount

        String desctiption = orderRequest.getDescription() == null ? "" : orderRequest.getDescription();
        OrderEntity newOrderEntity = new OrderEntity();
        newOrderEntity.setDescription(desctiption);

        OrderEntity finalNewOrderEntity = newOrderEntity;
        List<OrderHasPancake> orderedPancakes = orderRequest.getOrderedPancakes().stream()
                .map(pancakeQuantityReq ->
                    {
                        int pancakeId = pancakeQuantityReq.getPancakeId();
                        PancakeEntity pancake = pancakeRepository.findById(pancakeId).orElseThrow(NotFoundException::new);
                        int quantity = pancakeQuantityReq.getQuantity();

                        return new OrderHasPancake(pancake, finalNewOrderEntity, quantity);

                    }).collect(Collectors.toList());

        newOrderEntity.setOrderHasPancakes(orderedPancakes);
        Date currentDate = new Date();
        newOrderEntity.setOrderDate(currentDate);

        String generatedUUID = UUID.randomUUID().toString();
        newOrderEntity.setOrderNumber(generatedUUID);

        newOrderEntity = orderRepository.saveAndFlush(newOrderEntity);
        entityManager.refresh(newOrderEntity);

        return modelMapper.map(newOrderEntity, OrderDTO.class);
    }

    private OrderDTO mapToOrderDTO(OrderEntity orderEntity)
    {
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    @Override
    public OrderDTO findByOrderNumber(String orderNumber)
    {
        OrderEntity orderEntity = orderRepository.findOrderEntityByOrderNumber(orderNumber)
                .orElseThrow(NotFoundException::new);
        return mapToOrderDTO(orderEntity);
    }
}
