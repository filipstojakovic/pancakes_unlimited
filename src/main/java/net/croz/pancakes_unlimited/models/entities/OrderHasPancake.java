package net.croz.pancakes_unlimited.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.croz.pancakes_unlimited.models.entities.compositekeys.OrderPancakeKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "order_has_pancake")
public class OrderHasPancake
{
    @EmbeddedId
    @JsonIgnore
    private OrderPancakeKey pk;

    @Column(name = "quantity")
    private Integer quantity;

    public PancakeEntity getPancakeEntity()
    {
        return this.pk.getPancake();
    }
}
