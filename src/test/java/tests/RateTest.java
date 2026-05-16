package tests;

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
        String response = steps.getRatesResponse(url);
        validator.validateSchema(url);
        validator.validateHeader(url);
        for (String key : keys)
            validator.validateKey(url, key);
        validator.validateGrow(url);
        validator.validateScale(url, scale);
        validator.validateScaleRegex(response);
    }
}
