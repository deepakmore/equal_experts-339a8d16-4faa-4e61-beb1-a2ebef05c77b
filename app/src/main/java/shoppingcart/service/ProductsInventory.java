package shoppingcart.service;

import com.google.common.collect.Maps;
import shoppingcart.config.ApplicationDataConfiguration;
import shoppingcart.exception.ProductNotFoundException;
import shoppingcart.exception.ProductQuantityIsNotAvailableException;
import shoppingcart.model.Product;
import shoppingcart.model.ProductWithQuantity;

import java.util.Collections;
import java.util.Map;

final public class ProductsInventory {

    private final Map<String, ProductWithQuantity> products;
    private static ProductsInventory inventory;

    private ProductsInventory() {
        this.products = initializeInventory();
    }

    public static ProductsInventory initialize() {
        if(inventory == null)
            inventory = new ProductsInventory();
        return inventory;
    }

    private Map<String, ProductWithQuantity> initializeInventory() {
        return ApplicationDataConfiguration.getMockProductsInventory();
    }

    public Map<String, ProductWithQuantity> getInventoryProductsWithQuantity() {
        return Collections.unmodifiableMap(Maps.newHashMap(products));
    }

    public Product searchByProductName(String productName) {
        if(products.containsKey(productName)) {
            return products.get(productName).getProduct();
        }
        throw new ProductNotFoundException(productName);
    }

    public void validateProduct(String productName) {
        searchByProductName(productName);
    }

    public void validateQuantity(String productName, Integer expectedQuantity) {
        int availableQuantity = products.get(productName).getQuantity();
        if(availableQuantity < expectedQuantity) {
            throw new ProductQuantityIsNotAvailableException(availableQuantity, expectedQuantity);
        }
    }

    public void updateQuantity(String productName, int expectedQuantity) {
        ProductWithQuantity productWithQuantity = products.get(productName);
        productWithQuantity.setQuantity(productWithQuantity.getQuantity() - expectedQuantity);
        products.put(productName, productWithQuantity);
    }
}