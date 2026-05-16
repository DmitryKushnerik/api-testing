package steps;

import static io.restassured.RestAssured.given;

public class RateSteps {
    public String getRatesResponse(String url) {
        return given()
                .log().all()
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .asString();
    }
}
