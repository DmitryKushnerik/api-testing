package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertTrue;

public class RateValidator {
    public void validateSchema(String url) {
        given()
                .when()
                .get(url)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/rate_schema.json"));
    }

    public void validateHeader(String url) {
        given()
                .when()
                .get(url)
                .then()
                .header("Content-type", containsString("application/json"));
    }

    public void validateKey(String url, String key) {
        given()
                .when()
                .get(url)
                .then()
                .body("$", hasKey(key));
    }

    public void validateScale(String url, int scale) {
        given()
                .when()
                .get(url)
                .then()
                .body("scale", equalTo(scale));
    }

    public void validateGrow(String url) {
        given()
                .when()
                .get(url)
                .then()
                .body("grow", oneOf(-1, 0, 1));
    }

    public void validateScaleRegex(String responseBody) {
        String regex = "\"scale\":\\s*(?:[1-9]|[1-9]\\d{1,3}|10000)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(responseBody);
        assertTrue(matcher.find());
    }
}
