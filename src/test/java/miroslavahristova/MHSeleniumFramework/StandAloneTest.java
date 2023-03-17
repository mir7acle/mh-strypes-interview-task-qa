package miroslavahristova.MHSeleniumFramework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import managers.PageObjectManager;
import managers.WebDriverManager;
import pages.HomePage;
import pages.ServicesPage;

public class StandAloneTest {

	public static void main(String[] args) {
		
		WebDriverManager wdm = new WebDriverManager("chrome");
		WebDriver driver = wdm.getDriver();

		wdm.openURL();
		driver.manage().window().maximize();
		driver.findElement(By.className("gdprc_action__bar_save_all")).click();
		
		PageObjectManager pom = new PageObjectManager(driver);
		HomePage home = pom.getHomePage();
		
					
		Actions actions = new Actions(driver);
		
		// HomePage hp = new HomePage(driver);
		
//		WebElement aboutMenu = driver.findElement(By.linkText("About"));
//		actions.moveToElement(aboutMenu).perform();
//		
		WebElement swiperContainer = driver.findElement(By.className("elementor-slides"));
		actions.clickAndHold(swiperContainer).perform();

		
		
		// Test case 1: Open home page loads the swiper slider on the first slide
		System.out.println("\n--- Test case 1 ---\n");
		
		home.openHomePage();		
		System.out.println("Home page is loaded: " + home.isLoaded());
		home.getSlide("active");
		
		int slideFirstIndex = 0;
		String slideFirstHeader = "Building Smart Solutions";
		String slideFIrstDescription = "We provide end-to-end software solutions";
				
        Assert.assertTrue(home.getSlideIndex() == slideFirstIndex, "Open Home page swiper slide is not on the first slide item. Slide index in not " + 0); 
    	Assert.assertTrue(home.getSlideHeader().startsWith(slideFirstHeader), "Open Home page swiper slide is not on the first slide item. Slide header is not " + slideFirstHeader); 
        Assert.assertTrue(home.getSlideDescription().equals(slideFIrstDescription), "Open Home page swiper slide is not on the first slide item. Slide description is not " + slideFIrstDescription); 
 
		
		// Test case 2: Click on "Learn more" on the first slide opens Services page
        System.out.println("\n--- Test case 2 ---\n");
        
        home.openHomePage();
		home.clickLearnMore();
		ServicesPage services = new ServicesPage(driver);					
		Assert.assertTrue(services.isLoaded(), "Click on Learn more button on home page does not load the services page.");

	
		// Test case 3: Navigation right narrow: swiper narrow button right changes the slide item to the next one
		System.out.println("\n--- Test case 3 ---\n");

		home.openHomePage();
		home.isLoaded();
		home.getSlide("active");
		int indexCurrentSlide = home.getSlideIndex();
		home.clickOnSliderRightNarrow();
		home.getSlide("active");
		int indexRightSlide = home.getSlideIndex();		
		System.out.println("Right: Current slide: " + indexCurrentSlide + "\nAfter Right narrow slide: " + indexRightSlide);
		
		if (indexCurrentSlide == home.getSlideCountAccordingSlideItems() - 1) {
			Assert.assertTrue(indexRightSlide == 0, "Clicking on LEFT narrow on the first slide does not go to the last slide");			
		} else {
			Assert.assertTrue(indexRightSlide == indexCurrentSlide + 1, "Clicking on RIGHT narrow on slide does not go to the previous slide");			
		}
		
		
		//	Test case 4: Navigation left narrow: swiper narrow button left changes the slide item to the previous one
		System.out.println("\n--- Test case 4 ---\n");
		
		home.isLoaded();
		home.getSlide("active");
		indexCurrentSlide = home.getSlideIndex();
		home.clickOnSliderLeftNarrow();
		home.getSlide("active");
		int indexLeftSlide = home.getSlideIndex();		
		System.out.println("Left: Current slide: " + indexCurrentSlide + "\nAfter Left narrow slide: " + indexLeftSlide);
		
		if (indexCurrentSlide == 0) {
			Assert.assertTrue(indexLeftSlide == home.getSlideCountAccordingSlideItems()-1, "Clicking on LEFT narrow on the first slide does not go to the last slide");			
		} else {
			Assert.assertTrue(indexLeftSlide == indexCurrentSlide-1, "Clicking on LEFT narrow on slide does not go to the previous slide");			
		}		
	
		
		
		// Test case 5: The swipe active slide is auto-changed to the next slide on every 5 seconds 
		System.out.println("\n--- Test case 5 ---\n");
		
		home.openHomePage();
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		home.getSlide("active");
		
		int slideSecondIndex = 1;
		String slideSecondHeader = "Nearsurance";
		String slideSecondDescription = "Our unique outsourcing model that brings business success";
				
        Assert.assertTrue(home.getSlideIndex() == slideSecondIndex, "Open Home page swiper slide is not on the second slide item. Slide index in not " + slideSecondIndex); 
    	Assert.assertTrue(home.getSlideHeader().startsWith(slideSecondHeader), "Open Home page swiper slide is not on the second slide item. Slide header is not " + slideSecondHeader); 
        Assert.assertTrue(home.getSlideDescription().equals(slideSecondDescription), "Open Home page swiper slide is not on the second slide item. Slide description is not " + slideSecondDescription); 
   
   
        // Test case 6: Click on bullet in the swiper pagination, opens the slide with corresponding slide index to the bullet index    
        System.out.println("\n--- Test case 6 ---\n");
        
        home.isLoaded();
        int randomNum = home.clickOnRandomSwiperSliderBullet();
        home.getSlide("active");
        Assert.assertTrue(randomNum == home.getSlideIndex(), "Click on bullet in swipe pagination DOES NOT load the slide item with the same index as bullet sequence.\n Bullet sequence: " + randomNum + "\n Opened slide index: " + home.getSlideIndex()); 
 
		
        // Test case 7: The dots count in the swiper pagination bullet is the same as the slide count in the swipe slider
        System.out.println("\n--- Test case 7 ---\n");
        
        home.isLoaded();
        int slideCountAccordingPaginationBullets = home.getSlideCountAccordingPaginationBullets();
        int slideCountAccordingSlideItems = home.getSlideCountAccordingSlideItems();
        Assert.assertTrue(slideCountAccordingPaginationBullets == slideCountAccordingSlideItems, "The slide items in DOM is NOT the same as count as the count of the swipe pagination bullets:\n slide items count: " + slideCountAccordingSlideItems + "\n Pagination bullet count: " + slideCountAccordingPaginationBullets); 
         
        
        wdm.closeDriver();
	}
}
