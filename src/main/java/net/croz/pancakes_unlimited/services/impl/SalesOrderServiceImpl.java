package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.entities.SalesOrderEntity;
import net.croz.pancakes_unlimited.models.requests.SalesOrderRequest;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.repositories.SalesOrderEntityRepository;
import net.croz.pancakes_unlimited.services.SalesOrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService
{
    private final PancakeEntityRepository pancakeRepository;
    private final SalesOrderEntityRepository salesOrderRepository;

    public SalesOrderServiceImpl(PancakeEntityRepository pancakeRepository, SalesOrderEntityRepository salesOrderRepository)
    {
        this.pancakeRepository = pancakeRepository;
        this.salesOrderRepository = salesOrderRepository;
    }


    @Override
    public List<SalesOrderEntity> findAll()
    {
        return salesOrderRepository.findAll();
    }

    @Override
    public SalesOrderEntity findById(Integer id)
    {
        return salesOrderRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public SalesOrderEntity insert(SalesOrderRequest salesOrderRequest)
    {
        //TODO: check if pancake is valid
        //TODO: check discount

//        List<OrderHasPancake> orderedPancakesRequest = salesOrderRequest.getOrderedPancakes();
//        //TODO: validate pancakes existance
//
//        SalesOrderEntity salesOrderEntity = new SalesOrderEntity();
//        salesOrderEntity.setDescription(salesOrderRequest.getDescription());
//        salesOrderEntity.setOrderDate(LocalDate.now());
//        salesOrderEntity = salesOrderRepository.saveAndFlush(salesOrderEntity);
//
//        List<OrderHasPancake> orderedPancakes = new ArrayList<>();
//        for (OrderHasPancake orderedPancake : orderedPancakesRequest)
//        {
//            PancakeEntity pancake = pancakeRepository.getById(orderedPancake.getPancakeEntity().getId());
//            OrderHasPancake temp = new OrderHasPancake(salesOrderEntity, pancake, orderedPancake.getQuantity());
//            orderedPancakes.add(temp);
//        }
//
//        salesOrderEntity.setOrderHasPancakes(orderedPancakes);
//        return salesOrderEntity;
//        //        return salesOrderRepository.saveAndFlush(salesOrderRequest);
        return null;
    }
}
