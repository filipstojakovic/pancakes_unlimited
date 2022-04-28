package net.croz.pancakes_unlimited.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PancakeRequest
{
    @NotNull
    private Integer pancakeId;
    @NotNull
    private Integer quantity;
}
