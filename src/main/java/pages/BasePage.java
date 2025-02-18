package pages;

import config.AppiumConfig;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected AndroidDriver driver;
    protected static WebDriverWait wait;

    public BasePage() {
        this.driver = AppiumConfig.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected static void waitForElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        System.out.println("found element " + element);
    }

    protected void clickElement(WebElement element) {
        waitForElement(element);
        element.click();
    }

    protected void sendKeysToElement(WebElement element, String text) {
        waitForElement(element);
        element.sendKeys(text);
    }

    protected WebElement findElement(By by) {
        return driver.findElement(by);
    }

    protected static boolean isElementPresent(AndroidDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}