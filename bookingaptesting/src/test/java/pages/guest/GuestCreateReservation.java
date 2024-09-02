package pages.guest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GuestCreateReservation {
    private WebDriver webdriver;

    public GuestCreateReservation(WebDriver webdriver) {
        this.webdriver = webdriver;
        PageFactory.initElements(webdriver, this);
    }

    @FindBy(how = How.CSS, using = "#availabilityStart")
	private WebElement inputStartDate;

    @FindBy(how = How.CSS, using = "#availabilityEnd")
	private WebElement inputEndDate;

    @FindBy(how = How.CSS, using = "#guestNumber")
	private WebElement inputGuestNumber;

    public void btnAcommodationDetails_click() {
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.elementToBeClickable(webdriver.findElement(By.xpath("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[3]//button[normalize-space(text())='More']")))).click();
	}

    public void enterFields(String availabilityStart, String availabilityEnd, int guestNumber) {
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(inputStartDate)).clear();
        selectDate(inputStartDate, availabilityStart);
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(inputEndDate)).clear();
        selectDate(inputEndDate, availabilityEnd);
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(inputGuestNumber)).clear();
		inputGuestNumber.sendKeys(String.valueOf(guestNumber));
    }

    public void selectDate(WebElement datepicker, String date) {
        datepicker.click();
		datepicker.sendKeys(date.split("-")[0]);
		datepicker.sendKeys(date.split("-")[1]);
		datepicker.sendKeys(Keys.TAB);
		datepicker.sendKeys(date.split("-")[2]);
    }

    public Double getReservationPrice() {
        return Double.parseDouble((new WebDriverWait(webdriver, 20)).until(ExpectedConditions.visibilityOf(webdriver.findElement(By.xpath("//div[label[normalize-space(text())='Price:']]//p")))).getText().split(" ")[0]);
    }

    public void reserve() {
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.elementToBeClickable(webdriver.findElement(By.xpath("//button[@type='submit' and normalize-space(text())='Confirm']")))).click();
	}
}
