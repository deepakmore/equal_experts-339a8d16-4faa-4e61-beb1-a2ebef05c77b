package shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tax {
    String name;
    Double rate;
    //EligibleCategories categories;
}
