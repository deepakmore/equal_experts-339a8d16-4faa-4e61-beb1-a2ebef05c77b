package shoppingcart;

import com.google.common.collect.Sets;
import lombok.Getter;
import shoppingcart.exception.ProductExpectedQuantityZeroException;
import shoppingcart.model.ProductWithQuantity;
import shoppingcart.service.ProductsInventory;
import shoppingcart.util.NumberUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static shoppingcart.constant.ShoppingCartConstants.HUNDRED;
import static shoppingcart.constant.ShoppingCartConstants.PRECISION_TO_ROUND_OFF;
import static shoppingcart.constant.ShoppingCartConstants.SALES_TAX;
import static shoppingcart.constant.ShoppingCartConstants.ZERO;

public class ShoppingCart {

    private final Map<String, ProductWithQuantity> cart;
    @Getter private final ProductsInventory productsInventory;

    public ShoppingCart() {
        cart = new LinkedHashMap<>();
        productsInventory = ProductsInventory.initialize();
    }

    public void addProduct(String productName, int expectedQuantity) {
        if(expectedQuantity > 0) {
            validateProductAndAvailabilityOfQuantity(productName, expectedQuantity);
            int totalQuantity = cart.containsKey(productName) ? cart.get(productName).getQuantity() + expectedQuantity : expectedQuantity;
            cart.put(productName, new ProductWithQuantity(productsInventory.searchByProductName(productName), totalQuantity));
            productsInventory.updateQuantity(productName, expectedQuantity);
            return;
        }

        throw new ProductExpectedQuantityZeroException();
    }

    protected void validateProductAndAvailabilityOfQuantity(String productName, int expectedQuantity) {
        productsInventory.validateProduct(productName);
        productsInventory.validateQuantity(productName, expectedQuantity);
    }

    public BigDecimal totalPrice() {
        if(cart.isEmpty()) return ZERO;
        BigDecimal amountWithoutTax = NumberUtil.valueAfterRoundOff(
                BigDecimal.valueOf(
                        cart.values().stream()
                                .mapToDouble(a -> a.getProduct().getPrice() * a.getQuantity())
                                .sum()), PRECISION_TO_ROUND_OFF);

        if (cart.size() == 1) return amountWithoutTax;

        BigDecimal tax = applySalesTax(amountWithoutTax);
        return NumberUtil.valueAfterRoundOff(amountWithoutTax.add(tax), PRECISION_TO_ROUND_OFF);
    }

    private BigDecimal applySalesTax(BigDecimal amountWithoutTax) {
        return (amountWithoutTax.multiply(SALES_TAX)).divide(HUNDRED, BigDecimal.ROUND_UP);
    }

    public Set<ProductWithQuantity> getCartItems() {
        return Collections.unmodifiableSet(Sets.newHashSet(cart.values()));
    }
}