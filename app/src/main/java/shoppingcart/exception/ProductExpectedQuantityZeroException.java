package shoppingcart.exception;

public class ProductExpectedQuantityZeroException extends RuntimeException {
    public ProductExpectedQuantityZeroException() {
        super("Expected quantity should not be less or less than 0");
    }
}
