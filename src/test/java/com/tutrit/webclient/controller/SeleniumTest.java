package com.tutrit.webclient.controller;

import com.tutrit.webclient.WebClientApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {
    protected WebDriver driver;
    static {
        System.setProperty("webdriver.chrome.driver","chromedriver");
    }

    @BeforeAll
    public static void loadApp() {
//        WebClientApplication.main(new String[0]);
    }
    @BeforeEach
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
