package shoppingcart.exception;

public class ProductQuantityIsNotAvailableException extends RuntimeException {

    public ProductQuantityIsNotAvailableException(Integer availableQuantity, Integer expectedQuantity) {
        super("Invalid Quantity =  Available Quantity ["+ availableQuantity+"] - Expected Quantity ["+ expectedQuantity+"]");
    }
}
