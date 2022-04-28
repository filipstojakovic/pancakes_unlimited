package net.croz.pancakes_unlimited.models.entities;

import lombok.Data;
import net.croz.pancakes_unlimited.models.entities.compositekeys.OrderPancakeKey;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_has_pancake")
public class OrderHasPancake
{
    @EmbeddedId
    private OrderPancakeKey id = new OrderPancakeKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pancakeId")
    @JoinColumn(name = "pancake_id")
    PancakeEntity pancake;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("salesOrderId")
    @JoinColumn(name = "sales_order_id")
    SalesOrderEntity salesOrder;

    @Column(name = "quantity")
    private Integer quantity;
}
