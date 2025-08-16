package utils;

import io.cucumber.java.*;
import org.openqa.selenium.*;
import pages.LoginPage;

public class Hooks {

    WebDriver driver;
    private static Cookie sessionCookie;

    private void initializeDriverAndHandleCookies() {
        String browser = System.getProperty("browser", "chrome");
        String testEnv = System.getProperty("testEnv", "test");

        driver = DriverFactory.initializeDriver(browser, testEnv);

        if(sessionCookie != null){
            driver.manage().addCookie(sessionCookie);
            driver.navigate().refresh();
        }
    }

    @Before(order = 1, value = "not @LoginRequired")
    public void beforeScenario() {
        initializeDriverAndHandleCookies();
    }

    @Before(order = 0, value = "@LoginRequired")
    public void beforeScenarioWithLogin() {
        initializeDriverAndHandleCookies();

        if(sessionCookie == null){
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(
                    ConfigReader.get("correct.tc.id"),
                    ConfigReader.get("correct.password")
            );

            for (Cookie c :driver.manage().getCookies()){
                if(c.getName().toLowerCase().contains("SAGLIK01272506")){
                    sessionCookie = c;
                    System.out.println("Session Cookie: " + c.getName()  + " = " + c.getValue());
                    break;
                }
            }
        }
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
