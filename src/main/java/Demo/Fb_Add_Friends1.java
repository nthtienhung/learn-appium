package Demo;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import javax.management.RuntimeErrorException;

import static java.time.Duration.ofSeconds;

public class Fb_Add_Friends1 {
    private static AppiumDriverLocalService service;
    public static void startAppiumServer() {
        try {
            AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
            .withIPAddress("127.0.0.1")
            .usingPort(4723)
            .withLogFile(new java.io.File("logs/appium.log"));

            service = AppiumDriverLocalService.buildService(serviceBuilder);

            // if(service == null) {
            //     throw new RuntimeException("appium service could not be created");
            // }

            service.start();

            // if(service.isRunning()) {
            //     System.out.println("appium server started successfully on port " + service.getUrl());
            // }else {
            //     throw new RuntimeException("Appium server not running");
            // }

        } catch (Exception e) {
            System.err.println("error starting appium server: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        
        capabilities.setCapability("platformName", "Android"); 
        capabilities.setCapability("platformVersion", "14"); 
        capabilities.setCapability("deviceName", "M2101K7AG"); 
        capabilities.setCapability("automationName", "uiautomator2"); 
        capabilities.setCapability("appPackage", "com.facebook.katana"); 
        capabilities.setCapability("appActivity", "com.facebook.katana.activity.FbMainTabActivity"); 
        capabilities.setCapability("udid", "4ca23feb"); 
        capabilities.setCapability("noReset", true); 
        
        try {
            startAppiumServer();
            // URL url = URI.create("http://127.0.0.1:4723/wd/hub").toURL();

            AndroidDriver driver = new AndroidDriver(service.getUrl(), capabilities);

            System.out.println("Opening Facebook");

            //click friends button (too hard cannot do) 
            //alternative approach: swipe left
            // Get screen dimensions
            Dimension size = driver.manage().window().getSize();
            int screenWidth = size.getWidth();
            int screenHeight = size.getHeight();
            // Calculate swipe coordinates
            int startX = (int) (screenWidth * 0.9); // 10% from left
            int endX = (int) (screenWidth * 0.1);   // 90% from left
            int centerY = (int) (screenHeight * 0.8);   // Middle of screen
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

            while (!reachedEnd && scrollCount < 1000) { 
                try {
                    // Find and click buttons one at a time instead of storing a list
                    boolean foundButton = false;
                    while (true) {
                        try {
                            // Find single "Add Friend" button
                            WebElement button = driver.findElement(MobileBy.AndroidUIAutomator(
                                "new UiSelector().descriptionContains(\"Add\").descriptionContains(\"as a friend\").enabled(true)"
                            ));
                            
                            // Wait for element to be clickable
                            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                            wait.until(ExpectedConditions.elementToBeClickable(button));
                            
                            // Click the button
                            button.click();
                            addedFriends++;
                            System.out.println("Added friend #" + addedFriends);
                            Thread.sleep(500); // Wait between clicks
                            foundButton = true;
                        } catch (NoSuchElementException e) {
                            // No more buttons found on current screen
                            break;
                        } catch (ElementNotInteractableException e) {
                            System.out.println("Button not interactable, moving to next");
                            break;
                        }
                    }
            
                    // If no buttons were found, scroll down
                    if (!foundButton || addedFriends % 3 == 0) { // Scroll after every 3 friends or when no buttons found
                        boolean scrolled = driver.findElement(MobileBy.AndroidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
                        )) != null;
            
                        if (!scrolled) {
                            reachedEnd = true;
                            System.out.println("Reached end of scrollable content");
                        }
                        scrollCount++;
                        System.out.println("Scroll #" + scrollCount + " completed");
                        Thread.sleep(1000); // Wait after scroll
                    }
            
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