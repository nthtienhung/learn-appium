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

public class YoutubeTest {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        //real devices
        capabilities.setCapability("platformName", "Android"); // Platform name (case-sensitive)
        capabilities.setCapability("platformVersion", "14");  // Android version
        capabilities.setCapability("deviceName", "M2101K7AG"); // Emulator/device name
        capabilities.setCapability("automationName", "uiautomator2"); // Automation framework
        capabilities.setCapability("appPackage", "app.revanced.android.youtube"); // Automation framework
        capabilities.setCapability("appActivity", "com.google.android.youtube.app.honeycomb.Shell$HomeActivity"); // Automation framework
        capabilities.setCapability("udid", "4ca23feb"); // Automation framework


        // Define Appium Server URL
        URL url = URI.create("http://127.0.0.1:4723/wd/hub").toURL();
        // Initialize Android Driver
        AndroidDriver driver = new AndroidDriver(url, capabilities);
        System.out.println("open youtube");

        Thread.sleep(2000);

        WebElement btn_allow_permission = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]"));
        btn_allow_permission.click();

        Thread.sleep(2000);
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
