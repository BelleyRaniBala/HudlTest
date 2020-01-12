package com.hudl.pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage{
    public HomePage(WebDriver driver) {
        super(driver);
    }


    public String getTitle() {
        return driver.getTitle();
    }

    public String getURL () {
        String expected =  driver.getCurrentUrl();
        System.out.println(expected);
        return expected;
    }
}
