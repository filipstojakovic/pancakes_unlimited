package net.croz.pancakes_unlimited;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



public class UnderTest
{
    @Test
    public void generateOrderNumberTest()
    {
        var uuid = UUID.randomUUID();
        String number = uuid.toString();
        assertThat(UUID.randomUUID().toString().length()).isEqualTo(36);
    }

    @Test
    public void mergeStringWithSpecialCharactersTest()
    {
        List<String> ingredientsName = List.of("voce","test","voće","voče").stream() // distinct ingredients
                .distinct()
                .collect(Collectors.toList());

        assertThat(ingredientsName).isEqualTo(2);

    }


}
