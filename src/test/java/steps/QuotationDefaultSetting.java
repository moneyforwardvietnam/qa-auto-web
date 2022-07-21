package steps;

import io.cucumber.java.en.And;
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
    }

    @And("User upload a {string} image with {string} size into {string}")
    public void userUploadAImageWithSizeInto(String type, String size, String elementName) {
        String pref = "image-";
        String post = ".jpeg";
        String fileName = pref + size + post;
        String[] pathToFile = new String[]{"src", "test", "resources", "img", "stamp", fileName};

        driverUtil.getElement(elementName).sendKeys(csUtil.getFullPathFromFragments(pathToFile));
    }
}
