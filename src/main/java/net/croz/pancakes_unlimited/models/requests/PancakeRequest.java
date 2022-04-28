package net.croz.pancakes_unlimited.models.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PancakeRequest
{
    @NotEmpty
    List<String> ingredientNames;
}
