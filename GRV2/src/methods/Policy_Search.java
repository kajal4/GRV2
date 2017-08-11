package methods;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Policy_Search 
{
	public WebDriver dr;
	ResourceBundle rb;
	public Policy_Search(WebDriver dr)
	{
		this.dr = dr;
		rb= ResourceBundle.getBundle("grv2");
	}
	
	public void quick_link()
	{
		dr.findElement(By.xpath(rb.getString("quick_link_xpath"))).click();
	}
	
	public void policy_search()
	{
//		WebDriverWait wait= new WebDriverWait(dr,4000);
//		wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(rb.getString("policy_search_xpath")))));
		dr.findElement(By.xpath(rb.getString("policy_search_xpath"))).click();
	}
	
	public void policy_number_text(String policy_num)
	{
		dr.findElement(By.xpath(rb.getString("policy_numb_xpath"))).sendKeys(policy_num);
	}
	public void fetch()
	{
		dr.findElement(By.xpath(rb.getString("fetch_data"))).click();
	}
	public void check_mark() throws InterruptedException
	{
//		WebDriverWait wait= new WebDriverWait(dr, 2000);
//		wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(rb.getString("policy_check")))));
		Thread.sleep(1500);
		dr.findElement(By.xpath(rb.getString("policy_check"))).click();
		dr.findElement(By.xpath(rb.getString("client_id_check"))).click();
		dr.findElement(By.xpath(rb.getString("DOB_check"))).click();
	}
	public void validate()
	{
		dr.findElement(By.xpath(rb.getString("Validate"))).click();
	}
	public void popup_handling()
	{
		WebElement element= dr.findElement(By.xpath(rb.getString("popup_yes")));
		JavascriptExecutor jse= (JavascriptExecutor)dr;
		jse.executeScript("arguments[0].click()",element);
	}
	
	
}
