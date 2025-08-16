package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigReader;
import utils.ElementHelper;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class AppointmentPage {

    WebDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;


    @FindBy(xpath = "//h3[normalize-space()='Hastane Randevusu Al']")
    WebElement appointmentButton;

    @FindBy(xpath = "//button[@class='ant-btn randevu-turu-button genel-arama-button ant-btn-lg']")
    WebElement generalSearchButton;

    @FindBy(xpath = "//span[contains(text(),'İl Seçiniz')]")
    WebElement cityInput;

    @FindBy(xpath = "//span[contains(text(),'İlçe Seçiniz')]")
    WebElement districtInput;

    @FindBy(xpath = "//span[contains(text(),'Klinik Seçiniz')]")
    WebElement clinicInput;

    @FindBy(xpath = "//span[contains(text(),'Hastane Seçiniz')]")
    WebElement hospitalInput;

    @FindBy(xpath = "//span[contains(text(),'Muayene Yeri Seçiniz')]")
    WebElement examPlaceInput;

    @FindBy(xpath = "//span[contains(text(),'Hekim Seçiniz')]")
    WebElement doctorInput;

    @FindBy(id = "randevuAramaForm_baslangicZamani")
    WebElement startTimeInput;

    @FindBy(id = "randevuAramaForm_bitisZamani")
    WebElement endTimeInput;

    @FindBy(css = ".available-slots button")
    List<WebElement> availableSlots;

    @FindBy(xpath = "//button[contains(.,'Randevu Ara')]")
    WebElement searchAppointmentButton;

    @FindBy(xpath = "//button[contains(@data-testid,'confirm-appointment')]")
    WebElement confirmButton;

    @FindBy(css = ".confirmation-message")
    WebElement confirmationMessage;

    // ==== CONSTRUCTOR ====
    public AppointmentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
        this.elementHelper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
    }

    // ==== GENEL DROPDOWN SEÇME METODU (Türkçe karakter destekli) ====
    private void selectFromDropdown(WebElement dropdownTrigger, String optionText) {
        if (optionText == null || optionText.trim().isEmpty() || optionText.equalsIgnoreCase("SKIP")) {
            return; // Opsiyonel alan boş bırakılmış
        }

        elementHelper.click(dropdownTrigger);

        // Açılan dropdown container'ı bekle
        By visibleDropdown = By.xpath("//div[contains(@class,'ant-select-dropdown') and not(contains(@style,'display: none'))]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(visibleDropdown));

        // TR locale ile büyük harfe çevir
        String normalized = optionText.trim().toUpperCase(new Locale("tr", "TR"));

        // İstenen seçeneği bekle ve tıkla
        By optionLocator = By.xpath(
                "//div[contains(@class,'ant-select-dropdown') and not(contains(@style,'display: none'))]" +
                        "//li[normalize-space()='" + normalized + "']"
        );

        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        optionElement.click();
    }

    // ==== ADIM METOTLARI ====
    public void openAppointmentFlow() {
        elementHelper.click(appointmentButton);
        elementHelper.click(generalSearchButton);
    }

    public void selectCity(String city) {
        selectFromDropdown(cityInput, city); // Zorunlu alan
    }

    public void selectDistrictIfProvided(String district) {
        selectFromDropdown(districtInput, district); // Opsiyonel
    }

    public void selectClinic(String clinic) {
        selectFromDropdown(clinicInput, clinic); // Zorunlu
    }

    public void selectHospitalIfProvided(String hospital) {
        selectFromDropdown(hospitalInput, hospital); // Opsiyonel
    }

    public void selectExamPlaceIfProvided(String examPlace) {
        selectFromDropdown(examPlaceInput, examPlace); // Opsiyonel
    }

    public void selectDoctorIfProvided(String doctor) {
        selectFromDropdown(doctorInput, doctor); // Opsiyonel
    }

    public void selectFirstAvailableSlotBetween(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);

        for (WebElement slot : availableSlots) {
            String slotTime = slot.getText().trim();
            LocalTime currentSlotTime = LocalTime.parse(slotTime, formatter);

            if ((currentSlotTime.equals(start) || currentSlotTime.isAfter(start))
                    && (currentSlotTime.equals(end) || currentSlotTime.isBefore(end))) {
                elementHelper.click(slot);
                break;
            }
        }
    }

    public void searchAppointments() {
        elementHelper.click(searchAppointmentButton);
    }

    public void confirmAppointment() {
        elementHelper.click(confirmButton);
    }

    public void verifyConfirmation() {
        elementHelper.checkVisible(confirmationMessage);
        Assert.assertTrue(confirmationMessage.isDisplayed(), "Randevu onayı görüntülenemedi");
    }
}