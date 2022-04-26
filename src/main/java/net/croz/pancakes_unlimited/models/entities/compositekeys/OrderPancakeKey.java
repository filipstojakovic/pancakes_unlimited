package net.croz.pancakes_unlimited.models.entities.compositekeys;

import lombok.Data;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;
import net.croz.pancakes_unlimited.models.entities.SalesOrderEntity;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class OrderPancakeKey implements Serializable
{
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_id")
    private SalesOrderEntity salesOrder;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pancake_id")
    private PancakeEntity pancake;
}