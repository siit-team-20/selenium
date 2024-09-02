package pages.owner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OwnerViewReservations {
    private WebDriver webdriver;

    public OwnerViewReservations(WebDriver webdriver) {
        this.webdriver = webdriver;
        PageFactory.initElements(webdriver, this);
    }

    @FindBy(how = How.CSS, using = "a[href='#/reservations']")
    private WebElement btnNavbarReservations;

    public void btnNavbarReservations_click() {
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.elementToBeClickable(btnNavbarReservations)).click();
	}

    public int getGuestNumber(String guestEmail) {
        String xpath = String.format("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1][descendant::a[normalize-space(text())='%s']]//p[contains(normalize-space(text()), 'Number of guests:')]", guestEmail);
        return Integer.parseInt(
            (new WebDriverWait(webdriver, 10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
            .getText()
            .split(":")[1]
            .trim()
        );
    }

    public String getGuestEmail(String guestEmail) {
        String xpath = String.format("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1][descendant::a[normalize-space(text())='%s']]//div[span[normalize-space(text())='Guest:']]//span[position()=2]", guestEmail);
        return 
            (new WebDriverWait(webdriver, 10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
            .getText();	
    }

    public String getStartDate(String guestEmail) {		
        String xpath = String.format("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1][descendant::a[normalize-space(text())='%s']]//p[contains(normalize-space(text()), 'Start date:')]", guestEmail);        
        return 
            (new WebDriverWait(webdriver, 10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
            .getText()
            .split(":")[1]
            .trim();	
    }

    public String getDuration(String guestEmail) {		
        String xpath = String.format("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1][descendant::a[normalize-space(text())='%s']]//p[contains(normalize-space(text()), 'Duration:')]", guestEmail);        
        return 
            (new WebDriverWait(webdriver, 10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
            .getText()
            .split(":")[1]
            .trim();	
    }

    public Double getPrice(String guestEmail) {		
        String xpath = String.format("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1][descendant::a[normalize-space(text())='%s']]//p[contains(normalize-space(text()), 'Price:')]", guestEmail);
        return Double.parseDouble(
            (new WebDriverWait(webdriver, 10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
            .getText()
            .split(":")[1]
            .trim()
        );

    }

    public String getStatus(String guestEmail) {
        return (new WebDriverWait(webdriver, 10))
        .until(driver -> {
            WebElement element = driver.findElement(By.xpath(
                String.format("//h4[normalize-space(text())='Promena Brisbane North Accommodation, Motel']/ancestor::div[1][descendant::a[normalize-space(text())='%s']]//p[contains(normalize-space(text()), 'Status:')]", guestEmail)
            ));
            
            if (element.isDisplayed() && !element.getText().contains("Waiting")) {
                return element;
            }
            return null;
        })
        .getText().split(":")[1].trim();
    }

    public void refresh() {
        webdriver.navigate().refresh();
    }
}
