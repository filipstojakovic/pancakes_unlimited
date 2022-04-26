package net.croz.pancakes_unlimited.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "sales_order")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="orderPancakes")
public class SalesOrderEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;
    @Basic
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "pk.salesOrder")
    private List<OrderHasPancake> orderHasPancakes = new ArrayList<>();
}
