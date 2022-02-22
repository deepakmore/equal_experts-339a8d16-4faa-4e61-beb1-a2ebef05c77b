package shoppingcart.util;

import java.math.BigDecimal;

public class NumberUtil {

    public static BigDecimal valueAfterRoundOff(BigDecimal value, int precision) {
        return value.setScale(precision, BigDecimal.ROUND_HALF_UP);
    }
}
