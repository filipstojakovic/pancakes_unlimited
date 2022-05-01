package net.croz.pancakes_unlimited.models.requests;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderRequest
{
    private String description;

    @Valid
    @NotEmpty
    private List<PancakeQuantityRequest> orderedPancakes;
}
