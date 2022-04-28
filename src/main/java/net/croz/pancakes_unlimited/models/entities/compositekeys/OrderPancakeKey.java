package net.croz.pancakes_unlimited.models.entities.compositekeys;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class OrderPancakeKey implements Serializable
{
    @Column(name = "pancake_id")
    private Integer pancakeId;

    @Column(name = "sales_order_id")
    private Integer salesOrderId;
}