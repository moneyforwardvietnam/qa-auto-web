package core.helper;


public class Constants {
    private static Constants instance = null;

    private Constants() {
        // Do nothing
    }

    public static Constants getInstance() {
        if (instance == null) {
            instance = new Constants();
        }
        return instance;
    }

    public static final String IOS = "iOS";
    public static final String ANDROID = "Android";
    public static final int TIMEOUT_DEFAULT = 60;
}