package shoppingcart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {
    @Test
    void emptyCartTest() {
        var cart = new ShoppingCart();

        assertEquals(0.0d, cart.totalPrice());
    }
}
