package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverFactory {
    static WebDriver driver;

    public static WebDriver initializeDriver(String browser, String environment) {

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            options.addArguments(
                    "--disable-extensions",
                    "--disable-notifications",
                    "--incognito",
                    "--start-maximized",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--remote-allow-origins=*"
            );
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

            driver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();

        // Zaman aşımlarını config'den al
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(Math.max(15, ConfigReader.getInt("page.load.timeout"))));
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));

        // URL’ye güvenli şekilde git
        String baseUrl = ConfigReader.get("prod.url");
        safeNavigateTo(driver, baseUrl);

        return driver;
    }

    private static void safeNavigateTo(WebDriver driver, String url) {
        try {
            driver.get("about:blank");
            driver.navigate().to(url);
            waitForDomReady(driver, 12);
        } catch (TimeoutException e) {
            System.err.println("Initial navigation timed out, retrying...");
            driver.navigate().refresh();
            waitForDomReady(driver, 12);
        }
    }

    private static void waitForDomReady(WebDriver driver, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(d -> "complete".equals(
                        ((JavascriptExecutor) d)
                                .executeScript("return document.readyState")));
        try {
            Thread.sleep(250); // küçük stabilizasyon
        } catch (InterruptedException ignored) {
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
