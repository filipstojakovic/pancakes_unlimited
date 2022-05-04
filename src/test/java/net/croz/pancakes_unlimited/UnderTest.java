package net.croz.pancakes_unlimited;

import net.croz.pancakes_unlimited.utils.Utils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class UnderTest
{
    @Test
    public void testing()
    {
        BigDecimal total = new BigDecimal(100);
        BigDecimal perc = Utils.percentage(total,new BigDecimal(20));
        System.out.println(perc);
    }

}
