package managers;

import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.CareersPage;

public class PageObjectManager {

    private WebDriver webDriver;

    public PageObjectManager(WebDriver driver) {
        this.webDriver = driver;
    }

    public HomePage getHomePage() {
        return new HomePage(this.webDriver);
    }

    public CareersPage getShopPage() {
        return new CareersPage(this.webDriver);
    }
}
