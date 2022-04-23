package net.croz.pancakes_unlimited.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "INGREDIENT")
public class IngredientEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer ingredientId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    List<PancakeHasIngredient> pancakeIngredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private CategoryEntity ingredientCategory;
}
