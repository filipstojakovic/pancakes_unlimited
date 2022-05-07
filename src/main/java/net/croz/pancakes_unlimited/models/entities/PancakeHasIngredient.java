package net.croz.pancakes_unlimited.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.croz.pancakes_unlimited.models.entities.compositekeys.PancakeIngredientKey;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PancakeIngredientKey.class)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pancake_has_ingredient")
public class PancakeHasIngredient
{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pancake_id")
    private PancakeEntity pancake;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private IngredientEntity ingredient;

    @Column(name = "price")
    private BigDecimal price;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PancakeHasIngredient that = (PancakeHasIngredient) o;

        if (pancake.getId() != null &&
                !pancake.getId().equals(that.pancake.getId()))
            return false;
        if (ingredient.getIngredientId() != null &&
                !ingredient.getIngredientId().equals(that.ingredient.getIngredientId()))
            return false;
        return price.equals(that.price);
    }

    @Override
    public int hashCode()
    {
        int result = pancake.hashCode();
        result = 31 * result + ingredient.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
