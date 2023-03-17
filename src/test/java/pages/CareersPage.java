package pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class CareersPage {

    private WebDriver driver;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCareersPage() {
    	
        WebElement careers = driver.findElement(By.linkText("Careers"));

        SoftAssert careersIsAvalable = new SoftAssert();
        careersIsAvalable.assertTrue(careers.isEnabled());
        careersIsAvalable.assertTrue(careers.isDisplayed());
        careersIsAvalable.assertAll();
       
        careers.click();
    }
    
    public boolean isLoaded() throws Error {  
    	
//        LOGGER.info("Careers Page: isLoaded()");
        System.out.println("Careers Page: isLoaded()");
        try {
          Assert.assertTrue(driver.findElement(By.className("elementor-heading-title")).getText().equals("We are hiring"), "Careers page visible heading title is not yet available."); 
          return true;          
        } catch ( NoSuchElementException e) {
 //           LOGGER.info("No such element." );
           Assert.assertTrue(false,"No such element class elementor-heading-title in Careers page. ");        
        }
        return false;
    }
}