package net.croz.pancakes_unlimited.models.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.croz.pancakes_unlimited.models.entities.compositekeys.OrderPancakeKey;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "order_has_pancake")
public class OrderHasPancake
{
    @EmbeddedId
    private OrderPancakeKey id = new OrderPancakeKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pancakeId")
    @JoinColumn(name = "pancake_id")
    private PancakeEntity pancake;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("salesOrderId")
    @JoinColumn(name = "sales_order_id")
    private SalesOrderEntity salesOrder;

    @Column(name = "quantity")
    private Integer quantity;
}
