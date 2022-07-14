package core.driver;

import core.common.CommonActions;
import core.helper.LogHelper;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class AppiumServer {

    public static final String DEFAULT_APPIUM_SERVER_URI = "/wd/hub";
    public static final String DEFAULT_APPIUM_SERVER = "0.0.0.0";
    public static final int DEFAULT_APPIUM_PORT = 3456;
    private static int retryStartServer = 3;
    private static int timeout = 200000;
    private static AppiumDriverLocalService service;

    private static final Logger logger = LogHelper.getLogger();

    private AppiumServer() {
    }

    public static void startAppiumServer() {
        try {
            for (int i = 0; i < retryStartServer; i++) {
                startServerByCmd();
                boolean isStarted = checkIfServerIsRunning(DEFAULT_APPIUM_SERVER, DEFAULT_APPIUM_PORT, timeout);
                if (isStarted) {
                    break;
                } else {
                    CommonActions.holdOn(1);
                }
            }
        } catch (InterruptedException e) {
            logger.error("InterruptedException happens. Please check again!.");
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.error("IOException happens. Please check again!.");
            Thread.currentThread().interrupt();
        }
    }

    private static void startServerByCmd() throws IOException {
        String filePath;
        String appiumUrl = "appium -a " + DEFAULT_APPIUM_SERVER + " -p " + DEFAULT_APPIUM_PORT;

        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            filePath = "//usr//local//bin//appium.sh";

            FileWriter fileWriter = new FileWriter(filePath);
            try (BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {
                bufferWriter.write(appiumUrl);
            }

            Runtime.getRuntime().exec("chmod u+x " + filePath);
            Runtime.getRuntime().exec("/usr/bin/open -a Terminal " + filePath);
        } else if (System.getProperty("os.name").toLowerCase().contains("window")) {
            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"" + appiumUrl);
        } else {
            logger.error("Unsupported OS! Only support MacOS and WindowOS");
        }
        logger.info("Appium Server is started!");
    }

    public static void startAppiumLocalService() {
        //Build the Appium service
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(DEFAULT_APPIUM_SERVER);
        builder.usingPort(DEFAULT_APPIUM_PORT);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        builder.withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/");
//        builder.withTimeout(Duration.ofSeconds(300));
        builder.withArgument(() -> "--allow-insecure", "chromedriver_autodownload");

        //Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    public static void quitAppiumLocalService() {
        if (service != null) {
            service.stop();
        }
    }

    private static boolean checkIfServerIsRunning(String serverUrl, int serverPort, int timeout) throws InterruptedException, IOException {
        boolean isServerRunning = false;
        for (int i = 0; i <= timeout; i = i + 1000) {
            SocketAddress socketAddress = new InetSocketAddress(serverUrl, serverPort);
            try (Socket socket = new Socket()) {
                logger.info("Starting... serverUrl: {} , serverPort: {}", serverUrl, serverPort);
                socket.connect(socketAddress, 2000);
                socket.close();
                isServerRunning = true;
                logger.info("Appium server started successfully");
                break;
            } catch (SocketTimeoutException e) {
                logger.info("SocketTimeoutException: {}.{} - {}", serverUrl, serverPort, e.getMessage());
                Thread.sleep(1000);
            } catch (IOException e) {
                logger.info("IOException - Unable to connect to : {}.{} - {}", serverUrl, serverPort, e.getMessage());
                Thread.sleep(1000);
            }
        }
        return isServerRunning;
    }

    public static String getServerUrl() {
        return "http://" + DEFAULT_APPIUM_SERVER + ":" + DEFAULT_APPIUM_PORT + DEFAULT_APPIUM_SERVER_URI;
    }

}
