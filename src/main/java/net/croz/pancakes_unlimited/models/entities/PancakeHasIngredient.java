package net.croz.pancakes_unlimited.models.entities;

import lombok.*;
import net.croz.pancakes_unlimited.models.entities.compositekeys.PancakeIngredientKey;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

}
