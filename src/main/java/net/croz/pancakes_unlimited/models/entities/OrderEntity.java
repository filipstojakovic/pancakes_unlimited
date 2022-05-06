package net.croz.pancakes_unlimited.models.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<PancakeEntity> pancakeEntityList = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;


    @Override
    public String toString()
    {
        return "OrderEntity{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", description='" + description + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
