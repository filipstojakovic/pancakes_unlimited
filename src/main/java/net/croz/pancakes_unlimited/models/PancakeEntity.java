package net.croz.pancakes_unlimited.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pancake")
public class PancakeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "sales_order_id", nullable = true)
    private SalesOrderEntity pancakeOrder;

    @OneToMany(mappedBy = "pancake", cascade = CascadeType.ALL)
    private List<PancakeHasIngredient> pancakeIngredients=new ArrayList<>();
}
