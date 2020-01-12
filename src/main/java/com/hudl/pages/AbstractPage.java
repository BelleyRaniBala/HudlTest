package com.hudl.pages;

import org.openqa.selenium.WebDriver;

public class AbstractPage {

    protected final WebDriver driver;

    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public void quit() {
        driver.quit();
    }

}
