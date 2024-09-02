package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverSetup {
    private static final String DRIVER_CHROME = "webdriver.chrome.driver";
	private static final String DRIVER_CHROME_LOC = "src/test/resources/driver/chromedriver.exe";

    public static WebDriver useChrome() {
		System.setProperty(DRIVER_CHROME, DRIVER_CHROME_LOC);
		return new ChromeDriver();
	}
}
