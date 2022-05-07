package net.croz.pancakes_unlimited.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@EqualsAndHashCode
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pancake")
public class PancakeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "pancake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PancakeHasIngredient> pancakeIngredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sales_order_id", nullable = true)
    private OrderEntity order;

    public PancakeEntity(Integer id, List<PancakeHasIngredient> pancakeIngredients, OrderEntity order)
    {
        this.id = id;
        this.pancakeIngredients = pancakeIngredients;
        this.order = order;
    }

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;
    @LastModifiedDate
    @Column(name = "updated_at", updatable = true)
    private Date modifiedAt;
    @LastModifiedBy
    @Column(name = "updated_by", updatable = true)
    private String updatedBy;


    @Override
    public String toString()
    {
        return "PancakeEntity{" +
                "id=" + id +
                ", order=" + order +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedAt=" + modifiedAt +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
