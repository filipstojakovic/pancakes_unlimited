package net.croz.pancakes_unlimited.models.entities.compositekeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.croz.pancakes_unlimited.models.entities.OrderEntity;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class OrderPancakeKey implements Serializable
{
    private PancakeEntity pancake;
    private OrderEntity order;
}