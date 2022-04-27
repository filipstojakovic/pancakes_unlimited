package net.croz.pancakes_unlimited.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PancakeRequest
{
    private Integer pancakeId;
    private Integer quantity;
}
