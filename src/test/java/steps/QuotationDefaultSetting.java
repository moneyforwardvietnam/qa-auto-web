package steps;

import io.cucumber.java.en.When;
import util.Helper;
import util.WebDriverUtil;

public class QuotationDefaultSetting extends BaseStep {
    private WebDriverUtil driverUtil;
    private Helper helper;

    public QuotationDefaultSetting() {
        driverUtil = new WebDriverUtil();
        helper = new Helper(driverUtil);
    }

    @When("User enter a string with {int} chars into {string}")
    public void userEnterAStringWithCharsInto(int len, String elementName) {
        String input = helper.randomString(len);
        driverUtil.typeText(input, elementName);
        helper.delaySync(1);
    }
}
