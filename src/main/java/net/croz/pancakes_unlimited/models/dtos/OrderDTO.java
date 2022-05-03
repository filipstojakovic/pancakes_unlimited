package net.croz.pancakes_unlimited.models.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO
{
    private Integer id;
    private Date orderDate;
    private String description;
    private String orderNumber;
    List<Integer> pancakeIds;
}
