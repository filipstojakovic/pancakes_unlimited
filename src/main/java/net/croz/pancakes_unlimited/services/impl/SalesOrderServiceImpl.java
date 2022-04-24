package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.entities.SalesOrderEntity;
import net.croz.pancakes_unlimited.repositories.SalesOrderEntityRepository;
import net.croz.pancakes_unlimited.services.SalesOrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService
{
    private final SalesOrderEntityRepository salesOrderRepository;

    public SalesOrderServiceImpl(SalesOrderEntityRepository salesOrderRepository)
    {
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
    public SalesOrderEntity insert(SalesOrderEntity salesOrder)
    {
        return salesOrderRepository.saveAndFlush(salesOrder);
    }
}
