package net.croz.pancakes_unlimited.models.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PancakeQuantityRequest
{
    @NotNull
    private Integer pancakeId;

    @Min(1)
    private Integer quantity;
}
