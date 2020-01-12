import com.hudl.Launcher;
import com.hudl.pages.HomePage;
import com.hudl.pages.LoginPage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoginTest {


    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeEach
    public void startWeb() {
        loginPage = Launcher.navigateToLoginPage(Launcher.chromeDriver());
    }

    @DisplayName("Should be able to login with valid credential")
    @Test
    public void successfulLogin() throws InterruptedException {

        homePage = loginPage.login("balabelley@gmail.com", "Belley1985");

        Thread.sleep(2000);
        Assert.assertEquals( "Home - Hudl",homePage.getTitle());

        String expectedUrl = "https://www.hudl.com/home";
        if (homePage.getURL().equalsIgnoreCase(expectedUrl)) {
            System.out.println(" Successfully logged in with valid credential");
        } else {
            System.out.println("Unable to login with valid credential. Test failed");
        }
    }

    @DisplayName("Should Not be able to login with invalid credential")
    @ParameterizedTest(name = "Login with username \"{0}\" and password \"{1}\"")
    @CsvSource({"-1@gmail.com, 123456", "a, Qwerty122", "hello@yahoo.co.uk,-1"})
    void loginWithIncorrectCredential(String username, String password) throws InterruptedException {

        homePage = loginPage.login(username, password);
        Thread.sleep(2000);
        Assert.assertEquals("We didn't recognize that email and/or password. Need help?",loginPage.getInvalidLoginMessage());

    }

    @AfterEach
    void closeWeb() {
        homePage.quit();
    }
}


