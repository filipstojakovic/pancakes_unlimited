package net.croz.pancakes_unlimited.models.entities;

import lombok.*;

import javax.persistence.*;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@EqualsAndHashCode
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

}
