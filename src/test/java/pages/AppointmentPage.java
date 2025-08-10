package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;
import utils.ElementHelper;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentPage {

    WebDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;

    @FindBy(xpath = "//h3[normalize-space()='Hastane Randevusu Al']")
    WebElement appointmentButton;

    @FindBy(xpath = "//button[@class='ant-btn randevu-turu-button genel-arama-button ant-btn-lg']")
    WebElement generalSearchButton;

    @FindBy(xpath = "//span[text()='İl Seçiniz']")
    WebElement cityInput;

    @FindBy(xpath = "//div[text()='-FARK ETMEZ-']")
    WebElement districtInput;

    @FindBy(xpath = "//span[text()='Klinik Seçiniz']")
    WebElement clinicInput;

    @FindBy(css = "span[id='hastane-tree-select'] span[class='ant-select-selection__placeholder']")
    WebElement hospitalInput;

    @FindBy(css = "span[id='muayeneYeri-tree-select'] span[class='ant-select-selection__placeholder']")
    WebElement examPlaceInput;

    @FindBy(css = "span[id='hekim-tree-select'] span[class='ant-select-selection__placeholder']")
    WebElement doctorInput;

    @FindBy(id ="randevuAramaForm_baslangicZamani")
    WebElement startTimeInput;

    @FindBy(id="randevuAramaForm_bitisZamani")
    WebElement endTimeInput;

    @FindBy(css = ".available-slots button")
    List<WebElement> availableSlots;

    @FindBy(css = "button[data-testid='confirm-appointment']")
    WebElement confirmButton;

    @FindBy(css = ".confirmation-message")
    WebElement confirmationMessage;

    public AppointmentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
        this.elementHelper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
    }

    public void openAppointmentFlow() {
        elementHelper.click(appointmentButton);
    }

    public void selectCity(String city) {
        if (!"SKIP".equals(city)) {
            elementHelper.click(cityInput);
            WebElement cityOption = driver.findElement(By.xpath("//li[text()='" + city + "']"));
            elementHelper.click(cityOption);
        }
    }

    public void selectDistrictIfProvided(String district) {
        if (!"SKIP".equals(district)) {
            elementHelper.click(districtInput);
            WebElement districtOption = driver.findElement(By.xpath("//li[text()='" + district + "']"));
            elementHelper.click(districtOption);
        }
    }

    public void selectClinic(String clinic) {
        elementHelper.click(clinicInput);
        WebElement clinicOption = driver.findElement(By.xpath("//li[text()='" + clinic + "']"));
        elementHelper.click(clinicOption);
    }

    public void selectHospitalIfProvided(String hospital) {
        if (!"SKIP".equals(hospital)) {
            elementHelper.click(hospitalInput);
            WebElement hospitalOption = driver.findElement(By.xpath("//li[text()='" + hospital + "']"));
            elementHelper.click(hospitalOption);
        }
    }

    public void selectExamPlaceIfProvided(String examPlace) {
        if (!"SKIP".equals(examPlace)) {
            elementHelper.click(examPlaceInput);
            WebElement examPlaceOption = driver.findElement(By.xpath("//li[text()='" + examPlace + "']"));
            elementHelper.click(examPlaceOption);
        }
    }

    public void selectDoctorIfProvided(String doctor) {
        if (!"SKIP".equals(doctor)) {
            elementHelper.click(doctorInput);
            WebElement doctorOption = driver.findElement(By.xpath("//li[text()='" + doctor + "']"));
            elementHelper.click(doctorOption);
        }
    }

    public void selectFirstAvailableSlotBetween(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);

        for (WebElement slot : availableSlots) {
            String slotTime = slot.getText();
            LocalTime currentSlotTime = LocalTime.parse(slotTime, formatter);

            if (currentSlotTime.isAfter(start) && currentSlotTime.isBefore(end)) {
                elementHelper.click(slot);
                break;
            }
        }
    }

    public void confirmAppointment() {
        elementHelper.click(confirmButton);
    }

    public void verifyConfirmation() {
        elementHelper.checkVisible(confirmationMessage);
        Assert.assertTrue(confirmationMessage.isDisplayed(), "Randevu onayı görüntülenemedi");
    }
}