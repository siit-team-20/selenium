package pages.navbar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Navbar {
    @FindBy(how = How.CSS, using = "a[href='#/auth/login']")
	private WebElement btnLogout;
	
	private WebDriver webdriver;
	
	public Navbar(WebDriver webdriver) {
		this.webdriver = webdriver;
        PageFactory.initElements(webdriver, this);
	}
	
	public void logOut() {
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.elementToBeClickable(btnLogout)).click();
	}
}
