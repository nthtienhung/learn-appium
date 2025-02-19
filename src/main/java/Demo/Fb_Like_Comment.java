package Demo;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static java.time.Duration.ofSeconds;

public class Fb_Like_Comment {
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
        // capabilities.setCapability("fullReset", false);
        // capabilities.setCapability("autoGrantPermissions", true); // Auto-grant permissions
        // capabilities.setCapability("dontStopAppOnReset", true); // Keep app running
        
        try {
                    // Define Appium Server URL
            URL url = URI.create("http://127.0.0.1:4723/wd/hub").toURL();
            AndroidDriver driver = new AndroidDriver(url, capabilities);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            System.out.println("Opening Facebook");

            int scrollCount = 0;
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            while (true) {
                try {
                    driver.findElement(MobileBy.AndroidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(1);"
                    ));
                    System.out.println("Scrolled " + scrollCount + " times");

                    //press like
                    WebElement likeButton = driver.findElement(MobileBy.AndroidUIAutomator(
                        "new UiSelector().description(\"Like. Double tap and hold to react.\")"));
                    likeButton.click();

                    //click comment button
                    WebElement commentButton = driver.findElement(MobileBy.AndroidUIAutomator(
                        "new UiSelector().description(\"Comment\")"));
                    commentButton.click();

                    WebElement commentInput;

                    // Find and click the comment input field
                    //there are 2 type of selectors for inputting commment
                    //1 for empty post
                    //1 for post with existing comments
                    try{
                        commentInput = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                            MobileBy.AndroidUIAutomator("new UiSelector().text(\"Write a comment…\")")
                        ));
                    } catch (Exception e) {
                        commentInput = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                            MobileBy.AndroidUIAutomator("new UiSelector().text(\"Write a public comment…\")")
                        ));
                    }

                    commentInput.click();

                    // Clear existing text and type new comment
                    commentInput.clear();
                    commentInput.sendKeys("you do they they did you die.");

                    // click send comment, using AccessibilityId directly
                    WebElement sendButton = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Send")
                    ));
                    sendButton.click();

                    //go back
                    driver.navigate().back();
                    driver.navigate().back();


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