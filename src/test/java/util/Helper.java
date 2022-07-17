package util;

import net.serenitybdd.core.Serenity;
import org.junit.Assert;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Helper {
    private Properties prop;
    private CustomStringUtil customStringUtil;

    public Helper() {
        customStringUtil = new CustomStringUtil();
    }

    private Properties getProp() {
        if (prop == null) {
            prop = new Properties();
            String[] arr = new String[]{"src", "test", "resources", "config.properties"};

            try {
                InputStream is = Files.newInputStream(Paths.get(customStringUtil.getFullPathFromFragments(arr)));
                prop.load(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return prop;
    }

    public String getConfig(String key) {
        return getProp().getProperty(key);
    }

    public void writeStepFailed() {
        Assert.fail();
    }

    public void writeStepFailed(String message) {
        Assert.fail(message);
    }

    public void delaySync(double sec) {
        try {
            Thread.sleep((long) (sec * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void compareEqual(Object source, Object target) {
        Assert.assertEquals(source, target);
    }

    public void compareNotEqual(Object source, Object target) {
        Assert.assertNotEquals(source, target);
    }

    public void writeLogToReport(String title, String content) {
        Serenity.recordReportData().withTitle(title).andContents(content);
    }
}
