package core.helper;

public class UiSelectorHelper {
    private UiSelectorHelper() {
        //Do nothing
    }

    public static String scrollToId(String id) {
        return String.format("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceIdMatches(\"%s\"))", id);
    }

    public static String scrollToVisibleText(String text, boolean isExact) {
        return String.format("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().%s(\"%s\"))", isExact ? "text" : "textContains", text);
    }

    public static String scrollToVisibleText(String text) {
        return scrollToVisibleText(text, false);
    }

    public static String scrollToIdChildText(String id, String text) {
        return String.format("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceIdMatches(\"%s\").childSelector(text(\"%s\")))", id, text);
    }

    public static String scrollToIdAndText(String id, String text) {
        return String.format("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceIdMatches(\"%s\").text(\"%s\"))", id, text);
    }

    public static String scrollToIdHorizontalChildText(String id, String text) {
        return String.format("new UiScrollable(new UiSelector().scrollable(true).resourceId(\"%s\")).setAsHorizontalList().scrollIntoView(new UiSelector().childSelector(text(\"%s\")))", id, text);
    }
}
