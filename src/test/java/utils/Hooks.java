package utils;

import io.cucumber.java.*;
import org.openqa.selenium.*;
import pages.LoginPage;

public class Hooks {

    WebDriver driver;

    private void initializeDriverAndHandleCookies() {
        String browser = System.getProperty("browser", "chrome");
        String testEnv = System.getProperty("testEnv", "test");

        driver = DriverFactory.initializeDriver(browser, testEnv);
    }

    @Before(order = 1, value = "not @LoginRequired")
    public void beforeScenario() {
        initializeDriverAndHandleCookies();
    }

    @Before(order = 0, value = "@LoginRequired")
    public void beforeScenarioWithLogin() {
        initializeDriverAndHandleCookies();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                ConfigReader.get("correct.tc.id"),
                ConfigReader.get("correct.password")
        );
    }

    @AfterStep
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
