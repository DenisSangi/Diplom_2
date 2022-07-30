import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserCredentials;

import static org.junit.Assert.*;

public class UserTest {

    private final User user = User.getRandom();
    private final User userWithoutEmail = User.noEmail();
    private final User userWithoutPassword = User.noPassword();
    private final User userWithoutName = User.noName();
    private final User incorrectEmailUser = User.wrongEmail();
    private final User getIncorrectPasswordUser = User.wrongPassword();


    private UserClient userClient;
    String accessToken;

    @Before
    public void setup(){
        userClient = new UserClient();
    }

    @After
    public void clear() {
        if (accessToken != null) {
            boolean isDeleted = userClient.deleteUser(accessToken);
            assertTrue(isDeleted);
            System.out.println("User deleted");
        } else {
            System.out.println("Nothing to delete");
        }
    }

    @Test
    @DisplayName("New User creation test")
    @Description("Base test of \"/register\" endpoint. Checking code status and response's body after correct request were send")
    public void createUserAndCheckResponse(){
        this.accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
    }

    @Test
    @DisplayName("Existed User creation test")
    @Description("Base negative test of \"/register\" endpoint. Checking code status and response's body after correct request were send")
    public void createExistedUserAndCheckResponse(){
        this.accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        UserCredentials creds = UserCredentials.from(user);
        boolean result = userClient.createExistedUser(creds);
        assertFalse(result);
    }

    @Test
    @DisplayName("No email User creation test")
    @Description("Base negative test of \"/register\" endpoint. Checking code status and response's body after correct request were send")
    public void createUserWithoutEmailAndCheckResponse(){
        boolean result = userClient.createUserWithoutField(userWithoutEmail);
        assertFalse(result);
    }

    @Test
    @DisplayName("No password User creation test")
    @Description("Base negative test of \"/register\" endpoint. Checking code status and response's body after correct request were send")
    public void createUserWithoutPasswordAndCheckResponse(){
        boolean result = userClient.createUserWithoutField(userWithoutPassword);
        assertFalse(result);
    }

    @Test
    @DisplayName("No name User creation test")
    @Description("Base negative test of \"/register\" endpoint. Checking code status and response's body after correct request were send")
    public void createUserWithoutNameAndCheckResponse(){
        boolean result = userClient.createUserWithoutField(userWithoutName);
        assertFalse(result);
    }

    @Test
    @DisplayName("Correct User login test")
    @Description("Base test of \"/login\" endpoint. Checking code status and response's body after correct request were send")
    public void loginUserAndCheckResponse(){
        this.accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        UserCredentials creds = UserCredentials.from(user);
        boolean isLogin = userClient.loginUser(creds);
        assertTrue(isLogin);
    }

    @Test
    @DisplayName("Incorrect Email User login test")
    @Description("Base negative test of \"/login\" endpoint. Checking code status and response's body after correct request were send")
    public void loginIncorrectEmailUserAndCheckResponse(){
        this.accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        UserCredentials creds = UserCredentials.from(incorrectEmailUser);
        boolean result = userClient.incorrectUserLogin(creds);
        assertFalse(result);
    }

    @Test
    @DisplayName("Incorrect Password User login test")
    @Description("Base negative test of \"/login\" endpoint. Checking code status and response's body after correct request were send")
    public void loginIncorrectPasswordUserAndCheckResponse(){
        this.accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        UserCredentials creds = UserCredentials.from(getIncorrectPasswordUser);
        boolean result = userClient.incorrectUserLogin(creds);
        assertFalse(result);
    }

    @Test
    @DisplayName("Correct User info test")
    @Description("Base test of \"/user\" endpoint. Checking code status and response's body after correct request were send")
    public void getUserInfoAndCheckResponse(){
        this.accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        boolean result = userClient.getUserInfo(accessToken);
        assertTrue(result);
    }

    @Test
    @DisplayName("Update User name test")
    @Description("Base test of \"/user\" endpoint. Checking code status and response's body after correct request were send")
    public void updateUserNameAndCheckResponse(){
        this.accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        boolean isUpdated = userClient.updateUserDataAndCheckNameUpdate(accessToken);
        assertTrue(isUpdated);
    }

    @Test
    @DisplayName("Update User email test")
    @Description("Base test of \"/user\" endpoint. Checking code status and response's body after correct request were send")
    public void updateUserEmailAndCheckResponse(){
        this.accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        boolean isUpdated = userClient.updateUserDataAndCheckEmailUpdate(accessToken);
        assertTrue(isUpdated);
    }

    @Test
    @DisplayName("Update User without authorization test")
    @Description("Base negative test of \"/user\" endpoint. Checking code status and response's body after correct request were send")
    public void updateUserWithoutAuthorizationAndCheckResponse(){
        this.accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        boolean isUpdated = userClient.updateUserDataWithoutAuthorizationAndCheckResponse("incorrectToken");
        assertFalse(isUpdated);
    }
}
