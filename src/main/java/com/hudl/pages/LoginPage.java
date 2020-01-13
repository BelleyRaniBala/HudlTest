package com.hudl.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

    public static final String LOGIN_PAGE_URL ="https://www.hudl.com/login" ;

    private static final By EMAIL = By.id("email");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN = By.id("logIn");
    private static final By LOGIN_ERROR_CONTAINER =By.xpath("//html/body/div[2]/form[1]/div[3]/div/p");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public HomePage login(String username, String password) {

        try{
            driver.findElement(EMAIL).sendKeys(username);
            driver.findElement(PASSWORD).sendKeys(password);
            driver.findElement(LOGIN).click();
            System.out.println("Element present");
        }
        catch(NoSuchElementException e){
            //Element is not present
            System.out.println("Element not present");
        }
        return new HomePage(driver);
    }


  public String getInvalidLoginMessage() {

      String InvalidLoginMessage = driver.findElement(LOGIN_ERROR_CONTAINER).getText();
      return InvalidLoginMessage;
  }
}

