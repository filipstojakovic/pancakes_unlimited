package net.croz.pancakes_unlimited.models.requests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class SalesOrderRequest
{
    private String description;
    @Valid
    private List<PancakeRequest> orderedPancakes;

}
