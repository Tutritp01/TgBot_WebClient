package com.tutrit.webclient.controller;

import com.tutrit.webclient.WebClientApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

/**
 * public access modifier is important to Cucumber find Steps Definition
 */
@SpringBootTest
public class HomePageControllerSeleniumCucumberTest {

    WebDriver driver;

    static {
        System.setProperty("webdriver.chrome.driver","chromedriver");
    }

    /**
     * @see <a href="https://cucumber.io/docs/guides/10-minute-tutorial/?lang=java">Cucumber documentation</a>
     */
    @Given("EngineerGateway has no connection to rest service")
    public void engineer_gateway_has_no_connection_to_rest_service() {
        loadApp();
        setUp();
    }

    @When("User opens home page")
    public void user_opens_home_page() {
        driver.get("http://localhost/");
    }

    @Then("He should see {string} in the user data element")
    public void he_should_see_in_the_user_data_element(String string) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(0))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        WebElement userData = wait.until(driver -> driver.findElement(By.id("user-data")));
        Assertions.assertEquals("null null", userData.getText());

        tearDown();
    }

    public void loadApp() {
//        WebClientApplication.main(new String[0]);
    }

    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    public void tearDown() {
        driver.close();
        driver.quit();
    }
}