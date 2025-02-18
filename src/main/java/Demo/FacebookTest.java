package Demo;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static java.time.Duration.ofSeconds;

public class FacebookTest {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        //real devices
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
        // Define Appium Server URL
        URL url = URI.create("http://127.0.0.1:4723/wd/hub").toURL();
        // Initialize Android Driver
        AndroidDriver driver = new AndroidDriver(url, capabilities);
        System.out.println("open facebook");


        Thread.sleep(5000);


        // WebElement btn_save = driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Save\"]/android.view.ViewGroup"));
        // btn_save.click();

        //dont use x in a real setting, meaningful name
        int x = 0;
        while (x < 10) {
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(1);"
            ));
            System.out.println("scroll " + x + " times");
            x++;
        }

    }
}
