package net.croz.pancakes_unlimited.models.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CategoryDTO
{
    private Integer categoryId;
    private String name;
}
