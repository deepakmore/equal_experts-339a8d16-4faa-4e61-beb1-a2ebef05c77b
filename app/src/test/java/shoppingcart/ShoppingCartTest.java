package shoppingcart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppingcart.exception.ProductExpectedQuantityZeroException;
import shoppingcart.exception.ProductNotFoundException;
import shoppingcart.exception.ProductQuantityIsNotAvailableException;
import shoppingcart.model.Product;
import shoppingcart.model.ProductWithQuantity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void shouldInitializeInventory() {
        assertNotNull(cart.getProductsInventory());
        assertTrue(cart.getProductsInventory().getInventoryProductsWithQuantity().size() > 0);
    }

    @Test
    void shouldAddNewProductInCart() {
        cart.addProduct("Dove Soap", 1);
        assertEquals(1, cart.getCartItems().size());
    }

    @Test
    void shouldNotAddNewProductInCartIfQuantityIsZero() {
        assertThrows(ProductExpectedQuantityZeroException.class, () -> cart.addProduct("Dove Soap", 0));
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    void shouldNotAddNewProductInCartIfQuantityIsNegativeNumber() {
        assertThrows(ProductExpectedQuantityZeroException.class, () -> cart.addProduct("Dove Soap", -1));
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    void shouldNotAddUnknownProductInCart() {
        String unsupportedProductName = "Test Product";
        assertThrows(ProductNotFoundException.class, () ->  cart.addProduct(unsupportedProductName, 1));
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    void shouldAddOnlyOneProductIfUserAddsSameProductMultipleTimes() {
        cart.addProduct("Dove Soap", 5);
        cart.addProduct("Dove Soap", 3);
        assertEquals(1, cart.getCartItems().size());
        assertEquals(8, cart.getCartItems().stream().findFirst().get().getQuantity());
    }

    @Test
    void shouldAddTwoProductsIfUserAddsDifferentProduct() {
        cart.addProduct("Dove Soap", 1);
        cart.addProduct("Axe Deo", 1);
        assertEquals(2, cart.getCartItems().size());
    }

    @Test
    void shouldNotAddProductInCartWhichHasGreaterQuantityThanAvailability() {
        String productName = "Dove Soap";
        assertThrows(ProductQuantityIsNotAvailableException.class, () ->  cart.addProduct(productName, 30));
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    void shouldComputeTotalPriceForSingleProduct() {
        String productName = "Dove Soap";
        cart.addProduct(productName, 1);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(cart.totalPrice(), BigDecimal.valueOf(searchProduct(productName).get().getPrice()));
    }

    @Test
    void shouldComputeTotalPriceForSingleProductAddedForMultipleTimes() {
        cart.addProduct("Dove Soap", 5);
        cart.addProduct("Dove Soap", 3);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(BigDecimal.valueOf(319.92), cart.totalPrice());
    }

    @Test
    void shouldComputeTotalPriceForMultipleProducts() {
        cart.addProduct("Dove Soap", 2);
        cart.addProduct("Axe Deo", 2);

        assertEquals(2, cart.getCartItems().size());
        assertEquals(BigDecimal.valueOf(314.96), cart.totalPrice());
    }

    private Optional<Product> searchProduct(String productName) {
        return cart.getCartItems().stream()
                .map(ProductWithQuantity::getProduct)
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .findFirst();
    }
}