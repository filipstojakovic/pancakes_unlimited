package net.croz.pancakes_unlimited.models.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class PancakeRequest
{
    @NotEmpty
    Set<Integer> ingredientsId;
}
