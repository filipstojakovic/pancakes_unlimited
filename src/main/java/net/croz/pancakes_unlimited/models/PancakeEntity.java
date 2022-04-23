package net.croz.pancakes_unlimited.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pancake")
public class PancakeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @OneToMany(mappedBy = "pancake", cascade = CascadeType.ALL)
    List<PancakeHasIngredient> pancakeIngredients=new ArrayList<>();
}
