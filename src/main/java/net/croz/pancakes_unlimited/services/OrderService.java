package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;

import java.util.List;

public interface OrderService
{
    List<OrderDTO> findAll();
    OrderDTO findById(Integer id);
    OrderDTO insert(OrderRequest orderRequest);
}
