package utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementHelper {
    WebDriver driver;
    WebDriverWait wait;
    Actions action;

    public ElementHelper(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
        this.action = new Actions(driver);
    }

    public void checkVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void checkClickable(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void checkClickable(By element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void click(Object elementLocator){
        WebElement element=null;
        if(elementLocator instanceof WebElement){
            checkClickable((WebElement) elementLocator);
            element=(WebElement) elementLocator;
        } else if (elementLocator instanceof  By) {
            checkClickable((By) elementLocator);
            element=driver.findElement((By)elementLocator);
        }
        assert element != null;
        element.click();
    }


    public void checkNotVisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}








