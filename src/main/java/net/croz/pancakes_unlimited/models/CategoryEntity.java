package net.croz.pancakes_unlimited.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class CategoryEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String categoryId;
    @Basic
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "ingredientCategory")
    private List<IngredientEntity> ingredientsById;
}
