package core.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

    private DateTimeHelper() {
        // Do nothing
    }

    private static final LogHelper logger = LogHelper.getInstance();

    public static String getTimeStamp(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static long getCurrentTimeInMillis() {
        return new Date().getTime() / 10000;
    }

    public static String getTimeStamp() {
        return getTimeStamp("yyyyMMddHHmmss").trim()
                .replace("/", "")
                .replace(":", "")
                .replace(" ", "");
    }
}