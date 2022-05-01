package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.models.entities.SalesOrderEntity;
import net.croz.pancakes_unlimited.models.requests.SalesOrderRequest;
import net.croz.pancakes_unlimited.services.SalesOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales-orders")
public class SalesOrderController
{
    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService)
    {
        this.salesOrderService = salesOrderService;
    }


    //TODO: create and findByOrderNumber only requested
    @GetMapping
    public List<SalesOrderEntity> findAll()
    {
        return salesOrderService.findAll();
    }

    @PostMapping
    public SalesOrderEntity insert(@RequestBody SalesOrderRequest request)
    {
        return salesOrderService.insert(request);
    }
}
