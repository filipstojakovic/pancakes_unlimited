package net.croz.pancakes_unlimited.models.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class OrderRequest
{
    private String description;

    @NotNull
    @NotEmpty
    private Set<Integer> pancakeIds;
}
