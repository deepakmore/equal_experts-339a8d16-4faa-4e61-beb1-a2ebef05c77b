package shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
final public class ProductWithQuantity {
    @Getter private Product product;
    private Integer quantity;
}
