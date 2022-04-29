package net.croz.pancakes_unlimited.models.requests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class SalesOrderRequest
{
    @NotNull
    private String description;
    @Valid
    private List<PancakeRequest> orderedPancakes;

}
