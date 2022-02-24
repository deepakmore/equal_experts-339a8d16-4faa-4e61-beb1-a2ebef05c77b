package shoppingcart;

import com.google.common.collect.Sets;
import shoppingcart.exception.ExpectedQuantityIsZeroException;
import shoppingcart.model.Product;
import shoppingcart.model.ProductAndQuantity;
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

    private final Map<String, ProductAndQuantity> cart;
    private BigDecimal totalPrice;

    public ShoppingCart() {
        cart = new LinkedHashMap<>();
        totalPrice = ZERO;
    }

    public void addProduct(Product product, int expectedQuantity) {
        if(expectedQuantity > 0) {
            int totalQuantity = cart.containsKey(product.getName()) ? cart.get(product.getName()).getQuantity() + expectedQuantity : expectedQuantity;
            cart.put(product.getName(), new ProductAndQuantity(product, totalQuantity));
            calculateTotalPrice(product, expectedQuantity);
            return;
        }

        throw new ExpectedQuantityIsZeroException();
    }

    public BigDecimal totalPrice() {
        if(cart.size() >= 1) {
            BigDecimal tax = applySalesTax(totalPrice);
            return NumberUtil.valueAfterRoundOff(totalPrice.add(tax), PRECISION_TO_ROUND_OFF);
        }
        return ZERO;
    }

    private void calculateTotalPrice(Product product, int expectedQuantity) {
        totalPrice = totalPrice.add(BigDecimal.valueOf(product.getPrice() * expectedQuantity));
    }

    protected BigDecimal applySalesTax(BigDecimal amountWithoutTax) {
        return (amountWithoutTax.multiply(SALES_TAX)).divide(HUNDRED, BigDecimal.ROUND_UP);
    }

    public Set<ProductAndQuantity> getCartItems() {
        return Collections.unmodifiableSet(Sets.newHashSet(cart.values()));
    }
}