package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;
import utils.ElementHelper;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    ElementHelper elementHelper;
    WebDriverWait wait;

    @FindBy(id = "LoginForm_username")
    WebElement usernameBox;

    @FindBy(id = "LoginForm_password")
    WebElement passwordBox;

    @FindBy(css = "button.ant-btn.ant-btn-teal.ant-btn-block")
    WebElement loginButton;

    @FindBy(css = "button[class='ant-btn']")
    WebElement closeButton;

    public LoginPage(WebDriver driver){
        this.driver= driver;
        this.wait= new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
        this.elementHelper= new ElementHelper(driver);
        PageFactory.initElements(driver,this);
    }


    public void isLoginPageDisplayed() {
        String expectedUrl = ConfigReader.get("prod.url");
        String actualUrl= driver.getCurrentUrl();
        Assert.assertEquals(actualUrl,expectedUrl);
    }

    public void writeForUsernameField(String username) {
       usernameBox.clear();
       usernameBox.sendKeys(ConfigReader.get("correct.tc.id"));

    }

    public void writeForPasswordField(String password) {
        passwordBox.clear();
        passwordBox.sendKeys(ConfigReader.get("correct.password"));
    }

    public void clickLoginButton() {
        elementHelper.click(loginButton);
    }

    public void checkSuccessfulLogin() {
        elementHelper.checkVisible(closeButton);
        elementHelper.click(closeButton);
    }
    public void login(String username,String password){
        isLoginPageDisplayed();
        writeForUsernameField(username);
        writeForPasswordField(password);
        clickLoginButton();
        checkSuccessfulLogin();
    }
}
