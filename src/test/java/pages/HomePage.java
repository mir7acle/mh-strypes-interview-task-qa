package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.netty.util.internal.ThreadLocalRandom;

import java.util.List;
import java.util.NoSuchElementException;

public class HomePage {

    private WebDriver driver;
    private WebElement swiperSlide;
    private Slide slide;
    
    private class Slide {
    	
    	WebElement slide;
    	String slideState;
    	int slideIndex;
    	String slideHeader;
    	String slideDescription;
    	
    	
        public Slide(String slideState) {
        	
    		this.slideState = slideState;
    		
        	String className = "swiper-slide-" + slideState;
        	this.slide = driver.findElement(By.className(className));

    		String slideActiveIndex = slide.getAttribute("data-swiper-slide-index");
        	this.slideIndex = Integer.parseInt(slideActiveIndex);
        	
        	this.slideHeader = slide.findElement(By.className("elementor-slide-heading")).getText().trim();
        	this.slideDescription = slide.findElement(By.className("elementor-slide-description")).getText().trim();
    	
//    		System.out.println(this.slideIndex);
//    		System.out.println(this.slideHeader);
//    		System.out.println(this.slideDescription);
      	            
        }           		   	 	
     }
        

    public HomePage(WebDriver driver) {
    	
        this.driver = driver;
    	this.swiperSlide = driver.findElement(By.xpath("//div[@class='swiper-wrapper elementor-slides']"));
    }

    public void openHomePage() {
  
    	WebElement menuHome = driver.findElement(By.linkText("Home"));
 	        
        SoftAssert homeIsAvalable = new SoftAssert();
        homeIsAvalable.assertTrue(menuHome.isEnabled());
        homeIsAvalable.assertTrue(menuHome.isDisplayed());
        homeIsAvalable.assertAll();
   
    	menuHome.click();
    } 
    
    public boolean isLoaded() { 
    	
//      LOGGER.info("Careers Page: isLoaded()");
      System.out.println("Home Page: isLoaded()");
      try {        	
    	  WebElement headingTitle = driver.findElement(By.xpath("//h1[@class='elementor-heading-title elementor-size-default']"));
          String foundTitle = headingTitle.findElement(By.xpath("//span")).getText() + headingTitle.getText();
//          System.out.println("Home page search title found: " + foundTitle);
    	  Assert.assertTrue(foundTitle.equals("We are Strypes"), "Home page visible heading title is not yet available."); 
        return true;          
      } catch ( NoSuchElementException e) {
//           LOGGER.info("No such element." );
         Assert.assertTrue(false,"No such element class elementor-heading-title in Home page. ");        
      }
      return false;
  }
    
    public void getSlide(String status) {
    	this.slide = new Slide(status);
    }
    
	public int getSlideIndex() {		
		return this.slide.slideIndex;
	}

	public String getSlideHeader() {
		return this.slide.slideHeader;
	}
	
	public String getSlideDescription() {
		return this.slide.slideDescription;
	}     
    
    public void clickLearnMore() {
    	  
    	Slide slideActive = new Slide("active");
		WebElement buttonLearnMore = slideActive.slide.findElement(By.className("elementor-size-md"));
		
 	        
        SoftAssert learnmoreIsAvalable = new SoftAssert();
        learnmoreIsAvalable.assertTrue(buttonLearnMore.isEnabled());
        learnmoreIsAvalable.assertTrue(buttonLearnMore.isDisplayed());
        learnmoreIsAvalable.assertAll();
   
        buttonLearnMore.click();
    } 

    // Narrow slide navigation: Left: Go to the previous slide
    public void clickOnSliderLeftNarrow() {
		try {
			List<WebElement> leftNarrows = driver.findElements(By.xpath("//div[@class='elementor-swiper-button elementor-swiper-button-prev']"));
			leftNarrows.get(0).click();
		}
		catch(org.openqa.selenium.StaleElementReferenceException ex)
		{
			List<WebElement> leftNarrows = driver.findElements(By.xpath("//div[@class='elementor-swiper-button elementor-swiper-button-prev']"));
			leftNarrows.get(0).click();
		}
    }
    
    // Narrow slide navigation: Right: Go to the next slide
    public void clickOnSliderRightNarrow() {
		try {
			List<WebElement> rightNarrows = driver.findElements(By.xpath("//div[@class='elementor-swiper-button elementor-swiper-button-next']"));
			rightNarrows.get(0).click();
		}
		catch(org.openqa.selenium.StaleElementReferenceException ex)
		{
			List<WebElement> rightNarrows = driver.findElements(By.xpath("//div[@class='elementor-swiper-button elementor-swiper-button-next']"));
			rightNarrows.get(0).click();
		}
    }
    
    public int clickOnRandomSwiperSliderBullet() {
    	
        List<WebElement> paginationBullets = driver.findElements(By.className("swiper-pagination-bullet"));
        
        int randomNum = ThreadLocalRandom.current().nextInt(0, paginationBullets.size() -1);
        paginationBullets.get(randomNum).click();
        
        return randomNum;  
    }
    
	public int getSlideCountAccordingPaginationBullets() {
		
		List<WebElement> paginationBullets = driver.findElements(By.className("swiper-pagination-bullet"));
		int sliderPaginationBulletCaount = paginationBullets.size();
		return sliderPaginationBulletCaount;
	}
	
    public int getSlideCountAccordingSlideItems() {
       
    	WebElement swiperSlide = driver.findElement(By.xpath("//div[@class='swiper-wrapper elementor-slides']"));
    	List<WebElement> allSlides = swiperSlide.findElements(By.className("swiper-slide"));
        int count = 0;

        for (WebElement slide : allSlides) {
        	
        	int currentSlideIndex = Integer.parseInt(slide.getAttribute("data-swiper-slide-index"));
            if (count < currentSlideIndex) {
               count = currentSlideIndex;
            }
        }
        
        return count + 1;
    }

}
