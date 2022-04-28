package net.croz.pancakes_unlimited.models.requests;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SalesOrderRequest
{
    @NotNull
    private String description;
    @Valid
    private List<PancakeRequest> orderedPancakes;

}
