package net.croz.pancakes_unlimited.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UtilsTest
{
    @Test
    void percentage_BigDecimal1()
    {
        BigDecimal base = new BigDecimal(99);
        BigDecimal percentage = new BigDecimal(10);

        BigDecimal result = Utils.percentage(base,percentage);

        assertThat(result).isEqualTo(new BigDecimal("9.90"));
    }

    @Test
    void percentage_BigDecimal2()
    {
        BigDecimal base = new BigDecimal(4534);
        BigDecimal percentage = new BigDecimal(21);

        BigDecimal result = Utils.percentage(base,percentage);

        assertThat(result).isEqualTo(new BigDecimal("952.14"));
    }

    @Test
    void percentage_double1()
    {
        double base = 99.0;
        double percentage = 10.0;

        double result = Utils.percentage(base,percentage);

        assertThat(result).isEqualTo(9.90);
    }

    @Test
    void percentage_double2()
    {
        double base = 4534;
        double percentage = 21.0;

        double result = Utils.percentage(base,percentage);

        assertThat(result).isEqualTo(952.14);
    }
}