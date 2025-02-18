import config.AppiumConfig;
import pages.LoginPage;
import pages.SignupPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class LogInTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage();
    }

    @Test
    public void testLogin() throws InterruptedException {
        String email = ConfigReader.getProperty("email");
        String password = ConfigReader.getProperty("password");

        loginPage.completeLogin(email, password);
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() {
        AppiumConfig.quitDriver();
    }
}