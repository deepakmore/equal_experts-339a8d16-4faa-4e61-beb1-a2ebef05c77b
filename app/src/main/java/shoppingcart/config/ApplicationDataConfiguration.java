package shoppingcart.config;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import shoppingcart.model.Product;
import shoppingcart.model.ProductWithQuantity;
import shoppingcart.model.Tax;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class ApplicationDataConfiguration {

    public static Map<String, ProductWithQuantity> getMockProductsInventory() {
        Map<String, ProductWithQuantity> productsInventory = Maps.newHashMap();
        Product product = Product.builder().name("Dove Soap").price(39.99).build();
        ProductWithQuantity productQuantity = new ProductWithQuantity(product, 35);
        productsInventory.put(product.getName(), productQuantity);

        product = Product.builder().name("Axe Deo").price(99.99).build();
        productQuantity = new ProductWithQuantity(product, 35);
        productsInventory.put(product.getName(), productQuantity);
        return productsInventory;
    }

    public Set<Tax> getMockTaxes() {
        Set<Tax> eligibleTaxes = Sets.newHashSet();
        eligibleTaxes.add(new Tax("Sales Tax", 12.5));
        return eligibleTaxes;
    }
}
