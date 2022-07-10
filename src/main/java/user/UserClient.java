package user;

import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserClient extends RestAssuredClient{

    private final String ROOT = "/auth";
    private final String LOGIN = ROOT + "/login";
    private final String LOGOUT = ROOT + "/logout";
    private final String REGISTER = ROOT + "/register";
    private final String USER = ROOT + "/user";


    public String createUser(User user) {
        return given().spec(requestSpecification)
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

    public boolean createExistedUser(UserCredentials creds) {
        return given().spec(requestSpecification)
                .and()
                .body(creds)
                .when()
                .post(REGISTER)
                .then().log().all()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("User already exists"))
                .extract()
                .path("success");
    }

    public boolean createUserWithoutField(User user) {
        return given().spec(requestSpecification)
                .and()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");
    }

    public boolean loginUser(UserCredentials creds){
        return given().spec(requestSpecification)
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    public boolean incorrectUserLogin(UserCredentials creds){
        return given().spec(requestSpecification)
                .and()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("email or password are incorrect"))
                .extract()
                .path("success");
    }

    public boolean updateUserDataAndCheckNameUpdate(String accessToken){
        UserData json = new UserData();
        return given().spec(requestSpecification)
                .header("authorization", accessToken)
                .and()
                .body(json)
                .when()
                .patch(USER)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("user.name", equalTo(json.getName()))
                .extract()
                .path("success");
    }

    public boolean updateUserDataAndCheckEmailUpdate(String accessToken){
        UserData json = new UserData();
        return given().spec(requestSpecification)
                .header("authorization", accessToken)
                .and()
                .body(json)
                .when()
                .patch(USER)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("user.email", equalTo(json.getEmail()))
                .extract()
                .path("success");
    }

    public boolean updateUserDataWithoutAuthorizationAndCheckResponse(String accessToken){
        UserData json = new UserData();
        return given().spec(requestSpecification)
                .header("authorization", accessToken)
                .and()
                .body(json)
                .when()
                .patch(USER)
                .then().log().all()
                .assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("You should be authorised"))
                .extract()
                .path("success");
    }

    public boolean getUserInfo(String accessToken){
        return given().spec(requestSpecification)
                .header("authorization", accessToken)
                .when()
                .get(USER)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("user.email", notNullValue())
                .extract()
                .path("success");
    }

    public boolean deleteUser(String accessToken){
        return given().spec(requestSpecification)
                .header("authorization", accessToken)
                .log().all()
                .when()
                .delete(USER)
                .then().log().all()
                .assertThat()
                .statusCode(202)
                .extract()
                .path("success");
    }
}