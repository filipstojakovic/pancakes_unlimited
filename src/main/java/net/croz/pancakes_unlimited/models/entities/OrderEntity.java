package net.croz.pancakes_unlimited.models.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "sales_order")
public class OrderEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "order_date", nullable = false)
    private Date orderDate;
    @Column(name = "description")
    private String description;
    @Column(name = "order_number", length = 36, columnDefinition = "char") // UUID.randomUUID() is of length 36
    private String orderNumber;

    @OneToMany(mappedBy = "order")
    private List<PancakeEntity> pancakeEntityList=new ArrayList<>();
}
