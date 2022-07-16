package steps;

import io.cucumber.java.en.And;
import util.Helper;
import util.WebDriverUtil;

public class ARM_2129 extends BaseStep {
    private WebDriverUtil driverUtil;
    private Helper helper;

    public ARM_2129() {
        driverUtil = new WebDriverUtil();
        helper = new Helper();
    }

    @And("Create button in the quotation creation screen should be enabled initially")
    public void createButtonInTheQuotationCreationScreenShouldBeEnabledInitially() {
        driverUtil.clickElement("QUOTATION_PAGE_NEW_QUOTATION_CREATE_BUTTON");
        driverUtil.assertElementPresent("QUOTATION_PAGE_NEW_QUOTATION_ERROR_MESSAGE_LABEL");
    }
}
