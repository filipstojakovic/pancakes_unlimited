package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;
import net.croz.pancakes_unlimited.services.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    private final OrderService orderService;

    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }


    //TODO: create and find and findByOrderNumber only requested
    @GetMapping
    public List<OrderDTO> findAll()
    {
        return orderService.findAll();
    }

    @PostMapping
    public OrderDTO insert(@Valid @RequestBody OrderRequest request)
    {
        return orderService.insert(request);
    }
}
