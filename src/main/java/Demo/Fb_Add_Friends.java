package Demo;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static java.time.Duration.ofSeconds;

public class Fb_Add_Friends {
    private static AppiumDriverLocalService service;

    private static void startAppiumServer() {
        try {
            // Create logs directory if it doesn't exist
            java.io.File logDir = new java.io.File("logs");
            if (!logDir.exists()) {
                logDir.mkdir();
            }

            // Set NodeJS and Appium main.js path
            String nodeJSPath = "C:\\Program Files\\nodejs\\node.exe";  // Adjust this path
            String appiumJSPath = "C:\\Users\\hungnt2\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"; // Adjust this path

            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .withLogFile(new java.io.File("logs/appium.log"))
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info");

            // If you need to specify Node.js and Appium paths explicitly
            if (new java.io.File(nodeJSPath).exists()) {
                builder.usingDriverExecutable(new java.io.File(nodeJSPath));
            }
            if (new java.io.File(appiumJSPath).exists()) {
                builder.withAppiumJS(new java.io.File(appiumJSPath));
            }

            service = AppiumDriverLocalService.buildService(builder);
            
            if (service == null) {
                throw new RuntimeException("Appium service could not be created");
            }

            service.start();

            if (service.isRunning()) {
                System.out.println("Appium server started successfully on port 4723");
            } else {
                throw new RuntimeException("Appium server not running");
            }
        } catch (Exception e) {
            System.err.println("Error starting Appium server: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void stopAppiumServer() {
        if (service != null) {
            service.stop();
            System.out.println("Appium server stopped");
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        try {
            startAppiumServer();
            
            DesiredCapabilities capabilities = new DesiredCapabilities();
            
            capabilities.setCapability("platformName", "Android"); 
            capabilities.setCapability("platformVersion", "14"); 
            capabilities.setCapability("deviceName", "M2101K7AG"); 
            capabilities.setCapability("automationName", "uiautomator2"); 
            capabilities.setCapability("appPackage", "com.facebook.katana"); 
            capabilities.setCapability("appActivity", "com.facebook.katana.activity.FbMainTabActivity"); 
            capabilities.setCapability("udid", "4ca23feb"); 
            capabilities.setCapability("noReset", true); 
            
            // Modified URL to use service URL
            AndroidDriver driver = new AndroidDriver(service.getUrl(), capabilities);
            System.out.println("Opening Facebook");

            //click friends button (too hard cannot do) 
            //alternative approach: swipe left
            // Get screen dimensions
            Dimension size = driver.manage().window().getSize();
            int screenWidth = size.getWidth();
            int screenHeight = size.getHeight();
            // Calculate swipe coordinates
            int startX = (int) (screenWidth * 0.9); // 90% from left
            int endX = (int) (screenWidth * 0.1);   // 10% from left
            int centerY = (int) (screenHeight * 0.8); // 80% from top

            // Swipe using W3C Actions directly
            Map<String, Object> swipeObject = new HashMap<>();
            swipeObject.put("direction", "left");
            swipeObject.put("percent", 0.75);
            driver.executeScript("mobile: swipe", swipeObject);

            // Add wait after swipe
            Thread.sleep(1000);

            int scrollCount = 0;
            int addedFriends = 0;
            boolean reachedEnd = false;

            while (!reachedEnd && scrollCount < 50) { // Limit to 50 scrolls for safety
                try {
                    // Find all "Add Friend" buttons on current screen
                    String uiAutomatorString = "new UiSelector().descriptionContains(\"Add\").descriptionContains(\"as a friend\")";
                    List<WebElement> addButtons = driver.findElements(AppiumBy.androidUIAutomator(uiAutomatorString));
                    
                    // Click each "Add Friend" button found
                    //problem: it tried to click the previous add friend button, instead of clicking the next one
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

                    // Scroll down using UiAutomator2
                    boolean scrolled = driver.findElement(AppiumBy.androidUIAutomator(
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
        } finally {
            stopAppiumServer();
        }
    }
}