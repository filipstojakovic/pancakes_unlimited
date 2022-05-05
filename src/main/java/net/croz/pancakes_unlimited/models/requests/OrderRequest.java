package net.croz.pancakes_unlimited.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest
{
    private String description;

    @NotNull
    @NotEmpty
    private Set<Integer> pancakeIds;
}
