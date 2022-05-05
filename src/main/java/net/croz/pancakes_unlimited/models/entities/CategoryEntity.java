package net.croz.pancakes_unlimited.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer categoryId;
    @Basic
    @Column(name = "name", unique = true)
    private String name;

//    @JsonIgnore
//    @OneToMany(mappedBy = "ingredientCategory")
//    private List<IngredientEntity> ingredientsById;
}
