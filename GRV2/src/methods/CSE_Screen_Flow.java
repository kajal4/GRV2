package methods;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public  class CSE_Screen_Flow 
{

	public  WebDriver dr;
	  ResourceBundle rb;
	 
	public CSE_Screen_Flow(WebDriver dr)
	{
		this.dr=dr;
		rb=  ResourceBundle.getBundle("grv2");
	} 
	
	public void window_handling(WebDriver dr) throws InterruptedException
	{
        Thread.sleep(2000);
		Set<String> window = dr.getWindowHandles();
		Iterator<String> I1 = window.iterator();
		I1.next();
		String Childwindow = I1.next();
		dr.switchTo().window(Childwindow);
		
	}
	
	public void caller_type(String caller) throws InterruptedException
	{  
		Thread.sleep(2000);
		JavascriptExecutor jse= (JavascriptExecutor)dr;
		jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.id(rb.getString("Caller_type_xpath"))));
		WebElement caller_type= dr.findElement(By.id(rb.getString("Caller_type_xpath")));
		Select select= new Select(caller_type);
		select.selectByVisibleText(caller);

	}
	
	public  void caller_name()
	{
		dr.findElement(By.xpath(rb.getString("Caller_name_xpath"))).sendKeys("kajali");
	}
	
	public  void problem_box(String ar)
	{
		WebDriverWait wait= new WebDriverWait(dr,5000);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
		dr.findElement(By.xpath(rb.getString("problembox_xpath"))).sendKeys(ar);
		dr.switchTo().defaultContent();
	}
	
	public  void ssc(String ar) throws InterruptedException
	{
	  dr.findElement(By.xpath(rb.getString("ssc"))).sendKeys(ar);
	  WebDriverWait wait = new WebDriverWait(dr, 4000);
	  WebElement el=dr.findElement(By.xpath("//*[@id='ui-id-12']"));
	  wait.until(ExpectedConditions.visibilityOf(el));
	  dr.findElement(By.xpath(rb.getString("ssc"))).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
	}

	public void next_button()
	{
		dr.findElement(By.xpath(rb.getString("Next_button_xpath"))).click();
	}
	

	public void reason()
	{
		Select select=new Select (dr.findElement(By.xpath(rb.getString("Reason_xpath"))));
		select.selectByIndex(1);
	}
	
	public  void save()
	{
		JavascriptExecutor jse= (JavascriptExecutor)dr;
		jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("Save&Proceed_xpath"))));
		dr.findElement(By.xpath(rb.getString("Save&Proceed_xpath"))).click();
	}

   public String case_id()
   {
	 String case_id=dr.findElement(By.xpath(rb.getString("case_id"))).getText();
	 return(case_id.replace("000", ""));
   }
   
   public String case_id1()
   {
	 String case_id=dr.findElement(By.xpath(rb.getString("case_id"))).getText();
	 return(case_id.replace("000", ""));
   }
   
   public String assigned_to()
   {
	   return (dr.findElement(By.xpath(rb.getString("assigend_to_1"))).getText()); 
   }
	
   public String Case_owner()
   {
	   return (dr.findElement(By.xpath(rb.getString("case_owner"))).getText()); 
   }
	
   public void return_to_active_window()
   {
	  dr.switchTo().activeElement();
	  
   }
   
  public void is_lapse(String decision) 
  {
	  
	 Select select= new Select(dr.findElement(By.xpath(rb.getString("is_lapse"))));
	 select.selectByVisibleText(decision);
  }
   
  public void calendar(String day,String Month,String Year)
  {
	  JavascriptExecutor jse = (JavascriptExecutor)dr;
	  jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("calendar"))));
	  dr.findElement(By.xpath(rb.getString("calendar"))).click();	   
	  Actions month= new Actions(dr);
	  month.moveToElement(dr.findElement(By.xpath(rb.getString("month")))).build().perform();
	  Select mo= new Select (dr.findElement(By.xpath(rb.getString("month"))));
	  mo.selectByValue(Month);
	 
	  Actions year= new Actions(dr);
	  year.moveToElement(dr.findElement(By.xpath(rb.getString("year")))).build().perform();
	  Select yr= new Select (dr.findElement(By.xpath(rb.getString("year"))));
	  yr.selectByValue(Year);
	 
	  
	  String x1 = ".//*[@id='ui-datepicker-div']/table/tbody/tr[";
	  String x2 = "]/td["; 
	  String x3 ="]";
	 
	 List <WebElement> dates = new ArrayList<WebElement>();
	 for(int i=1;i<6;i++)
	 {
	 	  for(int j=1;j<8;j++)
	 	  {
	 		   WebElement z= dr.findElement(By.xpath(x1+i+x2+j+x3)); 
	 		   dates.add(z);
	 	  }
	 } 
	int size= dates.size();
	System.out.println(size);
	for(int i=0;i<size;i++)
	{ 
		String m = dates.get(i).getText();
		if(m.equals(day))
		{
			dates.get(i).click();
		}
	}
	  
  }
  
  public void doc_id() throws InterruptedException
  {
	  JavascriptExecutor jse = (JavascriptExecutor)dr;
	  jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("doc_id_picker"))));
	  dr.findElement(By.xpath(rb.getString("doc_id_picker"))).click();
	  Thread.sleep(2000);
	  dr.switchTo().activeElement();
	  Thread.sleep(500);
	  dr.findElement(By.xpath(rb.getString("doc_id"))).click();
  }
  
  public void doc_send_to()
  {
	  WebElement send=dr.findElement(By.xpath(rb.getString("doc_send_to")));
	  Select select= new Select (send);
	  select.selectByVisibleText("Customer");
  }
  
  public void mode()
  {
	  WebElement send=dr.findElement(By.xpath(rb.getString("mode")));
	  Select select= new Select (send);
	  select.selectByVisibleText("Email");
  }
  
  public void form_yr()
  {
	  WebElement send=dr.findElement(By.xpath(rb.getString("from_year")));
	  Select select= new Select (send);
	  select.selectByVisibleText("2005");
  }
  
  public void to_yr()
  {
	  WebElement send=dr.findElement(By.xpath(rb.getString("to_year")));
	  Select select= new Select (send);
	  select.selectByVisibleText("2008");
  }
  
  public void type_of_payment(String paymenttype)
  {
	  WebElement send=dr.findElement(By.xpath(rb.getString("type_of_payment")));
	  Select select= new Select (send);
	  select.selectByVisibleText(paymenttype);
  }
  
  
  public void date_of_payment(String day,String Month,String Year)
  {
	  dr.findElement(By.xpath(rb.getString("payment_calendar"))).click();	   
	  Actions month= new Actions(dr);
	  month.moveToElement(dr.findElement(By.xpath(rb.getString("month")))).build().perform();
	  Select mo= new Select (dr.findElement(By.xpath(rb.getString("month"))));
	  mo.selectByValue(Month);
	 
	  Actions year= new Actions(dr);
	  year.moveToElement(dr.findElement(By.xpath(rb.getString("year")))).build().perform();
	  Select yr= new Select (dr.findElement(By.xpath(rb.getString("year"))));
	  yr.selectByValue(Year);
	 
	  
	  String x1 = ".//*[@id='ui-datepicker-div']/table/tbody/tr[";
	  String x2 = "]/td["; 
	  String x3 ="]";
	 
	 List <WebElement> dates = new ArrayList<WebElement>();
	 for(int i=1;i<6;i++)
	 {
	 	  for(int j=1;j<8;j++)
	 	  {
	 		   WebElement z= dr.findElement(By.xpath(x1+i+x2+j+x3)); 
	 		   dates.add(z);
	 	  }
	 } 
	int size= dates.size();
	System.out.println(size);
	for(int i=0;i<size;i++)
	{ 
		String m = dates.get(i).getText();
		if(m.equals(day))
		{
			dates.get(i).click();
		}
	}
	  
  }
  
  public void amount()
  {
	  dr.findElement(By.xpath(rb.getString("amount"))).sendKeys("3500");
  }
  public void focusElement()
  {
	  JavascriptExecutor jse = (JavascriptExecutor)dr;
	  jse.executeScript("arguments[0].scrollIntoView(true);", "");
	
  }
  
  public String Role() throws InterruptedException
  {
	  Thread.sleep(1000);
	  String role=dr.findElement(By.id(rb.getString("Role1"))).getText();
	  System.out.println(role);
	  return(role);
  }
  public void switchRole(String ar)
  {
	  System.out.println("in switch role");
	  Select select = new Select(dr.findElement(By.id(rb.getString("Role"))));
	  select.selectByVisibleText(ar);
  }
  
  public String subStatus()
  {
	  String subStatus= dr.findElement(By.xpath(rb.getString("subStatus"))).getText();
	  System.out.println("Sub Status is : " + subStatus );
	  return(subStatus);
  }
}




