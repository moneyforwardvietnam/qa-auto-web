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
        switch (menuName){
            case "Settings":
                menuName = "設定";
                break;
            case "Quotations":
                menuName = "見積書";
                break;
            case "Opportunities":
                menuName = "案件";
                break;
            case "Sales":
                menuName = "売上";
                break;
            case "Invoices":
                menuName = "請求書";
                break;
            case "Reconciliations":
                menuName = "消込";
                break;
            case "Reports":
                menuName = "レポート";
                break;
        }
        String xpath = "//ul[@class='el-menu']//child::span[contains(text(),'" + menuName + "')]//parent::li[1]";
        driverUtil.getElementByXPath(xpath).click();
    }
}
