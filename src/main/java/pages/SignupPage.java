package pages;

import io.appium.java_client.android.Activity;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignupPage extends BasePage {
    @FindBy(xpath = "//android.widget.Button[@resource-id=\"com.automattic.simplenote:id/button_signup\"]")
    private WebElement btn_signup;

    @FindBy(xpath = "//android.widget.EditText[@resource-id=\"com.automattic.simplenote:id/email_edit_text\"]")
    private WebElement input_email;

    @FindBy(xpath = "//android.widget.Button[@resource-id=\"com.automattic.simplenote:id/button\"]")
    private WebElement btn_email_signup;

    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"com.google.android.gm:id/subject\" and @text=\"Create your Simplenote Account\"]")
    private WebElement btn_received_mail;

    @FindBy(xpath = "//android.widget.TextView[@text=\"https://app.simplenote.com/acc\"]")
    private WebElement link_complete_signup;

    @FindBy(xpath = "//android.widget.EditText[@resource-id=\"password\"]")
    private WebElement input_password;

    @FindBy(xpath = "//android.widget.Button[@text=\"Sign up\"]")
    private WebElement btn_create_acc_signup;

    public void clickSignupButton(WebElement element){
        waitForElement(element);
        clickElement(element);
    }
    public void enterEmail(WebElement element, String text) {
        waitForElement(element);
        sendKeysToElement(element, text);
    }
    public void clickButtonEmailSignup(WebElement element) {
        waitForElement(element);
        clickElement(element);
    }
    public void clickButtonReceivedMail (WebElement element) {
        waitForElement(element);
        clickElement(element);
    }
    public void clickLinkCompleteSignup (WebElement element) {
        waitForElement(element);
        clickElement(element);
    }

    public void enterPassword (WebElement element, String text) {
        waitForElement(element);
        sendKeysToElement(input_password, text);
    }
    public void clickButtonCreateAccSignup (WebElement element) {
        clickElement(element);
    }

    public void completeSignup(String email, String password) throws InterruptedException {
        clickSignupButton(btn_signup);
        enterEmail(input_email, email);
        clickButtonEmailSignup(btn_email_signup);

        //Switch to second app: gmail(get otp)
        Activity secondApp = new Activity("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");
        driver.startActivity(secondApp);
        Thread.sleep(3000); // Wait for the second app to launch
        System.out.println("Open to Gmail");

        //wait till received mail came
        Thread.sleep(5000);

        clickButtonReceivedMail(btn_received_mail);
        clickLinkCompleteSignup(link_complete_signup);
        enterPassword(input_password, password);
        clickButtonCreateAccSignup(btn_create_acc_signup);
    }
}
