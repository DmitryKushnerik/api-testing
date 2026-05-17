package tests;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.RateSteps;
import validators.RateValidator;

import static enums.Currency.*;

public class RateTest {
    private final RateSteps steps = new RateSteps();
    private final RateValidator validator = new RateValidator();

    @DataProvider(name = "currencies")
    public Object[][] currencyData() {
        return new Object[][]{
                {RUB.getCode(), RUB.getScale()},
                {EUR.getCode(), EUR.getScale()},
                {USD.getCode(), USD.getScale()}
        };
    }

    @Test(dataProvider = "currencies")
    public void checkRate(String currency, int scale) {
        String url = "https://kurs.onliner.by/sdapi/kurs/api/bestrate?currency=%S&type=nbrb".formatted(currency);
        String[] keys = {"amount", "grow", "scale", "banks"};
        Response response = steps.getRates(url);
        steps.logResponse(response);
        validator.validateStatusCode(response, 200);
        validator.validateSchema(response);
        validator.validateHeader(response);
        for (String key : keys)
            validator.validateKey(response, key);
        validator.validateGrow(response);
        validator.validateScale(response, scale);
        validator.validateScaleRegex(steps.getRatesAsString(response));
    }
}
