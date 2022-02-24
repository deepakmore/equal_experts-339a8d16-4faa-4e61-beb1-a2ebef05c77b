package shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
final public class ProductAndQuantity {
    @Getter final private Product product;
    private Integer quantity;
}
