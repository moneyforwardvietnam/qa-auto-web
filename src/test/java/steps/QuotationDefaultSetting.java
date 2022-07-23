package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import util.CustomStringUtil;
import util.Helper;
import util.WebDriverUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class QuotationDefaultSetting extends BaseStep {
    private WebDriverUtil driverUtil;
    private Helper helper;
    private CustomStringUtil csUtil;

    public QuotationDefaultSetting() {
        driverUtil = new WebDriverUtil();
        helper = new Helper(driverUtil);
        csUtil = new CustomStringUtil();
    }

    @When("User enter a string with {int} chars into {string}")
    public void userEnterAStringWithCharsInto(int len, String elementName) {
        String input = helper.randomString(len);
        driverUtil.typeText(input, elementName);
        helper.delaySync(1);
        if (elementName.contains("ISSUER"))
            context.setContext("issuerText", input);
        else if (elementName.contains("REMARKS")) {
            context.setContext("remarksText", input);
        }
    }

    @And("User upload a {string} image with {string} size into {string}")
    public void userUploadAImageWithSizeInto(String type, String size, String elementName) {
        String pref = "image-";
        String post = ".jpeg";
        String fileName = pref + size + post;
        String[] pathToFile = new String[]{"src", "test", "resources", "img", "stamp", fileName};

        driverUtil.getElement(elementName).sendKeys(csUtil.getFullPathFromFragments(pathToFile));
        helper.delaySync(1);
        if (elementName.equals("SETTINGS_PAGE_QUOTATION_STAMP_IMAGE_UPLOAD_INPUT")) {
            String src = driverUtil.getElement("SETTINGS_PAGE_QUOTATION_STAMP_IMAGE_HAS_IMAGE").getAttribute("src").trim();
            context.setContext("stampImgSrc", src);
        }
    }

    @Then("{string} should show correct text per inputted")
    public void shouldShowCorrectTextPerInputted(String elementName) {
        String expected;
        String actual;

        if (elementName.contains("ISSUER")) {
            expected = context.getContext("issuerText");
            driverUtil.assertElementShowText(elementName, expected);
        } else if (elementName.contains("REMARKS")) {
            expected = context.getContext("remarksText");
            actual = driverUtil.getElement(elementName).getAttribute("innerText");
            helper.compareEqual(expected, actual);
        } else if (elementName.contains("STAMP")) {
            expected = context.getContext("stampImgSrc");
            driverUtil.assertElementAttributeHasValue("src", elementName, expected);
        }
    }

    @And("{string} should be {string}")
    public void shouldBe(String elementName, String status) {
        if (status.equalsIgnoreCase("disabled")) {
            driverUtil.assertElementAttributeContainValue("class", elementName, status.toUpperCase());
        } else if (status.equalsIgnoreCase("enabled")) {
            driverUtil.assertElementAttributeNotContainValue("class", elementName, "disabled");
        }
    }

    @When("User select first {string} for New Quotation")
    public void userSelectFirstForNewQuotation(String fieldName) {
        if (fieldName.equalsIgnoreCase("client")) {
            driverUtil.clickElement("QUOTATION_PAGE_NEW_QUOTATION_CLIENT_NAME_INPUT");
        } else if (fieldName.equalsIgnoreCase("item")) {
            driverUtil.clickElement("QUOTATION_PAGE_NEW_QUOTATION_FIRST_ITEM_NAME_INPUT");
        }
        helper.delaySync(1);
        driverUtil.pressKey("DOWN");
        driverUtil.pressKey("ENTER");
    }

    @And("User saves {string} into context as {string}")
    public void userSavesIntoContextAs(String value, String key) {
        context.setContext(key, value);
    }

    @And("User update Quotation setting using {string} account")
    public void userUpdateQuotationSettingUsingAccount(String office) throws IOException {
        WebDriverManager.firefoxdriver().setup();
        WebDriver ffDriver = new FirefoxDriver();
        WebDriverUtil ffDriverUtil = new WebDriverUtil(ffDriver);

        ffDriverUtil.redirectTo(context.getContext("baseURL"));
        ffDriverUtil.assertPageShowUp("PRE_LOGIN_PAGE");
        ffDriverUtil.clickElement("PRE_LOGIN_PAGE_CONTINUE_BUTTON");
        ffDriverUtil.assertPageShowUp("LOGIN_PAGE");

        String user = helper.getConfig("env." + context.getContext("env") + "." + office + ".login.user");
        String pass = helper.getConfig("env." + context.getContext("env") + "." + office + ".login.pass");
        ffDriverUtil.typeText(user, "LOGIN_PAGE_USERNAME_INPUT");
        ffDriverUtil.clickElement("LOGIN_PAGE_SUBMIT_BUTTON");
        ffDriverUtil.typeText(pass, "LOGIN_PAGE_PASSWORD_INPUT");
        ffDriverUtil.clickElement("LOGIN_PAGE_SUBMIT_BUTTON");
        ffDriverUtil.assertPageShowUpInGivenTimeout("MASTER_PAGE", 10);
        ffDriverUtil.clickElement("MASTER_PAGE_LEFT_MENU_SETTINGS_LABEL");
        ffDriverUtil.assertPageShowUpInGivenTimeout("SETTINGS_PAGE", 10);
        ffDriverUtil.assertElementPresentInGivenTimeout("SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON", 10);
        ffDriverUtil.clickElement("SETTINGS_PAGE_SUB_MENU_QUOTATION_ITEM_BUTTON");
        ffDriverUtil.assertElementPresent("SETTINGS_PAGE_QUOTATION_PREVIEW_HEADER_LABEL");
        ffDriverUtil.typeText("same office account updated issuer", "SETTINGS_PAGE_QUOTATION_ISSUER_TEXTAREA");
        ffDriverUtil.clickElement("SETTINGS_PAGE_QUOTATION_SAVE_BUTTON");
        ffDriverUtil.assertElementPresent("SETTINGS_PAGE_QUOTATION_SAVED_TEMPLATE_LABEL");
        ffDriverUtil.terminate();
        helper.delaySync(2);
    }

    @And("{string} column should be sorted in {string} order by default")
    public void columnShouldBeSortedInOrderByDefault(String header, String order) {
        String xpath = "//span[text()='" + header + "']//parent::div//parent::th";
        Assert.assertTrue(driverUtil.getElementByXPath(xpath).getAttribute("class").contains(order));
    }

    @Then("{string} column data of {string} table should be sorted in {string} order properly")
    public void columnDataOfTableShouldBeSortedInOrderProperly(String header, String table, String order) {
        ArrayList<Integer> actual = helper.getIntegerArray(driverUtil.getAllRowsByHeader(table, header));
        ArrayList<Integer> expected = helper.getIntegerArray(context.getContext("colData"));

        if (order.equalsIgnoreCase("ascending"))
            Collections.sort(expected);
        else
            Collections.sort(expected, Collections.reverseOrder());

        Assert.assertTrue(expected.equals(actual));
    }

    @And("User snapshot current state of {string} column data in {string} table")
    public void userSnapshotCurrentStateOfColumnDataInTable(String header, String table) {
        ArrayList<String> colData = driverUtil.getAllRowsByHeader(table, header);
        context.setContext("colData", colData);
    }
}
