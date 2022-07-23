package util;

import com.google.gson.JsonObject;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WebDriverUtil {

    private WebDriver driver;
    private WebElement element;
    private JavascriptExecutor executor;
    private Actions builder;
    private Helper helper;
    private CustomStringUtil csUtil;
    private JsonUtil jsonUtil;

    public WebDriverUtil() {
        this.driver = ((WebDriverFacade) ThucydidesWebDriverSupport.getDriver()).getProxiedDriver();
        executor = (JavascriptExecutor) this.driver;
        builder = new Actions(this.driver);
        helper = new Helper();
        csUtil = new CustomStringUtil();
        jsonUtil = new JsonUtil();
    }

    public WebDriverUtil(WebDriver p_driver) {
        this.driver = p_driver;
        executor = (JavascriptExecutor) this.driver;
        builder = new Actions(this.driver);
        helper = new Helper();
        csUtil = new CustomStringUtil();
        jsonUtil = new JsonUtil();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getElement(String elementName) {
        // TODO Auto-generated method stub
        String jPath = "$.elements[?(@.name=='" + elementName + "')].locators.web";
        String pageName = csUtil.extractPageName(elementName);
        File jFile = new File(csUtil.getFullPathFromFragments(new String[]{"src", "test", "resources", "pages", pageName + ".json"}));

        JsonObject jo = jsonUtil.fetchJsonObjectFromFile(jFile, jPath);

        String selector = jsonUtil.getValueFromJsonObject(jo, "selector");
        String strategy = jsonUtil.getValueFromJsonObject(jo, "strategy");

        switch (strategy) {
            case "xpath":
                return driver.findElement(By.xpath(selector));
            case "id":
                return driver.findElement(By.id(selector));
            case "name":
                return driver.findElement(By.name(selector));
            case "cssSelector":
                return driver.findElement(By.cssSelector(selector));
            case "className":
                return driver.findElement(By.className(selector));
            case "linkText":
                return driver.findElement(By.linkText(selector));
            case "tagName":
                return driver.findElement(By.tagName(selector));
            case "partialLinkText":
                return driver.findElement(By.partialLinkText(selector));
        }
        return null;
    }

    public String getElementLocator(String elementName) {
        String jPath = "$.elements[?(@.name=='" + elementName + "')].locators.web";
        String pageName = csUtil.extractPageName(elementName);
        File jFile = new File(csUtil.getFullPathFromFragments(new String[]{"src", "test", "resources", "pages", pageName + ".json"}));

        JsonObject jo = jsonUtil.fetchJsonObjectFromFile(jFile, jPath);

        return jsonUtil.getValueFromJsonObject(jo, "selector");
    }

    public String getElementText(String elementName) {
        return getElement(elementName).getText().trim();
    }

    public WebElement getElementByXPath(String expression) {
        return driver.findElement(By.xpath(expression));
    }

    public List<WebElement> getElementsByXPath(String expression) {
        return driver.findElements(By.xpath(expression));
    }

    public void clickElement(String elementName) {
        getElement(elementName).click();
    }

    public void clickElementByJS(String elementName) {
        executor.executeScript("arguments[0].click();", getElement(elementName));
    }

    public void clickElementByJS(WebElement element) {
        executor.executeScript("arguments[0].click();", element);
    }

    public void typeText(String text, String elementName) {
        element = getElement(elementName);
        element.clear();
        element.sendKeys(text);
    }

    public void typeTextByJS(String elementId, String value) {
        executor.executeScript("document.getElementById('" + elementId + "').value='" + value + "'");
    }

    public void goToURL(String url) {
        driver.get(url);
    }

    public void assertPageShowUp(String pageName) throws IOException {
        String jPath = "$.detectors.web";
        File jFile = new File(csUtil.getFullPathFromFragments(new String[]{"src", "test", "resources", "pages", pageName + ".json"}));
        String element = csUtil.getPureString(jsonUtil.fetchJsonStringFromFile(jFile, jPath));
        getElement(element);
    }

    public void assertPageShowUpInGivenTimeout(String pageName, int n) throws IOException {
        String jPath = "$.detectors.web";
        File jFile = new File(csUtil.getFullPathFromFragments(new String[]{"src", "test", "resources", "pages", pageName + ".json"}));
        String element = csUtil.getPureString(jsonUtil.fetchJsonStringFromFile(jFile, jPath));
        assertElementPresentInGivenTimeout(element, n);
    }

    public void refreshBrowser() {
        driver.navigate().refresh();
    }

    public void redirectTo(String url) {
        driver.navigate().to(url);
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }

    public void switchToIframe(String iframeName) {
        driver.switchTo().frame(getElement(iframeName));
    }

    public void exitIframe() {
        driver.switchTo().defaultContent();
    }

    public void singleScrollDown(int pixel) {
        executor.executeScript("window.scrollBy(0," + pixel + ")");
    }

    public void multiScrollDown(int pixel, int n) {
        for (int i = 0; i < n; i++) {
            singleScrollDown(pixel);
            helper.delaySync(0.5);
        }
    }

    public void scrollToElement(String elementName) {
        executor.executeScript("arguments[0].scrollIntoView();", getElement(elementName));
    }

    public void scrollToElement(WebElement element) {
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void clickElementIfPresent(String elementName) {
        try {
            clickElement(elementName);
        } catch (Exception ignored) {

        }
    }

    public boolean isElementDisplayed(String elementName) {
        try {
            getElement(elementName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void assertElementPresent(String elementName) {
        if (!getElement(elementName).isDisplayed())
            helper.writeStepFailed("Element is NOT being presented -> " + elementName);
    }

    public void assertElementPresentInGivenTimeout(String elementName, int n) {
        int i = 1;
        boolean found = false;

        do {
            try {
                System.out.println("Searching for element -> " + elementName);
                getElement(elementName);
                found = true;
            } catch (Exception e) {
                i += 1;
            }
        } while (i <= n && !found);

        if (!found)
            helper.writeStepFailed("Element is NOT being presented -> " + elementName);
    }

    public void assertElementNotPresent(String elementName) {
        try {
            getElement(elementName);
            helper.writeStepFailed("Element is being presented -> " + elementName);
        } catch (Exception ignored) {

        }
    }

    public void assertElementNotPresentInGivenTimeout(String elementName, int n) {
        int i = 1;
        boolean found = true;

        do {
            try {
                getElement(elementName);
                i += 1;
                helper.delaySync(5);
            } catch (Exception e) {
                found = false;
            }
        } while (i <= n && found);

        if (found)
            helper.writeStepFailed("Element is being presented -> " + elementName);
    }

    public void assertElementShowText(String elementName, String expectedText) {
        Assert.assertEquals(expectedText, getElement(elementName).getText().trim());
    }

    public void assertElementShowText(WebElement element, String expectedText) {
        Assert.assertEquals(expectedText, element.getText().trim());
    }

    public void assertElementContainText(String elementName, String expectedText) {
        Assert.assertTrue(getElement(elementName).getText().trim().contains(expectedText));
    }

    public void assertElementContainText(WebElement element, String expectedText) {
        Assert.assertTrue(element.getText().trim().contains(expectedText));
    }

    public void assertElementAttributeHasValue(String attribute, String elementName, String expected) {
        Assert.assertEquals(expected, getElement(elementName).getAttribute(attribute).trim());
    }

    public void assertElementAttributeHasValue(WebElement element, String attribute, String expected) {
        Assert.assertEquals(expected, element.getAttribute(attribute).trim());
    }

    public void assertElementAttributeContainValue(String attribute, String elementName, String expected) {
        Assert.assertTrue(getElement(elementName).getAttribute(attribute).trim().contains(expected));
    }

    public void assertElementAttributeNotContainValue(String attribute, String elementName, String expected) {
        Assert.assertFalse(getElement(elementName).getAttribute(attribute).trim().contains(expected));
    }

    public void assertElementAttributeContainValue(WebElement element, String attribute, String expected) {
        Assert.assertTrue(element.getAttribute(attribute).trim().contains(expected));
    }

    public void doubleClick(String elementName) {
        builder.doubleClick(getElement(elementName)).perform();
    }

    public void doubleClick(WebElement element) {
        builder.doubleClick(element).perform();
    }

    public void mouseHover(String elementName) {
        builder.moveToElement(getElement(elementName)).build().perform();
    }

    public void mouseHover(WebElement element) {
        builder.moveToElement(element).build().perform();
    }

    public void pressKey(String keyCode) {
        String OS = System.getProperty("os.name");
        switch (keyCode) {
            case "ESC":
                builder.sendKeys(Keys.ESCAPE).build().perform();
                break;
            case "TAB":
                builder.sendKeys(Keys.TAB).build().perform();
                break;
            case "ENTER":
                builder.sendKeys(Keys.ENTER).build().perform();
                break;
            case "BACK_SPACE":
                builder.sendKeys(Keys.BACK_SPACE).build().perform();
                break;
            case "DEL":
                builder.sendKeys(Keys.DELETE).build().perform();
                break;
            case "DOWN":
                builder.sendKeys(Keys.ARROW_DOWN).build().perform();
                break;
            case "UP":
                builder.sendKeys(Keys.ARROW_UP).build().perform();
                break;
            case "PAGE_DOWN":
                builder.sendKeys(Keys.PAGE_DOWN).build().perform();
                break;
            case "PAGE_UP":
                builder.sendKeys(Keys.PAGE_UP).build().perform();
                break;
            case "CTRL_A":
                if (OS.startsWith("Windows"))
                    builder.keyDown(Keys.CONTROL).sendKeys(String.valueOf('\u0061')).keyUp(Keys.CONTROL).build().perform();
                else
                    builder.keyDown(Keys.COMMAND).sendKeys(String.valueOf('\u0061')).keyUp(Keys.COMMAND).build().perform();
                break;
            case "CTRL_C":
                if (OS.startsWith("Windows"))
                    builder.keyDown(Keys.CONTROL).sendKeys(String.valueOf('\u0063')).keyUp(Keys.CONTROL).build().perform();
                else
                    builder.keyDown(Keys.COMMAND).sendKeys(String.valueOf('\u0063')).keyUp(Keys.COMMAND).build().perform();
                break;
            case "CTRL_V":
                if (OS.startsWith("Windows"))
                    builder.keyDown(Keys.CONTROL).sendKeys(String.valueOf('\u0076')).keyUp(Keys.CONTROL).build().perform();
                else
                    builder.keyDown(Keys.COMMAND).sendKeys(String.valueOf('\u0076')).keyUp(Keys.COMMAND).build().perform();
                break;
            case "CTRL_T":
                if (OS.startsWith("Windows"))
                    builder.keyDown(Keys.CONTROL).sendKeys(String.valueOf('\u0074')).keyUp(Keys.CONTROL).build().perform();
                else
                    builder.keyDown(Keys.COMMAND).sendKeys(String.valueOf('\u0074')).keyUp(Keys.COMMAND).build().perform();
                break;
            case "NUM_0":
                builder.sendKeys(Keys.NUMPAD0).build().perform();
                break;
            case "NUM_1":
                builder.sendKeys(Keys.NUMPAD1).build().perform();
                break;
            case "NUM_2":
                builder.sendKeys(Keys.NUMPAD2).build().perform();
                break;
            case "NUM_3":
                builder.sendKeys(Keys.NUMPAD3).build().perform();
                break;
            case "NUM_4":
                builder.sendKeys(Keys.NUMPAD4).build().perform();
                break;
            case "NUM_5":
                builder.sendKeys(Keys.NUMPAD5).build().perform();
                break;
            case "NUM_6":
                builder.sendKeys(Keys.NUMPAD6).build().perform();
                break;
            case "NUM_7":
                builder.sendKeys(Keys.NUMPAD7).build().perform();
                break;
            case "NUM_8":
                builder.sendKeys(Keys.NUMPAD8).build().perform();
                break;
            case "NUM_9":
                builder.sendKeys(Keys.NUMPAD9).build().perform();
                break;
            case "DOT":
                builder.sendKeys(Keys.DECIMAL).build().perform();
                break;
        }
    }

    public String takeSnapShot(String fileName) {
        TakesScreenshot scrShot = (TakesScreenshot) driver;
        File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
        CustomStringUtil csUtil = new CustomStringUtil();
        String fileWithPath = csUtil.getFullPathFromFragments(new String[]{"target", "site", "serenity", fileName + ".png"});
        File destFile = new File(fileWithPath);

        try {
            FileUtils.copyFile(scrFile, destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileWithPath;
    }

    public void terminate() {
        driver.quit();
    }
}
