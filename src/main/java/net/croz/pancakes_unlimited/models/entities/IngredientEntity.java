package net.croz.pancakes_unlimited.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
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

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<PancakeHasIngredient> pancakeIngredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity ingredientCategory;
}
