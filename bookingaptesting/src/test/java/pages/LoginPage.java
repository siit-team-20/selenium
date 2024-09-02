package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.Util;

public class LoginPage {
    private WebDriver webdriver;

    public LoginPage(WebDriver webdriver) {
        this.webdriver = webdriver;
        webdriver.get(Util.BookingAppUrl);
        PageFactory.initElements(webdriver, this);
    }

    @FindBy(how = How.CSS, using = "a[href='#/auth/login']")
    private WebElement btnNavbarLogin;
	
	@FindBy(how = How.CSS, using = "#email")
	private WebElement inputEmail;
	
	@FindBy(how = How.CSS, using = "#password")
	private WebElement inputPassword;
	
	@FindBy(how = How.CSS, using = "button[type='submit']")
	private WebElement btnSubmitLogin;
	
	public void btnNavbarLogin_click() {
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.elementToBeClickable(btnNavbarLogin)).click();
	}
	
	public void login(String email, String password) {
		enterEmailPassword(email, password);
		
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.elementToBeClickable(btnSubmitLogin)).click();
	}
	
	public void enterEmailPassword(String email, String password) {
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(inputEmail)).clear();
		inputEmail.sendKeys(email);
		(new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(inputPassword)).clear();
		inputPassword.sendKeys(password);
	}
	
	public boolean isLoginButtonEnabled() {
		return (new WebDriverWait(webdriver, 10)).until(ExpectedConditions.visibilityOf(btnSubmitLogin)).isEnabled();
	}
}
