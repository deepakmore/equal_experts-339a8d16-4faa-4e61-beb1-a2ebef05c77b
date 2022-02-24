package shoppingcart.exception;

public class ExpectedQuantityIsZeroException extends RuntimeException {
    public ExpectedQuantityIsZeroException() {
        super("Expected quantity should be more than 0");
    }
}
