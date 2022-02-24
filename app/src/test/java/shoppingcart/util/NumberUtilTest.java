package shoppingcart.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberUtilTest {

    @Test
    void shouldGetNumberRoundedOffSinglePrecision() {
        assertEquals(BigDecimal.valueOf(124.0), NumberUtil.valueAfterRoundOff(BigDecimal.valueOf(123.999), 1));
    }

    @Test
    void shouldGetNumberRoundedOffTwoPrecision() {
        assertEquals(BigDecimal.valueOf(123.12), NumberUtil.valueAfterRoundOff(BigDecimal.valueOf(123.124), 2));
        assertEquals(BigDecimal.valueOf(123.13), NumberUtil.valueAfterRoundOff(BigDecimal.valueOf(123.125), 2));
        assertEquals(BigDecimal.valueOf(123.13), NumberUtil.valueAfterRoundOff(BigDecimal.valueOf(123.129), 2));
    }

    @Test
    void shouldGetNumberRoundedOffThreePrecision() {
        assertEquals(BigDecimal.valueOf(123.124), NumberUtil.valueAfterRoundOff(BigDecimal.valueOf(123.1242), 3));
        assertEquals(BigDecimal.valueOf(123.126), NumberUtil.valueAfterRoundOff(BigDecimal.valueOf(123.1256), 3));
        assertEquals(new BigDecimal("123.130"), NumberUtil.valueAfterRoundOff(BigDecimal.valueOf(123.1296), 3));
    }
}
