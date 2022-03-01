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

import static shoppingcart.constant.ShoppingCartConstants.*;

public class ShoppingCart {

    private final Map<String, ProductAndQuantity> cart;

    public ShoppingCart() {
        cart = new LinkedHashMap<>();
    }

    public void addProduct(Product product, int expectedQuantity) {
        if(expectedQuantity > 0) {
            int totalQuantity = cart.containsKey(product.getName()) ? cart.get(product.getName()).getQuantity() + expectedQuantity : expectedQuantity;
            cart.put(product.getName(), new ProductAndQuantity(product, totalQuantity));
            return;
        }

        throw new ExpectedQuantityIsZeroException();
    }

    public BigDecimal totalPrice() {
            BigDecimal totalPrice = calculateTotalPrice();
            BigDecimal tax = applySalesTax(totalPrice);
            return NumberUtil.valueAfterRoundOff(totalPrice.add(tax), PRECISION_TO_ROUND_OFF);
    }

    private BigDecimal calculateTotalPrice() {
        return NumberUtil.valueAfterRoundOff(
                BigDecimal.valueOf(cart.values().stream()
                .mapToDouble( v -> v.getProduct().getPrice() * v.getQuantity())
                .sum()), PRECISION_TO_ROUND_OFF);
    }

    protected BigDecimal applySalesTax(BigDecimal amountWithoutTax) {
        return NumberUtil.valueAfterRoundOff((amountWithoutTax.multiply(SALES_TAX)).divide(HUNDRED, BigDecimal.ROUND_UP), PRECISION_TO_ROUND_OFF);
    }

    public Set<ProductAndQuantity> getCartItems() {
        return Collections.unmodifiableSet(Sets.newHashSet(cart.values()));
    }
}