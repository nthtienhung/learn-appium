package Demo;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static java.time.Duration.ofSeconds;

public class Fb_Add_Friends {
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
            URL url = URI.create("http://127.0.0.1:4723/wd/hub").toURL();
            AndroidDriver driver = new AndroidDriver(url, capabilities);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            System.out.println("Opening Facebook");

            //click friends button (too hard cannot do) 
            //alternative approach: swipe right
            // Get screen dimensions
            Dimension size = driver.manage().window().getSize();
            int screenWidth = size.getWidth();
            int screenHeight = size.getHeight();

            // Calculate swipe coordinates
            int startX = (int) (screenWidth * 0.9); // 10% from left
            int endX = (int) (screenWidth * 0.1);   // 90% from left
            int centerY = (int) (screenHeight * 0.8);         // Middle of screen

            // Create TouchAction and perform swipe
            new TouchAction(driver)
                .press(PointOption.point(startX, centerY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(endX, centerY))
                .release()
                .perform();

            // Add wait after swipe
            Thread.sleep(1000);

            int scrollCount = 0;
            int addedFriends = 0;
            boolean reachedEnd = false;

            while (!reachedEnd && scrollCount < 50) { // Limit to 50 scrolls for safety
                try {
                    // Find all "Add Friend" buttons on current screen
                    String uiAutomatorString = "new UiSelector().descriptionContains(\"Add\").descriptionContains(\"as a friend\")";
                    java.util.List<WebElement> addButtons = driver.findElements(MobileBy.AndroidUIAutomator(uiAutomatorString));
                    
                    // Click each "Add Friend" button found
                    for (WebElement button : addButtons) {
                        try {
                            button.click();
                            addedFriends++;
                            System.out.println("Added friend #" + addedFriends);
                            Thread.sleep(1000); // Wait between clicks
                        } catch (ElementNotInteractableException e) {
                            System.out.println("Couldn't click button: " + e.getMessage());
                        }
                    }

                    // Scroll down
                    boolean scrolled = driver.findElement(MobileBy.AndroidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(1)"
                    )) != null;

                    if (!scrolled) {
                        reachedEnd = true;
                        System.out.println("Reached end of scrollable content");
                    }

                    scrollCount++;
                    System.out.println("Scroll #" + scrollCount + " completed");

                } catch (Exception e) {
                    System.out.println("Error during scroll/add: " + e.getMessage());
                    break;
                }
            }

            System.out.println("Total friends added: " + addedFriends);

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}