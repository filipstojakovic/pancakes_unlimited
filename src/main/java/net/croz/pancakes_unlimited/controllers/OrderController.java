package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.controllers.crudinterfaces.IFindController;
import net.croz.pancakes_unlimited.controllers.crudinterfaces.IInsertController;
import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;
import net.croz.pancakes_unlimited.services.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController implements IFindController<Integer, OrderDTO>, IInsertController<OrderRequest, OrderDTO>
{
    private final OrderService orderService;

    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @Override
    public List<OrderDTO> findAll()
    {
        return orderService.findAll();
    }

    @Override
    public OrderDTO findById(Integer id)
    {
        return orderService.findById(id);
    }

    @GetMapping(params = "orderNumber")
    public OrderDTO findByOrderNumber(@RequestParam String orderNumber)
    {
        return orderService.findByOrderNumber(orderNumber);
    }

    @Override
    public OrderDTO insert(OrderRequest request)
    {
        return orderService.insert(request);
    }
}
