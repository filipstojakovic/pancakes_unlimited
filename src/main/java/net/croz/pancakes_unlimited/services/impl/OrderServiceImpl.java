package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.exceptions.PancakeAlreadyOrderedException;
import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.entities.OrderEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;
import net.croz.pancakes_unlimited.repositories.OrderEntityRepository;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.OrderService;
import net.croz.pancakes_unlimited.utils.MapUtils;
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
        return orderRepository.findAll().stream()
                .map(x -> MapUtils.mapToOrderDTO(x, modelMapper))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findById(Integer id)
    {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        return MapUtils.mapToOrderDTO(orderEntity, modelMapper);
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

        return MapUtils.mapToOrderDTO(newOrderEntity, modelMapper);
    }

    private List<PancakeEntity> getRequestedPancakeEntityList(OrderRequest orderRequest, OrderEntity newOrderEntity)
    {
        return orderRequest.getPancakeIds()
                .stream()
                .map(pancakeId ->
                    {
                        PancakeEntity pancake = pancakeRepository.findById(pancakeId).orElseThrow(NotFoundException::new);
                        if (pancake.getOrder() != null)
                            throw new PancakeAlreadyOrderedException("Pancake id " + pancake.getId() + " already ordered");
                        pancake.setOrder(newOrderEntity);
                        return pancake;
                    })
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findByOrderNumber(String orderNumber)
    {
        OrderEntity orderEntity = orderRepository.findOrderEntityByOrderNumber(orderNumber)
                .orElseThrow(NotFoundException::new);
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

}
