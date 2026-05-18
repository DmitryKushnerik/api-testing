package validators;

import io.restassured.response.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertTrue;

public class RateValidator {
    public void validateStatusCode(Response response, int code) {
        response
                .then()
                .statusCode(code);
    }

    public void validateSchema(Response response) {
        response
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/rate_schema.json"));
    }

    public void validateHeader(Response response) {
        response
                .then()
                .header("Content-type", containsString("application/json"));
    }

    public void validateKey(Response response, String key) {
        response
                .then()
                .body("$", hasKey(key));
    }

    public void validateScale(Response response, int scale) {
        response
                .then()
                .body("scale", equalTo(scale));
    }

    public void validateGrow(Response response) {
        response
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
