package util;

import net.serenitybdd.core.Serenity;
import org.junit.Assert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Properties;

public class Helper {
    private Properties prop;
    private CustomStringUtil csUtil;
    private String globFile;
    private WebDriverUtil driverUtil;

    public Helper() {
        csUtil = new CustomStringUtil();
        globFile = csUtil.getFullPathFromFragments(new String[]{"src", "test", "resources", "glob.txt"});
    }

    public Helper(WebDriverUtil p_driverUtil) {
        csUtil = new CustomStringUtil();
        globFile = csUtil.getFullPathFromFragments(new String[]{"src", "test", "resources", "glob.txt"});
        driverUtil = p_driverUtil;
    }

    private Properties getProp() {
        if (prop == null) {
            prop = new Properties();
            String[] arr = new String[]{"src", "test", "resources", "config.properties"};

            try {
                InputStream is = Files.newInputStream(Paths.get(csUtil.getFullPathFromFragments(arr)));
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

    private String readGlobalParam(String key) {
        try {
            FileReader reader = new FileReader(globFile);
            BufferedReader br = new BufferedReader(reader);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.split("=")[0].trim().equalsIgnoreCase(key)) {
                    reader.close();
                    return line.split("=")[1].trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeGlobalParam(String key, String oldVal, String newVal) {
        File fileToBeModified = new File(globFile);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));
            String line = reader.readLine();
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }

            String newContent = oldContent.replaceAll(key + "=" + oldVal, key + "=" + newVal);
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void takeScreenshot() {
        String key = "screenshotID";
        int ssId = Integer.parseInt(Objects.requireNonNull(readGlobalParam(key)));
        int nextSSID = ssId + 1;

        String fileName = "evidence_" + ssId;
        driverUtil.takeSnapShot(fileName);
        writeLogToReport("<img src='" + fileName + ".png'>", String.valueOf(ssId));
        writeGlobalParam(key, String.valueOf(ssId), String.valueOf(nextSSID));
    }

    public String randomString(int len) {
        final String AB = "文字以内で入力してください";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
