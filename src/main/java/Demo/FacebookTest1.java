package Demo;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static java.time.Duration.ofSeconds;

public class FacebookTest1 {
    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        
        // ...existing code...
        
        capabilities.setCapability("platformName", "Android"); // Platform name (case-sensitive)
        capabilities.setCapability("platformVersion", "14");  // Android version
        capabilities.setCapability("deviceName", "M2101K7AG"); // Emulator/device name
        capabilities.setCapability("automationName", "uiautomator2"); // Automation framework
        capabilities.setCapability("appPackage", "com.facebook.katana"); // Automation framework
        capabilities.setCapability("appActivity", "com.facebook.katana.activity.FbMainTabActivity"); // Automation framework
        capabilities.setCapability("udid", "4ca23feb"); // Automation framework

        capabilities.setCapability("noReset", true); // Preserve app state
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("autoGrantPermissions", true); // Auto-grant permissions
        capabilities.setCapability("dontStopAppOnReset", true); // Keep app running
        
        // ...existing code...
        
        try {
                    // Define Appium Server URL
            URL url = URI.create("http://127.0.0.1:4723/wd/hub").toURL();
            AndroidDriver driver = new AndroidDriver(url, capabilities);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            System.out.println("Opening Facebook");

            // // Wait for app to load and stabilize
            // wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Button[@content-desc=\"Save\"]")));
            
            // // Click save login if present
            // try {
            //     WebElement saveButton = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Save\"]"));
            //     if (saveButton.isDisplayed()) {
            //         saveButton.click();
            //         System.out.println("Clicked save login button");
            //     }
            // } catch (Exception e) {
            //     System.out.println("Save login button not found or not needed");
            // }

            Thread.sleep(5000);

            // Scroll with proper error handling
            int scrollCount = 0;
            while (scrollCount < 10) {
                try {
                    driver.findElement(MobileBy.AndroidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(1);"
                    ));
                    System.out.println("Scrolled " + scrollCount + " times");
                    // Add small delay between scrolls
                    Thread.sleep(1000);
                    scrollCount++;
                } catch (Exception e) {
                    System.out.println("Scroll failed: " + e.getMessage());
                    // Attempt to recover app state if needed
                    driver.activateApp("com.facebook.katana");
                }
            }

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}