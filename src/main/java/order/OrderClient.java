package order;

import user.RestAssuredClient;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient{
    private final String ROOT = "/orders";
    private final String INGREDIENTS = ROOT + "/ingredients";

    public boolean createOrderWithouAuthorization(Order order) {
        return given().spec(requestSpecification)
                .body(order)
                .when().log().all()
                .post(ROOT)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    public boolean createOrderWithAuthorization(Order order, String accessToken) {
        return given().spec(requestSpecification)
                .header("authorization", accessToken)
                .body(order)
                .when().log().all()
                .post(ROOT)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    public int createIncorrectOrder(Order order) {
        return given().spec(requestSpecification)
                .body(order)
                .when().log().all()
                .post(ROOT)
                .then().log().all()
                .statusCode(500)
                .extract()
                .statusCode();
    }

    public boolean createEmptyOrder(Order order) {
        return given().spec(requestSpecification)
                .body(order)
                .when().log().all()
                .post(ROOT)
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("success");
    }

    public boolean getUserOrderWithAuthorization(Order order, String accessToken) {
        return given().spec(requestSpecification)
                .header("authorization", accessToken)
                .body(order)
                .when().log().all()
                .get(ROOT)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("success");
    }

    public boolean getUserOrderWithoutAuthorization(Order order) {
        return given().spec(requestSpecification)
                .body(order)
                .when().log().all()
                .get(ROOT)
                .then().log().all()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("success");
    }
}
