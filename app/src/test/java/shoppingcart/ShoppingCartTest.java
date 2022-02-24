package shoppingcart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppingcart.exception.ExpectedQuantityIsZeroException;
import shoppingcart.model.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static shoppingcart.constant.ShoppingCartConstants.ZERO;

public class ShoppingCartTest {

    ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    void shouldTestForEmptyCart() {
        assertEquals(cart.totalPrice(), ZERO);
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    void shouldAddNewProductInCartIfExpectedQuantityIsMoreThanZero() {
        Product product = Product.builder().name("Dove Soap").price(39.99).build();
        assertDoesNotThrow(() -> cart.addProduct(product, 1));
        assertEquals(1, cart.getCartItems().size());
    }

    @Test
    void shouldNotAddNewProductInCartIfQuantityIsZero() {
        Product product = Product.builder().name("Dove Soap").price(39.99).build();
        assertThrows(ExpectedQuantityIsZeroException.class, () -> cart.addProduct(product, 0));
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    void shouldNotAddNewProductInCartIfQuantityIsNegativeNumber() {
        Product product = Product.builder().name("Dove Soap").price(39.99).build();
        assertThrows(ExpectedQuantityIsZeroException.class, () -> cart.addProduct(product, -1));
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    void shouldAddOnlyOneProductIfUserAddsSameProductMultipleTimes() {
        Product product = Product.builder().name("Dove Soap").price(39.99).build();
        cart.addProduct(product, 5);
        cart.addProduct(product, 3);
        assertEquals(1, cart.getCartItems().size());
        assertEquals(8, cart.getCartItems().stream().findFirst().get().getQuantity());
    }

    @Test
    void shouldAddTwoProductsIfUserAddsDifferentProduct() {
        Product productOne = Product.builder().name("Dove Soap").price(39.99).build();
        Product productTwo = Product.builder().name("Axe Deo").price(99.99).build();
        cart.addProduct(productOne, 1);
        cart.addProduct(productTwo, 1);
        assertEquals(2, cart.getCartItems().size());
    }

    @Test
    void shouldComputeTotalPriceForSingleProduct() {
        Product product = Product.builder().name("Dove Soap").price(39.99).build();
        cart.addProduct(product, 1);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(BigDecimal.valueOf(44.99), cart.totalPrice());
    }

    @Test
    void shouldComputeTotalPriceForSingleProductAddedForMultipleTimes() {
        Product product = Product.builder().name("Dove Soap").price(39.99).build();
        cart.addProduct(product, 5);
        cart.addProduct(product, 3);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(BigDecimal.valueOf(359.91), cart.totalPrice());
    }

    @Test
    void shouldComputeTotalPriceForTwoProducts() {
        Product productOne = Product.builder().name("Dove Soap").price(39.99).build();
        Product productTwo = Product.builder().name("Axe Deo").price(99.99).build();

        cart.addProduct(productOne, 2);
        cart.addProduct(productTwo, 2);

        assertEquals(2, cart.getCartItems().size());
        assertEquals(BigDecimal.valueOf(314.96), cart.totalPrice());
    }

    @Test
    void shouldComputeTotalPriceForMultipleProducts() {
        Product productOne = Product.builder().name("Dove Soap").price(39.99).build();
        Product productTwo = Product.builder().name("Axe Deo").price(99.99).build();

        cart.addProduct(productOne, 2);
        cart.totalPrice();

        assertEquals(BigDecimal.valueOf(89.98), cart.totalPrice());

        cart.addProduct(productTwo, 2);
        cart.totalPrice();

        assertEquals(2, cart.getCartItems().size());
        assertEquals(BigDecimal.valueOf(314.96), cart.totalPrice());
    }
}