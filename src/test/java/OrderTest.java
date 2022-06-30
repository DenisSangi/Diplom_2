import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import order.Order;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.junit.Assert.*;

public class OrderTest {

    private OrderClient orderClient;
    private Order correctOrder = Order.correctIngredientOrder();
    private Order incorrectOrder = Order.incorrectIngredientOrder();
    private Order emptyOrder = Order.emptyIngredientOrder();
    private UserClient userClient = new UserClient();
    private final User user = User.getRandom();

    @Before
    public void setup() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("New correct order creation with authorization positive test")
    @Description("Base test of \"/order\" endpoint. Checking code status and response's body after correct request were send")
    public void createOrderWithAuthorizationAndCheckResponse() {

        String accessToken = userClient.createUser(user);
        boolean isCreated = orderClient.createOrderWithAuthorization(correctOrder, accessToken);
        assertTrue(isCreated);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("New correct order creation without authorization positive test")
    @Description("Base test of \"/order\" endpoint. Checking code status and response's body after correct request were send")
    public void createOrderWithoutAuthorizationAndCheckResponse() {

        boolean isCreated = orderClient.createOrderWithouAuthorization(correctOrder);
        assertTrue(isCreated);
    }

    @Test
    @DisplayName("New incorrect order creation negative test")
    @Description("Base test of \"/order\" endpoint. Checking code status and response's body after correct request were send")
    public void createIncorrectOrderAndCheckResponse() {

        int result = orderClient.createIncorrectOrder(incorrectOrder);
        assertEquals(result, 500);
    }

    @Test
    @DisplayName("New empty order creation negative test")
    @Description("Base test of \"/order\" endpoint. Checking code status and response's body after correct request were send")
    public void createEmptyOrderAndCheckResponse() {

        boolean isCreated = orderClient.createEmptyOrder(emptyOrder);
        assertFalse(isCreated);
    }

    @Test
    @DisplayName("New get User's order with authorization positive test")
    @Description("Base test of \"/order\" endpoint. Checking code status and response's body after correct request were send")
    public void getUserOrderWithAuthorizationAndCheckResponse() {

        String accessToken = userClient.createUser(user);
        boolean isCreated = orderClient.createOrderWithAuthorization(correctOrder, accessToken);
        assertTrue(isCreated);
        boolean result = orderClient.getUserOrderWithAuthorization(correctOrder, accessToken);
        assertTrue(result);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("New get User's order without authorization negative test")
    @Description("Base test of \"/order\" endpoint. Checking code status and response's body after correct request were send")
    public void getUserOrderWithoutAuthorizationAndCheckResponse() {

        boolean result = orderClient.getUserOrderWithoutAuthorization(correctOrder);
        assertFalse(result);
    }
}