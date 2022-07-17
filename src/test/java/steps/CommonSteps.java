package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import util.Helper;
import util.WebDriverUtil;

public class CommonSteps extends BaseStep {
    private WebDriverUtil driverUtil;
    private Helper helper;

    public CommonSteps() {
        driverUtil = new WebDriverUtil();
        helper = new Helper();
    }

    @Given("User redirects to {string}")
    public void userRedirectsTo(String env) {
        String url = helper.getConfig("env." + env + ".url");
        String user = helper.getConfig("env." + env + ".auth.user");
        String pass = helper.getConfig("env." + env + ".auth.pass");
        url = "https://" + user + ":" + pass + "@" + url;
        driverUtil.goToURL(url);
        context.setContext("env", env);
    }

    @When("{string} shows up")
    public void showsUp(String pageName) throws Exception {
        driverUtil.assertPageShowUp(pageName);
    }

    @And("{string} shows up in {int} timeout")
    public void showsUpInTimeout(String pageName, int n) throws Exception {
        driverUtil.assertPageShowUpInGivenTimeout(pageName, n);
    }

    @And("User clicks on {string}")
    public void userClicksOn(String elementName) {
        driverUtil.clickElement(elementName);
    }

    @And("User clicks on {string} by JS")
    public void userClicksOnByJS(String elementName) {
        driverUtil.clickElementByJS(elementName);
    }

    @And("User types {string} into {string}")
    public void userTypesInto(String text, String elementName) {
        driverUtil.typeText(text, elementName);
    }

    @And("User types {string} into {string} by JS")
    public void userTypesIntoByJS(String text, String elementName) {
        driverUtil.typeTextByJS(text, elementName);
    }

    @And("User refresh browser")
    public void userRefreshBrowser() {
        driverUtil.refreshBrowser();
    }

    @And("User navigate {string}")
    public void userNavigate(String direction) {
        if (direction.equalsIgnoreCase("BACK"))
            driverUtil.navigateBack();
        else
            driverUtil.navigateForward();
    }

    @And("User switch to {string}")
    public void userSwitchTo(String iframeName) {
        driverUtil.switchToIframe(iframeName);
    }

    @And("User exit current iframe")
    public void userExitCurrentIframe() {
        driverUtil.exitIframe();
    }

    @And("User scroll down by {int} pixel")
    public void userScrollDownByPixel(int pixel) {
        driverUtil.singleScrollDown(pixel);
    }

    @And("User scroll down by {int} pixel {int} times")
    public void userScrollDownByPixelTimes(int pixel, int n) {
        driverUtil.multiScrollDown(pixel, n);
    }

    @And("User scroll to {string}")
    public void userScrollTo(String elementName) {
        driverUtil.scrollToElement(elementName);
    }

    @And("User clicks on {string} if present")
    public void userClicksOnIfPresent(String elementName) {
        driverUtil.clickElementIfPresent(elementName);
    }

    @And("{string} is present")
    public void isPresent(String elementName) {
        driverUtil.assertElementPresent(elementName);
    }

    @And("{string} is present in {int} timeout")
    public void isPresentInTimeout(String elementName, int n) {
        driverUtil.assertElementPresentInGivenTimeout(elementName, n);
    }

    @And("{string} is not present")
    public void isNotPresent(String elementName) {
        driverUtil.assertElementNotPresent(elementName);
    }

    @And("{string} is not present in {int} timeout")
    public void isNotPresentInTimeout(String elementName, int n) {
        driverUtil.assertElementNotPresentInGivenTimeout(elementName, n);
    }

    @And("{string} shows {string}")
    public void shows(String elementName, String expectedText) {
        driverUtil.assertElementShowText(elementName, expectedText);
    }

    @And("{string} contains {string}")
    public void contains(String elementName, String expectedText) {
        driverUtil.assertElementContainText(elementName, expectedText);
    }

    @And("{string} of {string} has {string}")
    public void ofHas(String attribute, String elementName, String expectedValue) {
        driverUtil.assertElementAttributeHasValue(attribute, elementName, expectedValue);
    }

    @And("{string} of {string} contains {string}")
    public void ofContains(String attribute, String elementName, String expectedValue) {
        driverUtil.assertElementAttributeContainValue(attribute, elementName, expectedValue);
    }

    @And("User double click on {string}")
    public void userDoubleClickOn(String elementName) {
        driverUtil.doubleClick(elementName);
    }

    @And("User mouse hover on {string}")
    public void userMouseHoverOn(String elementName) {
        driverUtil.mouseHover(elementName);
    }

    @And("User press key {string}")
    public void userPressKey(String keyCode) {
        driverUtil.pressKey(keyCode);
    }

    @And("User waits for {int} seconds")
    public void userWaitsForSeconds(int sec) {
        helper.delaySync(sec);
    }

    @And("User clicks on {string} by {string}")
    public void userClicksOnBy(String elementType, String visibleText) {
        String xpath = "//span[contains(text(),'" + visibleText + "')]//parent::" + elementType + "[1]";
        driverUtil.getElementByXPath(xpath).click();
    }

    @Given("User switch to language {string}")
    public void userSwitchToLanguage(String language) {
        driverUtil.clickElement("MASTER_PAGE_LANGUAGE_SETTING_DROPDOWN");
        try {
            driverUtil.clickElement("MASTER_PAGE_LANGUAGE_SETTING_" + language.toUpperCase() + "_ITEM");
        } catch (Exception e) {
            driverUtil.clickElementByJS("MASTER_PAGE_LANGUAGE_SETTING_" + language.toUpperCase() + "_ITEM");
        }
    }
}
