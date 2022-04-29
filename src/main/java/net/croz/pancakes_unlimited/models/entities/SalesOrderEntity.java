package net.croz.pancakes_unlimited.models.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "sales_order")
public class SalesOrderEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;
    @Column(name = "description")
    private String description;
    @Column(name="order_number", length = 36) // UUID.randomUUID() is of length 36
    private String orderNumber;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL)
    List<OrderHasPancake> orderHasPancakes = new ArrayList<>();
}
