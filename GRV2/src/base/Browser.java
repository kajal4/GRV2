package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;

public class Browser 
{
public WebDriver dr;

@ BeforeMethod	
public void initiate_browser()
{
	System.setProperty("webdriver.chrome.driver", "C:/Users/Kajali Agrawal/Desktop/chromedriver.exe");
	ChromeOptions option = new ChromeOptions();
	option.addArguments("start-maximized");
	dr = new ChromeDriver (option);
	dr.get("http://maxprodsa/sn/app/login/login");
//	dr.get("http://maxqa/sn/app/login/login");
	
}

}
