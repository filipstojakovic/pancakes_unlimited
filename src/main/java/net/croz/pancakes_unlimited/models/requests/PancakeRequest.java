package net.croz.pancakes_unlimited.models.requests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class PancakeRequest
{
    @NotEmpty
    List<String> ingredientNames;
}
