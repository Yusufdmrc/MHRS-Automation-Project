package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.AppointmentPage;
import utils.DriverFactory;

public class AppointmentStepDefinitions {
    WebDriver driver= DriverFactory.getDriver();
    AppointmentPage appointmentPage=new AppointmentPage(driver);

    @When("User opens appointment flow")
    public void userOpensAppointmentFlow() {
        appointmentPage.openAppointmentFlow();
    }

    @And("Select city {string}")
    public void selectCity(String city) {
        appointmentPage.selectCity(city);
    }

    @And("Optionally select district {string}")
    public void optionallySelectDistrict(String district) {
        appointmentPage.selectDistrictIfProvided(district);
    }

    @And("Select clinic {string}")
    public void selectClinic(String clinic) {
        appointmentPage.selectClinic(clinic);
    }

    @And("Optionally select hospital {string}")
    public void optionallySelectHospital(String hospital) {
        appointmentPage.selectHospitalIfProvided(hospital);
    }

    @And("Optionally select exam place {string}")
    public void optionallySelectExamPlace(String examPlace) {
        appointmentPage.selectExamPlaceIfProvided(examPlace);
    }

    @And("Optionally select doctor {string}")
    public void selectDoctor(String doctor) {
        appointmentPage.selectDoctorIfProvided(doctor);
    }

    @And("Select a slot between {string} and {string}")
    public void selectASlotBetweenAnd(String startTime, String endTime) {
        appointmentPage.selectFirstAvailableSlotBetween(startTime,endTime);
    }

    @And("Confirm appointment")
    public void confirmAppointment() {
        appointmentPage.confirmAppointment();
    }

    @Then("Verify appointment confirmation")
    public void verifyAppointmentConfirmation() {
        appointmentPage.verifyConfirmation();
    }
}
