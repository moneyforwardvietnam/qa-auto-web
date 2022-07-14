package util;

import java.nio.file.FileSystems;

public class CustomStringUtil {
    private String fileSeparator = FileSystems.getDefault().getSeparator();
    private String userDir = System.getProperty("user.dir");

    public CustomStringUtil() {
    }

    public String getFullPathFromFragments(String[] frags) {
        StringBuilder r = new StringBuilder();

        for (String s : frags) {
            r.append(s).append(fileSeparator);
        }
        return userDir + fileSeparator + r.substring(0, r.length() - 1);
    }

    public String extractPageName(String elementName) {
        int i = elementName.indexOf("PAGE") + 4;
        return elementName.substring(0, i);
    }

    public String getPureString(String input) {
        return input.replaceAll("^\"+|\"+$", "");
    }
}
