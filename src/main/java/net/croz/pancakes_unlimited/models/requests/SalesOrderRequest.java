package net.croz.pancakes_unlimited.models.requests;

import lombok.Data;
import net.croz.pancakes_unlimited.models.entities.OrderHasPancake;

import java.util.List;

@Data
public class SalesOrderRequest
{
    private String description;
    private List<PancakeRequest> orderedPancakes;

}
