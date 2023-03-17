package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class WebDriverManager {

	private static Properties prop;
	private WebDriver driver;
    
    public WebDriverManager(String browser) {
		
    	getServerProperties();
    	System.setProperty("webdriver.http.factory", this.getWebDriverHTTPFactory());
    	
    	setWebDriver(browser); 	
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(this.getImplicitlyWait()));
	}
     
    public WebDriver getDriver() {
        return driver;
    }

    public void openURL() {
        driver.get(this.getURL());
    }
    
    public void openURL(String url) {
       if (url != null) {
    	   driver.get(url); 
       }
    }

    public String getDriverPath(){
		String driverPath = prop.getProperty("driverPath");
		if (driverPath!= null) return driverPath;
		else throw new RuntimeException("driverPath not specified in the configuration.properties file.");		
	}
	
	public long getImplicitlyWait() {		
		String implicitlyWait = prop.getProperty("implicitlyWait");
//		System.out.println("Drive implicitlyWait = " + implicitlyWait);
		if (implicitlyWait != null) return Long.parseLong(implicitlyWait);
		else throw new RuntimeException("implicitlyWait not specified in the configuration.properties file.");		
	}
	
	public String getURL() {		
		String url = prop.getProperty("url");
		if (url != null) return url;
		else throw new RuntimeException("URL not specified in the configuration.properties file.");		
	}
 
	private String getWebDriverHTTPFactory() {		
		String webDriverHTTPFactory = prop.getProperty("webdriver.http.factory");
		if (webDriverHTTPFactory != null) return webDriverHTTPFactory;
		else throw new RuntimeException("webdriver.http.factory not specified in the configuration.properties file.");		
	}
	
    private void setWebDriver(String browser) {

    	String driverPath = this.getDriverPath();
    	
        if (browser.equalsIgnoreCase("chrome")) {

            System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
            driver = new ChromeDriver();


        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", driverPath + "getgeckodriver.exe");
            driver = new FirefoxDriver();
        }
        else throw new RuntimeException("Browser not specified in the configuration.properties file.");		
    	
    }
    
    private void getServerProperties() {
        prop = new Properties();
        InputStream in;
        FileInputStream input = null;

        try { 
            input = new FileInputStream("src\\test\\java\\config\\configuration.properties");
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void closeDriver() {
      //  driver.close();
        driver.quit();
    }
}
