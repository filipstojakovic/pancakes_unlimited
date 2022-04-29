package net.croz.pancakes_unlimited.models.entities.compositekeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderPancakeKey implements Serializable
{
    @Column(name = "pancake_id")
    private Integer pancakeId;

    @Column(name = "sales_order_id")
    private Integer salesOrderId;
}