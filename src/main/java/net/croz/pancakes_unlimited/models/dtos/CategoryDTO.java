package net.croz.pancakes_unlimited.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO
{
    private Integer categoryId;
    private String name;
}
