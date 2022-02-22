package shoppingcart.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productName) {
        super("Invalid Product - " + productName);
    }
}
