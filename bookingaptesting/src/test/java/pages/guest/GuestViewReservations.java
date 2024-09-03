package pages.guest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GuestViewReservations {
    private WebDriver webdriver;

    public GuestViewReservations(WebDriver webdriver) {
        this.webdriver = webdriver;
        PageFactory.initElements(webdriver, this);
    }

    @FindBy(how = How.CSS, using = "a[href='#/reservations']")
    private WebElement btnNavbarReservations;

    public void btnNavbarReservations_click() {
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.elementToBeClickable(btnNavbarReservations)).click();
	}

    public int getGuestNumber() {
		return Integer.parseInt((new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(webdriver.findElement(By.xpath("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1]//p[contains(normalize-space(text()), 'Number of guests:')]")))).getText().split(":")[1].substring(1));
    }

    public String getGuestEmail() {		
        return (new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(webdriver.findElement(By.xpath("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1]//div[span[normalize-space(text())='Guest:']]//span[position()=2]")))).getText();
    }

    public String getStartDate() {		
        return (new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(webdriver.findElement(By.xpath("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1]//p[contains(normalize-space(text()), 'Start date:')]")))).getText().split(":")[1].substring(1);
    }

    public String getDuration() {		
        return (new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(webdriver.findElement(By.xpath("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1]//p[contains(normalize-space(text()), 'Duration:')]")))).getText().split(":")[1].substring(1);
    }

    public Double getPrice() {	
        return Double.parseDouble((new WebDriverWait(webdriver, 10))
        .until(driver -> {
            WebElement element = driver.findElement(By.xpath(
                "//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1]//p[contains(normalize-space(text()), 'Price:')]"
            ));

            if (element.isDisplayed() && !element.getText().equals("0 €")) {
                return element;
            }
            return null;
        })
        .getText().split(":")[1].trim());	
        // return Double.parseDouble((new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(webdriver.findElement(By.xpath("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1]//p[contains(normalize-space(text()), 'Price:')]")))).getText().split(":")[1].substring(1));
    }

    public void cancelReservation() {
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.elementToBeClickable(webdriver.findElement(By.xpath("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1]//button[contains(normalize-space(text()), 'Cancel')]")))).click();
    }

    public String getStatus(String text) {
        return (new WebDriverWait(webdriver, 10))
        .until(driver -> {
            WebElement element = driver.findElement(By.xpath(
                String.format("//h4[normalize-space(text())='%s']/ancestor::div[1]//p[contains(normalize-space(text()), 'Status:')]", text)
            ));

            if (element.isDisplayed() && !element.getText().contains("Waiting")) {
                return element;
            }
            return null;
        })
        .getText().split(":")[1].trim();
    }

    public boolean isCancelButtonEnabled() {
        return (new WebDriverWait(webdriver, 10))
        .until(ExpectedConditions.visibilityOf(webdriver.findElement(By.xpath("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1]//button[contains(normalize-space(text()), 'Cancel')]"))))
        .isEnabled();
    }
}
