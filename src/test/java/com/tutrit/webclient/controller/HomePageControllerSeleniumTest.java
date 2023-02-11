package com.tutrit.webclient.controller;

import com.tutrit.webclient.bean.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.mockito.Mockito.when;

@SpringBootTest
class HomePageControllerSeleniumTest extends SeleniumTest {

    /**
     * @see <a href="https://www.selenium.dev/documentation/webdriver/waits/">selenium documentation</a>
     */
//    @Test
    void openHomePage() {
        driver.get("http://localhost/");
        // Waiting 30 seconds for an element to be present on the page, checking
        // for its presence once every 5 seconds.
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(0))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        WebElement userData = wait.until(driver -> driver.findElement(By.id("user-data")));
        Assertions.assertEquals("null null", userData.getText());
    }

//    @Test
    void saveEngineer() {
    }

//    @Test
    void testOpenHomePage() {
    }

    private User makeUser() {
        return new User("Mikas", 27);
    }
}