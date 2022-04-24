package net.croz.pancakes_unlimited.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sales_order")
public class SalesOrderEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "order_date", nullable = false)
    private Date orderDate;
    @Basic
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "pancakeOrder", cascade = CascadeType.ALL)
    private List<PancakeEntity> pancakesById;
}
