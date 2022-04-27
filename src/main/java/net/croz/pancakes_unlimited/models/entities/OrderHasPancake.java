package net.croz.pancakes_unlimited.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.croz.pancakes_unlimited.models.entities.compositekeys.OrderPancakeKey;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_has_pancake")
public class OrderHasPancake
{
    @EmbeddedId
    private OrderPancakeKey id = new OrderPancakeKey();

    @JsonIgnore
    @ManyToOne
    @MapsId("pancakeId")
    @JoinColumn(name = "pancake_id")
    PancakeEntity pancake;

    @ManyToOne
    @MapsId("salesOrderId")
    @JoinColumn(name = "sales_order_id")
    SalesOrderEntity salesOrder;


    @Column(name = "quantity")
    private Integer quantity;
}
