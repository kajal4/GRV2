package methods;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class login 
{
	public WebDriver dr;
	ResourceBundle rb;
	public login(WebDriver dr)
	{
		this.dr=dr;
		rb=  ResourceBundle.getBundle("grv2");
	}
	
	public void username(String usrname)
	{
		dr.findElement(By.xpath(rb.getString("usrname_xpath"))).sendKeys(usrname);
	}
	
	public void pswd(String pswd)
	{
		dr.findElement(By.xpath(rb.getString("pswd_xpath"))).sendKeys(pswd);
	}
	
	public void login1()
	{
		dr.findElement(By.xpath(rb.getString("login_xpath"))).click();
	}
	
	public void logout() throws InterruptedException
	{
		try
		{
			JavascriptExecutor jse= (JavascriptExecutor)dr;
			jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("logout_xpath"))));
		    dr.findElement(By.xpath(rb.getString("logout_xpath"))).click();
		}
		catch(Exception e)
		{
			System.out.println("entered exception of logout");
			dr.switchTo().activeElement();
			dr.findElement(By.xpath(".//*[@id='saveproceeddialog']/div[2]/input[1]")).click();
			Thread.sleep(2000);
			dr.findElement(By.xpath(rb.getString("logout_xpath"))).click();
		}
	}
	
}
