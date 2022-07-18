package steps;

import io.cucumber.java.en.And;
import util.Helper;
import util.WebDriverUtil;

public class LoginSteps extends BaseStep {
    private WebDriverUtil driverUtil;
    private Helper helper;

    public LoginSteps() {
        driverUtil = new WebDriverUtil();
        helper = new Helper();
    }

    @And("User enter valid credentials")
    public void userEnterValidCredentials() {
        String user = helper.getConfig("env." + context.getContext("env") + ".login.user");
        String pass = helper.getConfig("env." + context.getContext("env") + ".login.pass");
        driverUtil.typeText(user, "LOGIN_PAGE_USERNAME_INPUT");
        driverUtil.clickElement("LOGIN_PAGE_SUBMIT_BUTTON");
        driverUtil.typeText(pass, "LOGIN_PAGE_PASSWORD_INPUT");
        driverUtil.clickElement("LOGIN_PAGE_SUBMIT_BUTTON");
    }
}
