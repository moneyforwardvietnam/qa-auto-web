package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import util.Helper;
import util.WebDriverUtil;

import java.util.Map;

public class ARM_2129 extends BaseStep {
    private WebDriverUtil driverUtil;
    private Helper helper;

    public ARM_2129() {
        driverUtil = new WebDriverUtil();
        helper = new Helper(driverUtil);
    }

    @And("Create button in the quotation creation screen should be enabled initially")
    public void createButtonInTheQuotationCreationScreenShouldBeEnabledInitially() {
        driverUtil.clickElement("QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON");
        helper.delaySync(1);
        driverUtil.assertElementPresent("QUOTATION_PAGE_NEW_QUOTATION_ERROR_MESSAGE_LABEL");
        helper.takeScreenshot();
    }

    @When("User enter Client info as below")
    public void userEnterClientInfoAsBelow(Map<String, String> table) {
        String client = table.get("client").trim();
        String clientDetails = table.get("client_details").trim();
        String title = table.get("title").trim();

        driverUtil.typeText(client, "QUOTATION_PAGE_NEW_QUOTATION_CLIENT_NAME_INPUT");
        driverUtil.typeText(clientDetails, "QUOTATION_PAGE_NEW_QUOTATION_CLIENT_DETAILS_INPUT");
        driverUtil.typeText(title, "QUOTATION_PAGE_NEW_QUOTATION_TITLE_INPUT");
    }

    @And("User enter {string} into the mandatory field {string}")
    public void userEnterIntoTheMandatoryField(String value, String field) {
        driverUtil.typeText(value, field);
        helper.delaySync(1);
        driverUtil.pressKey("ESC");
    }
}
