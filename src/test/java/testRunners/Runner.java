package testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utils.DriverFactory;


@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"stepDefinitions", "utils"},
        plugin = {
                "summary", "pretty", "html:Reports/CucumberReport/Reports.html",
                "json:Reports/CucumberReport/Reports.json",
                "rerun:target/rerun.txt"
        },
        tags = "@SuccessfulLogin"
)
public class Runner extends AbstractTestNGCucumberTests {
}