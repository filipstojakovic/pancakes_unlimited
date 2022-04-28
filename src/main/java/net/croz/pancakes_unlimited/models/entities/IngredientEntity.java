package net.croz.pancakes_unlimited.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
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

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    List<PancakeHasIngredient> pancakeIngredients = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private CategoryEntity ingredientCategory;
}
