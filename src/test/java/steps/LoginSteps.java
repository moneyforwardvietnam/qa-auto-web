package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import util.Helper;
import util.WebDriverUtil;

public class LoginSteps extends BaseStep {
    private WebDriverUtil driverUtil;
    private Helper helper;

    public LoginSteps() {
        driverUtil = new WebDriverUtil();
        helper = new Helper(driverUtil);
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

    @When("User enter valid credentials of {string}")
    public void userEnterValidCredentialsOf(String office) {
        String user = helper.getConfig("env." + context.getContext("env") + "." + office + ".login.user");
        String pass = helper.getConfig("env." + context.getContext("env") + "." + office + ".login.pass");
        driverUtil.typeText(user, "LOGIN_PAGE_USERNAME_INPUT");
        driverUtil.clickElement("LOGIN_PAGE_SUBMIT_BUTTON");
        driverUtil.typeText(pass, "LOGIN_PAGE_PASSWORD_INPUT");
        driverUtil.clickElement("LOGIN_PAGE_SUBMIT_BUTTON");
    }

    @When("User logout ARM")
    public void userLogoutARM() {
        driverUtil.clickElement("MASTER_PAGE_LOGGED_IN_USER_LABEL");
        try {
            driverUtil.clickElement("MASTER_PAGE_LOGOUT_BUTTON");
        } catch (Exception e) {
            driverUtil.clickElementByJS("MASTER_PAGE_LOGOUT_BUTTON");
        }
    }
}
