package steps;

import io.cucumber.java.en.And;
import util.Helper;
import util.WebDriverUtil;

public class LeftMenuSteps extends BaseStep {
    private WebDriverUtil driverUtil;
    private Helper helper;

    public LeftMenuSteps() {
        driverUtil = new WebDriverUtil();
        helper = new Helper(driverUtil);
    }

    @And("User navigate to {string} on left menu")
    public void userNavigateToOnLeftMenu(String menuName) {
        String xpath = "//ul[@class='el-menu']//child::span[contains(text(),'" + menuName + "')]//parent::li[1]";
        driverUtil.getElementByXPath(xpath).click();
    }
}
