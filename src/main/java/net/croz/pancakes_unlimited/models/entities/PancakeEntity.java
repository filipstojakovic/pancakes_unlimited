package net.croz.pancakes_unlimited.models.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "pancake")
public class PancakeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "pancake", cascade = CascadeType.ALL)
    private List<PancakeHasIngredient> pancakeIngredients = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pancake", cascade = CascadeType.ALL)
    List<OrderHasPancake> orderHasPancakes = new ArrayList<>();
}
