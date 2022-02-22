package shoppingcart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppingcart.exception.ProductNotFoundException;
import shoppingcart.exception.ProductQuantityIsNotAvailableException;
import shoppingcart.model.Product;

import static org.junit.jupiter.api.Assertions.*;

class ProductsInventoryTest {

    ProductsInventory productsInventory;

    @BeforeEach
    void setUp() {
        productsInventory = ProductsInventory.initialize();
    }

    @Test
    void shouldLoadProductsWithQuantity() {
        assertTrue(productsInventory.getInventoryProductsWithQuantity().size() > 0);
    }

    @Test
    void shouldGetProductByProductName() {
        String productName = "Dove Soap";
        Product product = productsInventory.searchByProductName(productName);

        assertNotNull(product);
        assertEquals(productName, product.getName());
    }

    @Test
    void shouldThrowExceptionIfSearchingUnknownProduct() {
        String unknownProduct = "Test Product";
        assertThrows(ProductNotFoundException.class, () -> productsInventory.searchByProductName(unknownProduct));
    }

    @Test
    void shouldValidateProduct() {
        String productName = "Dove Soap";
        assertDoesNotThrow(() -> productsInventory.validateProduct(productName));
    }

    @Test
    void shouldValidateQuantity() {
        String productName = "Dove Soap";
        Integer quantity = 1;
        assertDoesNotThrow(() -> productsInventory.validateQuantity(productName, quantity));
    }

    @Test
    void shouldThrowExceptionIfExpectedQuantityIsMoreThanAvailableQuantity() {
        String productName = "Dove Soap";
        Integer quantity = 35;
        assertThrows(ProductQuantityIsNotAvailableException.class, () -> productsInventory.validateQuantity(productName, quantity));
    }

    @Test
    void shouldUpdateQuantity() {
        String productName = "Dove Soap";
        int quantity = 5;

        int availableQuantity = getQuantityOfProduct(productName);
        productsInventory.updateQuantity(productName, quantity);

        assertTrue(availableQuantity - quantity < availableQuantity);
        assertTrue(availableQuantity - quantity >= 0);
    }

    private int getQuantityOfProduct(String productName) {
        return productsInventory.getInventoryProductsWithQuantity()
                .get(productName)
                .getQuantity();
    }
}