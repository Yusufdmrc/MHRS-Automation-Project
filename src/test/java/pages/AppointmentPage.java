package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.ElementHelper;

import java.time.Duration;

public class AppointmentPage {

    WebDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;

    public AppointmentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
        this.elementHelper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
    }

    public void openAppointmentFlow() {
    }

    public void selectCity(String city) {
    }

    public void selectDistrictIfProvided(String district) {
    }

    public void selectClinic(String clinic) {
    }

    public void selectHospitalIfProvided(String hospital) {
    }

    public void selectExamPlaceIfProvided(String examPlace) {
    }

    public void selectDoctorIfProvided(String doctor) {
    }

    public void selectFirstAvailableSlotBetween(String startTime, String endTime) {
    }

    public void confirmAppointment() {
    }

    public void verifyConfirmation() {
    }
}
