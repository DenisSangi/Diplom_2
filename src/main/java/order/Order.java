package order;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

@Data
public class Order {

    private String[] ingredients;

    public Order(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public static Order correctIngredientOrder() {
        String[] ingredients = new String[]{"61c0c5a71d1f82001bdaaa6c"};

        return new Order(ingredients);
    }

    public static Order incorrectIngredientOrder() {
        String[] ingredients = new String[] {RandomStringUtils.randomAlphanumeric(24)};

        return new Order(ingredients);
    }

    public static Order emptyIngredientOrder() {
        String[] ingredients = new String[] {null};

        return new Order(ingredients);
    }
}
