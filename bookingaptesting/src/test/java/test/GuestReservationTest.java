package test;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.WebDriver;

import pages.LoginPage;
import pages.guest.GuestCreateReservation;
import pages.guest.GuestViewReservations;
import pages.navbar.Navbar;
import pages.owner.OwnerViewReservations;
import util.DriverSetup;

@TestInstance(Lifecycle.PER_CLASS)
public class GuestReservationTest {
    private static WebDriver webdriver;
    private static WebDriver webdriver2;

    @BeforeAll
    public void init() {
        webdriver = DriverSetup.useChrome();
        webdriver.manage().window().maximize();

        webdriver2 = DriverSetup.useChrome();
        webdriver2.manage().window().maximize();
    }

    @AfterAll
	public void finish() {
		webdriver.close();
		webdriver2.close();
	}

    @Test
    @DisplayName("Guest cancels reservation")
    public void t1() {
        WebDriver wdGuest = webdriver;
        WebDriver wdOwner = webdriver2;

        LoginPage loginGuest = new LoginPage(wdGuest);
        LoginPage loginOwner = new LoginPage(wdOwner);

        loginGuest.btnNavbarLogin_click();
        loginGuest.login("guest@gmail.com", "guest");
        
        loginOwner.btnNavbarLogin_click();
        loginOwner.login("owner@gmail.com", "owner");

        GuestCreateReservation guestCreateReservation = new GuestCreateReservation(wdGuest);
        guestCreateReservation.btnAcommodationDetails_click();
        guestCreateReservation.enterFields("09-Sep-2024", "10-Sep-2024", 2);
        Double price = guestCreateReservation.getReservationPrice();
        guestCreateReservation.reserve();

        GuestViewReservations guestReservations = new GuestViewReservations(wdGuest);
        guestReservations.btnNavbarReservations_click();
        verifyReservation(guestReservations, "guest@gmail.com", "2024-09-09", "1 days", 2, price);

        OwnerViewReservations ownerReservations = new OwnerViewReservations(wdOwner);
        ownerReservations.btnNavbarReservations_click();
        verifyReservationOwner(ownerReservations, "guest@gmail.com", "2024-09-09", "1 days", 2, price);

        guestReservations.cancelReservation();

        String status = guestReservations.getStatus("Promena Brisbane North Accommodation, Motel");
        assertThat(status).isEqualTo("Cancelled");
        
        ownerReservations.refresh();
        String statusOwner = ownerReservations.getStatus("guest@gmail.com");
        assertThat(statusOwner).isEqualTo("Cancelled");

        Navbar navbarGuest = new Navbar(wdGuest);
        navbarGuest.logOut();

        Navbar navbarOwner = new Navbar(wdOwner);
        navbarOwner.logOut();
    }

    @Test
    @DisplayName("Guest cannot cancel reservation - deadline")
    public void t2() {
        WebDriver wdGuest = webdriver;
        WebDriver wdOwner = webdriver2;

        LoginPage loginGuest = new LoginPage(wdGuest);
        LoginPage loginOwner = new LoginPage(wdOwner);

        loginGuest.btnNavbarLogin_click();
        loginGuest.login("guest1@gmail.com", "guest");
        
        loginOwner.btnNavbarLogin_click();
        loginOwner.login("owner@gmail.com", "owner");

        GuestCreateReservation guestCreateReservation = new GuestCreateReservation(wdGuest);
        guestCreateReservation.btnAcommodationDetails_click();
        guestCreateReservation.enterFields("05-Sep-2024", "06-Sep-2024", 2);
        Double price = guestCreateReservation.getReservationPrice();
        guestCreateReservation.reserve();

        GuestViewReservations guestReservations = new GuestViewReservations(wdGuest);
        guestReservations.btnNavbarReservations_click();
        verifyReservation(guestReservations, "guest1@gmail.com", "2024-09-05", "1 days", 2, price);

        OwnerViewReservations ownerReservations = new OwnerViewReservations(wdOwner);
        ownerReservations.btnNavbarReservations_click();
        verifyReservationOwner(ownerReservations, "guest1@gmail.com", "2024-09-05", "1 days", 2, price);

        assertThat(!guestReservations.isCancelButtonEnabled());

        Navbar navbarGuest = new Navbar(wdGuest);
        navbarGuest.logOut();

        Navbar navbarOwner = new Navbar(wdOwner);
        navbarOwner.logOut();
    }

    @Test
    @DisplayName("Guest reserves - automatic acceptance")
    public void t3() {
        WebDriver wdGuest = webdriver;
        WebDriver wdOwner = webdriver2;

        LoginPage loginGuest = new LoginPage(wdGuest);
        LoginPage loginOwner = new LoginPage(wdOwner);

        loginGuest.btnNavbarLogin_click();
        loginGuest.login("guest_new@gmail.com", "guest");
        
        loginOwner.btnNavbarLogin_click();
        loginOwner.login("owner@gmail.com", "owner");

        GuestCreateReservation guestCreateReservation = new GuestCreateReservation(wdGuest);
        guestCreateReservation.btnAcommodationDetailsAutomatic_click();
        guestCreateReservation.enterFields("09-Sep-2024", "10-Sep-2024", 3);
        guestCreateReservation.reserve();

        GuestViewReservations guestReservations = new GuestViewReservations(wdGuest);
        guestReservations.btnNavbarReservations_click();

        String status = guestReservations.getStatus("test 1, Apartment");
        assertThat(status).isEqualTo("Approved");

        Navbar navbarGuest = new Navbar(wdGuest);
        navbarGuest.logOut();
    }

    private void verifyReservation(GuestViewReservations g, String guestEmail, String startDate, String duration, int guestNumber, Double price) {
        int otherGuestNumber = g.getGuestNumber();
        assertThat(otherGuestNumber).isEqualTo(guestNumber);

        String otherGuestEmail = g.getGuestEmail();
        assertThat(otherGuestEmail).isEqualTo(guestEmail);

        String otherStartDate = g.getStartDate();
        assertThat(otherStartDate).isEqualTo(startDate);

        String otherDuration = g.getDuration();
        assertThat(otherDuration).isEqualTo(duration);

        Double otherPrice = g.getPrice();
        assertThat(otherPrice).isEqualTo(price);
    }

    private void verifyReservationOwner(OwnerViewReservations o, String guestEmail, String startDate, String duration, int guestNumber, Double price) {
        int otherGuestNumber = o.getGuestNumber(guestEmail);
        assertThat(otherGuestNumber).isEqualTo(guestNumber);

        String otherGuestEmail = o.getGuestEmail(guestEmail);
        assertThat(otherGuestEmail).isEqualTo(guestEmail);

        String otherStartDate = o.getStartDate(guestEmail);
        assertThat(otherStartDate).isEqualTo(startDate);

        String otherDuration = o.getDuration(guestEmail);
        assertThat(otherDuration).isEqualTo(duration);

        Double otherPrice = o.getPrice(guestEmail);
        assertThat(otherPrice).isEqualTo(price);
    }
}
