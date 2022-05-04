package net.croz.pancakes_unlimited.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils
{
    private static final int scale = 2;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public static BigDecimal percentage(BigDecimal base, BigDecimal percentage)
    {
        return base.multiply(percentage).divide(ONE_HUNDRED, scale, RoundingMode.HALF_UP);
    }

    public static double percentage(double base, double percentage)
    {
        return base * percentage / 100;
    }
}
