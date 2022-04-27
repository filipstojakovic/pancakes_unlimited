package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.entities.SalesOrderEntity;
import net.croz.pancakes_unlimited.models.requests.SalesOrderRequest;

import java.util.List;

public interface SalesOrderService
{
    List<SalesOrderEntity> findAll();
    SalesOrderEntity findById(Integer id);
    SalesOrderEntity insert(SalesOrderRequest salesOrder);
}
