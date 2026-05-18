package steps;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RateSteps {
    public Response getRates(String url) {
        return given()
                .log().all()
                .when()
                .get(url);
    }

    public String getRatesAsString(Response response) {
        return response
                .then()
                .extract()
                .asString();
    }

    public void logResponse(Response response) {
        response
                .then()
                .log().all();
    }
}
