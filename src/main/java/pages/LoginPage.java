package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//android.widget.Button[@resource-id=\"com.automattic.simplenote:id/button_login\"]")
    private WebElement btn_login;

    @FindBy(xpath = "//android.widget.EditText[@resource-id=\"com.automattic.simplenote:id/email_edit_text\"]")
    private WebElement input_email;

    @FindBy(xpath = "//android.widget.Button[@resource-id=\"com.automattic.simplenote:id/button\"]")
    private WebElement btn_login_with_email;

    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"com.google.android.gm:id/subject\" and @text=\"Log in to Simplenote\"]")
    private WebElement btn_received_mail;

    @FindBy(xpath = "//android.view.View[@content-desc=\"Log in to Simplenote\"]")
    private WebElement btn_received_mail_login;

    @FindBy(xpath = "//android.widget.EditText[@text=\"Password\"]")
    private WebElement input_password;

    @FindBy(xpath = "//android.widget.Button[@resource-id=\"com.automattic.simplenote:id/button\"]")
    private WebElement btn_login_with_password;

//    @FindBy(xpath = "//android.widget.TextView[@text=\"Enter Code\"]")
//    private WebElement conditionForOtp;
//
//    @FindBy(xpath= "//android.widget.TextView[@resource-id=\"com.automattic.simplenote:id/login_with_password_email_message\"]")
//    private WebElement conditionForPasswordRequired;


    private By conditionForOtp = By.xpath("//android.widget.EditText[@resource-id=\"com.automattic.simplenote:id/confirmation_code_textfield\"]");
    private By conditionForPasswordRequired = By.xpath("//android.widget.TextView[@resource-id=\"com.automattic.simplenote:id/login_with_password_email_message\"]");

    public void clickLoginButton(WebElement element){
        waitForElement(element);
        clickElement(element);
    }
    public void enterEmail(WebElement element, String text) {
        waitForElement(element);
        sendKeysToElement(element, text);
    }
    public void clickLoginWithEmailButton(WebElement element) {
        waitForElement(element);
        clickElement(element);
    }
    public void clickButtonReceivedMail (WebElement element) {
        waitForElement(element);
        clickElement(element);
    }
    public void clickReceivedMailLogin (WebElement element) {
        waitForElement(element);
        clickElement(element);
    }

    public void enterPassword (WebElement element, String text) {
        waitForElement(element);
        sendKeysToElement(input_password, text);
    }
    public void clickButtonLoginWithPassword (WebElement element) {
        clickElement(element);
    }

    public void completeLogin(String email, String password) throws InterruptedException {
        clickLoginButton(btn_login);
        enterEmail(input_email, email);
        clickLoginWithEmailButton(btn_login_with_email); //step 3

        Thread.sleep(4000);

        if(isElementPresent(driver, conditionForOtp)){
            System.out.println("Enter case: Find received mail to log in");
            Activity secondApp = new Activity("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");
            driver.startActivity(secondApp);
            Thread.sleep(3000); // Wait for the second app to launch
            System.out.println("Open gmail");

            //wait till received mail came
            Thread.sleep(5000);

            clickButtonReceivedMail(btn_received_mail);

            Thread.sleep(3000);

            // Scroll to the bottom of the list first
//            driver.findElement(MobileBy.AndroidUIAutomator(
//                    "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(2);"
//            ));


//            // After scrolling, find all matching elements and click the last one
//            List<WebElement> emails = driver.findElements(By.xpath(
//                    "//android.widget.TextView[@text=\"Log in to Simplenote\"]"
//            ));
//
//            Thread.sleep(2000);
//
//            if (!emails.isEmpty()) {
//                WebElement lastEmail = emails.get(emails.size() - 1);
//                clickElement(lastEmail);
//            } else {
//                throw new NoSuchElementException("No login emails found");
//            }

            clickReceivedMailLogin(btn_received_mail_login);

        }else if(isElementPresent(driver, conditionForPasswordRequired)) {
            System.out.println("Enter case: Requires password to login");
            enterPassword(input_password, password);
            clickButtonLoginWithPassword(btn_login_with_password);

        }
        Thread.sleep(5000);
    }
}
