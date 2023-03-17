package pages;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class ServicesPage {

    private WebDriver driver;

    public ServicesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openServicesPage() {
    	
    	WebElement menuHome = driver.findElement(By.linkText("Services"));
	        
        SoftAssert homeIsAvalable = new SoftAssert();
        homeIsAvalable.assertTrue(menuHome.isEnabled());
        homeIsAvalable.assertTrue(menuHome.isDisplayed());
        homeIsAvalable.assertAll();
   
    	menuHome.click();
    }
    
    public boolean isLoaded() { 
    	
//        LOGGER.info("Careers Page: isLoaded()");
        System.out.println("Services Page: isLoaded()");
        try {        	
//          Assert.assertTrue(driver.findElement(By.className("elementor-heading-title")).getText().equals("Our smart services that create business impact"), "Services page visible heading title is not yet available."); 
        	List<WebElement> foundTitleAllElems = driver.findElements(By.className("elementor-heading-title"));
      	   	Assert.assertTrue(foundTitleAllElems.get(1).getText().equals("Services to make the world smarter"), "Services page visible heading title is not faund."); 
            
        	return true;          
        } catch ( NoSuchElementException e) {
 //           LOGGER.info("No such element." );
           Assert.assertTrue(false,"No such element class elementor-heading-title in Services page. ");        
        }
        return false;
    }
}