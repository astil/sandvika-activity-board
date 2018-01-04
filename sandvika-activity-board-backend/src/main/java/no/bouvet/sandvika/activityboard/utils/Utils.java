package no.bouvet.sandvika.activityboard.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils
{
    public static double scaledDouble(double aDouble, int scale)
    {
        try
        {
            return BigDecimal.valueOf(aDouble).setScale(scale, RoundingMode.HALF_UP).doubleValue();
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public static double scaledDouble(double aDouble)
    {
        return scaledDouble(aDouble, 2);
    }
}
