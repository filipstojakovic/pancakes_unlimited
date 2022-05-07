package net.croz.pancakes_unlimited.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PancakeRequest
{
    @NotEmpty
    @Size(min = 2) // 1 base + 1 fil
    Set<Integer> ingredientsId;
}
