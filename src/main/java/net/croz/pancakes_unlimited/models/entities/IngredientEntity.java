package net.croz.pancakes_unlimited.models.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@EqualsAndHashCode
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ingredient")
public class IngredientEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer ingredientId;

    @Column(name = "name")
    private String name;

    @Column(name = "is_healthy")
    private Boolean isHealthy;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity ingredientCategory;

    public IngredientEntity(Integer ingredientId, String name, Boolean isHealthy, BigDecimal price, CategoryEntity ingredientCategory)
    {
        this.ingredientId = ingredientId;
        this.name = name;
        this.isHealthy = isHealthy;
        this.price = price;
        this.ingredientCategory = ingredientCategory;
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
}
