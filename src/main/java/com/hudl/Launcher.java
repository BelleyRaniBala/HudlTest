package com.hudl;

import com.hudl.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Launcher {


    public static ChromeDriver chromeDriver() {

        ChromeDriver driver = new ChromeDriver();
        return driver;

    }

    public static FirefoxDriver firefoxDriver() {

        FirefoxDriver driver = new FirefoxDriver();
        return driver;

    }

    public static LoginPage navigateToLoginPage(WebDriver driver) {
        driver.get(LoginPage.LOGIN_PAGE_URL);
        return new LoginPage(driver);
    }

}
