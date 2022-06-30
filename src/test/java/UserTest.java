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
    //private String accessToken;

    @Before
    public void setup(){
        userClient = new UserClient();
    }

    //@After
    //public void clear(){
        //userClient.deleteUser(accessToken);
    //}

    @Test
    @DisplayName("New User creation test")
    @Description("Base test of \"/register\" endpoint. Checking code status and response's body after correct request were send")
    public void createUserAndCheckResponse(){
        String accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Existed User creation test")
    @Description("Base negative test of \"/register\" endpoint. Checking code status and response's body after correct request were send")
    public void createExistedUserAndCheckResponse(){
        String accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        UserCredentials creds = UserCredentials.from(user);
        boolean result = userClient.createExistedUser(creds);
        assertFalse(result);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
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
        String accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        UserCredentials creds = UserCredentials.from(user);
        boolean isLogin = userClient.loginUser(creds);
        assertTrue(isLogin);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Incorrect Email User login test")
    @Description("Base negative test of \"/login\" endpoint. Checking code status and response's body after correct request were send")
    public void loginIncorrectEmailUserAndCheckResponse(){
        String accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        UserCredentials creds = UserCredentials.from(incorrectEmailUser);
        boolean result = userClient.incorrectUserLogin(creds);
        assertFalse(result);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Incorrect Password User login test")
    @Description("Base negative test of \"/login\" endpoint. Checking code status and response's body after correct request were send")
    public void loginIncorrectPasswordUserAndCheckResponse(){
        String accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        UserCredentials creds = UserCredentials.from(getIncorrectPasswordUser);
        boolean result = userClient.incorrectUserLogin(creds);
        assertFalse(result);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Correct User info test")
    @Description("Base test of \"/user\" endpoint. Checking code status and response's body after correct request were send")
    public void getUserInfoAndCheckResponse(){
        String accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        boolean result = userClient.getUserInfo(accessToken);
        assertTrue(result);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Update User name test")
    @Description("Base test of \"/user\" endpoint. Checking code status and response's body after correct request were send")
    public void updateUserNameAndCheckResponse(){
        String accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        boolean isUpdated = userClient.updateUserDataAndCheckNameUpdate(accessToken);
        assertTrue(isUpdated);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Update User email test")
    @Description("Base test of \"/user\" endpoint. Checking code status and response's body after correct request were send")
    public void updateUserEmailAndCheckResponse(){
        String accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        boolean isUpdated = userClient.updateUserDataAndCheckEmailUpdate(accessToken);
        assertTrue(isUpdated);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Update User without authorization test")
    @Description("Base negative test of \"/user\" endpoint. Checking code status and response's body after correct request were send")
    public void updateUserWithoutAuthorizationAndCheckResponse(){
        String accessToken = userClient.createUser(user);
        assertNotNull(accessToken);
        boolean isUpdated = userClient.updateUserDataWithoutAuthorizationAndCheckResponse("incorrectToken");
        assertFalse(isUpdated);
        boolean isDeleted = userClient.deleteUser(accessToken);
        assertTrue(isDeleted);
    }


}
