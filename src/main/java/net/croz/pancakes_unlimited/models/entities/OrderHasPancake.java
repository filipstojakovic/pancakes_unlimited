package net.croz.pancakes_unlimited.models.entities;

import lombok.*;
import net.croz.pancakes_unlimited.models.entities.compositekeys.OrderPancakeKey;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "order_has_pancake")
@IdClass(OrderPancakeKey.class)
public class OrderHasPancake
{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pancake_id")
    private PancakeEntity pancake;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_id")
    private OrderEntity order;

    @Column(name = "quantity")
    private Integer quantity;
}
