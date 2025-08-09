package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverFactory;

public class LoginStepDefinitions {

    WebDriver driver = DriverFactory.getDriver();
    LoginPage loginPage = new LoginPage(driver);

    @Given("User at login page")
    public void userAtLoginPage() {
        loginPage.isLoginPageDisplayed();
    }

    @When("write {string} for username field")
    public void writeForUsernameField(String username) {
        loginPage.writeForUsernameField(username);

    }

    @And("write {string} for password field")
    public void writeForPasswordField(String password) {
        loginPage.writeForPasswordField(password);
    }

    @And("Click login button")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("Check Successful login")
    public void checkSuccessfulLogin() {
        loginPage.checkSuccessfulLogin();
    }
}
