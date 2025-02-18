package config;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class AppiumConfig {
    private static AndroidDriver driver;

    public static AndroidDriver getDriver() {
        if (driver == null) {
            setupDriver();
        }
        return driver;
    }

    private static void setupDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

//        //for emulator
//        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("platformVersion", "13");
//        capabilities.setCapability("deviceName", "Medium Phone API 33");
//        capabilities.setCapability("automationName", "uiautomator2");
//        capabilities.setCapability("appPackage", "com.automattic.simplenote");
//        capabilities.setCapability("ignoreHiddenApiPolicyError", true);
//        capabilities.setCapability("appActivity", ".NotesActivity");

        //for real devices
        capabilities.setCapability("platformName", "Android"); // Platform name (case-sensitive)
        capabilities.setCapability("platformVersion", "14");  // Android version
        capabilities.setCapability("deviceName", "M2101K7AG"); // Emulator/device name
        capabilities.setCapability("automationName", "uiautomator2"); // Automation framework
        capabilities.setCapability("appPackage", "com.automattic.simplenote"); // Automation framework
        capabilities.setCapability("appActivity", ".NotesActivity"); // Automation framework
        capabilities.setCapability("udid", "4ca23feb"); // Automation framework


        try {
            URL url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AndroidDriver(url, capabilities);
        } catch (Exception e) {
            throw new RuntimeException("Could not create Appium session", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
