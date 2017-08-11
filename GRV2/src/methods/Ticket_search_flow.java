package methods;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ticket_search_flow {

	public WebDriver dr;
	ResourceBundle rb;
	String case_id;
	
	public Ticket_search_flow(WebDriver dr)
	{
		this.dr=dr;
		rb=  ResourceBundle.getBundle("grv2");
	}
	
	public void quick_link()
	{
		dr.findElement(By.xpath(rb.getString("quick_link_xpath"))).click();
	}
	
	public void ticket_search()
	{
		dr.findElement(By.xpath(rb.getString("ticket_search_xpath"))).click();
	}
	public void ticket_search_icon()
	{
		dr.findElement(By.xpath(rb.getString("ticket_search_icon"))).click();
	}
	public void window_handling(WebDriver dr) throws InterruptedException
	{
        Thread.sleep(2000);
		Set<String> window = dr.getWindowHandles();
		Iterator<String> I1 = window.iterator();
		String basewindow = I1.next();
		System.out.println(basewindow);
		String Childwindow = I1.next();
		System.out.println(Childwindow);
		String SearchWindow = I1.next();
		dr.switchTo().window(SearchWindow);
		System.out.println(SearchWindow);
		
	}
	

	public void tckt_number_text(String ticket_num) 
	{
	dr.findElement(By.xpath(rb.getString("ticket_num_xpath"))).sendKeys(ticket_num);
		
	}
	public void selectall_checkbox()
	{
	boolean isChecked = dr.findElement(By.xpath(rb.getString("ticket_search_checkbox"))).getAttribute("checked").equals("false");
	if(isChecked)
	{
		dr.findElement(By.xpath(rb.getString("ticket_search_checkbox"))).click();	
	}
	}	
	
	public void tckt_search_arrow()
	{
		dr.findElement(By.xpath(rb.getString("ticket_fetch_arrow"))).click();
		
	}
	public void ticket_fetch_click()
	{
		dr.findElement(By.xpath(rb.getString("ticket_fetch_click"))).click();
		
	}
	
	public void switch_to_base() throws InterruptedException {
		Thread.sleep(2000);
		Set<String> window = dr.getWindowHandles();
		Iterator<String> I1 = window.iterator();
		String Basewindow = I1.next();
		System.out.println(Basewindow);
		String Childwindow = I1.next();
		dr.switchTo().window(Childwindow);
		
	}
	
		public void fetch()
		{
			dr.findElement(By.xpath(rb.getString("fetch_data"))).click();
		}
		public void action()
		{
			dr.findElement(By.xpath(rb.getString("Validate"))).click();
		}

		
	//**************************************************************************************
		
		 public void Policysearchlink()
		 {
			Actions action = new Actions(dr);
			action.moveToElement(dr.findElement(By.xpath(rb.getString("quicklink")))).click().build().perform();
			action.moveToElement(dr.findElement(By.xpath(rb.getString("searchPolicy")))).click().build().perform();
		 }
		 
		 public void enterpolicynum(String ar)
		 {
			 dr.findElement(By.id(rb.getString("policyNumber"))).sendKeys(ar);
		 }
		 
		 public void fetchButton()
		 {
			 dr.findElement(By.id(rb.getString("FetchButton"))).click();
		 }
		 
		 public void checkboxes()
		 {
			 dr.findElement(By.id(rb.getString("policyNumCheck"))).click();
			 dr.findElement(By.id(rb.getString("clientIdCheck"))).click();
			 dr.findElement(By.id(rb.getString("DOB"))).click();
		 }
		 
		 public void Validate() throws InterruptedException
		 {
			 dr.findElement(By.id(rb.getString("BtnValidate"))).click();
			 Thread.sleep(1500);
		 }
		 public void Continue()
		 {
			 dr.switchTo().activeElement();
			 dr.findElement(By.id(rb.getString("Continue"))).click();
		 }
		 
		 public void Ticketsearchlink1() throws InterruptedException
		 {
			Thread.sleep(1000);
			WebDriverWait wait= new WebDriverWait(dr, 5000);
			WebElement el= dr.findElement(By.xpath(rb.getString("quicklink")));
			wait.until(ExpectedConditions.visibilityOf(el));
			Actions action = new Actions(dr);
			action.moveToElement(el).click().build().perform();
			action.moveToElement(dr.findElement(By.xpath(rb.getString("TicketSearchLink")))).click().build().perform();

		 }
		 
		 public void Ticketsearchlink2() throws InterruptedException
		 {
			Thread.sleep(1000);
			WebDriverWait wait= new WebDriverWait(dr, 5000);
			WebElement el= dr.findElement(By.xpath(rb.getString("quicklink1")));
			wait.until(ExpectedConditions.visibilityOf(el));
			System.out.println("waiting for el");
			Actions action = new Actions(dr);
			action.moveToElement(el).click().build().perform();
			action.moveToElement(dr.findElement(By.xpath(rb.getString("TicketSearchLink1")))).click().build().perform();
			System.out.println("tct search screen");
		 }
		 
		 
		 public void EnterTcktNum(String ar)
		 {
			 dr.findElement(By.id(rb.getString("TicketNum"))).sendKeys(ar);
		 }
	
	
}
