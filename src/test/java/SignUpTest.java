import config.AppiumConfig;
import pages.SignupPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class SignUpTest {
    private SignupPage signupPage;

    @BeforeMethod
    public void setup() {
        signupPage = new SignupPage();
    }

    @Test
    public void testSignup() throws InterruptedException {
        String email = ConfigReader.getProperty("email");
        String password = ConfigReader.getProperty("password");

        signupPage.completeSignup(email, password);
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() {
        AppiumConfig.quitDriver();
    }
}