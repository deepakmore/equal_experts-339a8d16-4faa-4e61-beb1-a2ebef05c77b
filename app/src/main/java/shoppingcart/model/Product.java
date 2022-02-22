package shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Product {
    @Getter final private String name;
    private Double price;
    private Map<String, String> properties; // color, publishedOn / LaunchedYear, limitToPurchase
}
