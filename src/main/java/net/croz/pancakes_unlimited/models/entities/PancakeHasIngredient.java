package net.croz.pancakes_unlimited.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.croz.pancakes_unlimited.models.entities.compositekeys.PancakeIngredientKey;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@IdClass(PancakeIngredientKey.class)
@Table(name = "pancake_has_ingredient")
public class PancakeHasIngredient
{
    @JsonIgnore
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pancake_id")
    private PancakeEntity pancake;

    @JsonIgnore
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private IngredientEntity ingredient;

    @Column(name = "price")
    private BigDecimal price;
}
