package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import util.CustomStringUtil;
import util.Helper;
import util.WebDriverUtil;

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
}
