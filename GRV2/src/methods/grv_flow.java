package methods;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class grv_flow 
{
public WebDriver dr;
ResourceBundle rb;

public grv_flow(WebDriver dr)
{
this.dr=dr;
rb=ResourceBundle.getBundle("grv2");
}

public void edit()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("edit_xpath"))));
	dr.findElement(By.xpath(rb.getString("edit_xpath"))).click();
}

public void owner_section()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("case_owner_section"))));
	dr.findElement(By.xpath(rb.getString("case_owner_section"))).click();
	
}

public void resolution_ud()
{
    Select select= new Select(dr.findElement(By.xpath(rb.getString("resolution_u/d"))));
    select.selectByVisibleText("Acceptance to response received");
}

public void resolution_by()
{
    Select select= new Select(dr.findElement(By.xpath(rb.getString("resolution_by"))));
    select.selectByVisibleText("Email");
}

public void is_forwarding()
{
    Select select= new Select(dr.findElement(By.xpath(rb.getString("is_forwarding"))));
    select.selectByVisibleText("No");
}

public void acceptance_status()
{
    Select select= new Select(dr.findElement(By.xpath(rb.getString("acceptance_status"))));
    select.selectByVisibleText("Redressed");
}

public void save_proceed() throws InterruptedException
{   
	Thread.sleep(4000);
	JavascriptExecutor js = (JavascriptExecutor)dr;
	js.executeScript("arguments[0].click();", dr.findElement(By.xpath(rb.getString("save_proceed"))));

}

public void new_save() throws InterruptedException
{   
	JavascriptExecutor js = (JavascriptExecutor)dr;
	js.executeScript("arguments[0].click();",dr.findElement(By.xpath(rb.getString("new_save"))));
}

public void resolution_box(String ar) throws InterruptedException {
	WebDriverWait wait = new WebDriverWait(dr, 5000);
	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(1));
	Thread.sleep(2000);
	dr.findElement(By.xpath(rb.getString("resolutionbox_xpath"))).sendKeys(ar);
	dr.switchTo().defaultContent();
}

public String ticket_status()
{
	String status=dr.findElement(By.xpath(rb.getString("tct_status"))).getText();
	System.out.println("Status is : "+status);
	return(status);
}
public void new_no_policy_status()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("new_policy_status_picker"))));
	dr.findElement(By.xpath(rb.getString("new_policy_status_picker"))).click();
}


public void new_no_status(String decision) throws InterruptedException
{
  WebDriverWait wait= new WebDriverWait(dr,60);
  Thread.sleep(600);
  dr.switchTo().activeElement();
  wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.id(rb.getString("decision_search_box1")))));
  dr.findElement(By.id(rb.getString("decision_search_box1"))).sendKeys(decision);
  dr.findElement(By.xpath(rb.getString("search_icon"))).click();
  wait.until(ExpectedConditions.visibilityOf( dr.findElement(By.xpath(rb.getString("new_status")))));
  for(int i=0;i<6;i++)
  {
	  try
	  {
		  dr.findElement(By.xpath(rb.getString("new_status"))).click();
		  Thread.sleep(500);
	  }
	  catch(Exception e)
	  {
		  System.out.println("entered catch of new status");
	  }
  }
 }

public void new_no_status_1(String decision) throws InterruptedException
{
  dr.switchTo().activeElement();
  Thread.sleep(1000);
  dr.findElement(By.id(rb.getString("decision_search_box1"))).sendKeys(decision);
  dr.findElement(By.xpath(rb.getString("search_icon"))).click();
  Thread.sleep(1000);
  dr.findElement(By.xpath(rb.getString("new_status_1"))).click();
  Thread.sleep(1000);
}


public void backend_arrow() throws InterruptedException
{
	dr.findElement(By.xpath(rb.getString("backend_arrow"))).click();;
	
}

public void fetch_case()
{
	WebDriverWait wait = new WebDriverWait(dr,60);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(rb.getString("Case_id_xpath"))));
	dr.findElement(By.xpath(rb.getString("Case_id_xpath"))).click();

}

public void required_premium(String amount)
{
	dr.findElement(By.xpath(rb.getString("required_premium"))).sendKeys(amount);
}

public void case_history(String ar)
{
	dr.findElement(By.xpath(rb.getString("case_history"))).sendKeys(ar);
}
public void delay(String ar)
{
	dr.findElement(By.xpath(rb.getString("delay"))).sendKeys(ar);
}
public void went_wrong(String ar)
{
	dr.findElement(By.xpath(rb.getString("went_wrong"))).sendKeys(ar);
}
public void is_recovery(String ar)
{
	dr.findElement(By.xpath(rb.getString("is_recovery"))).sendKeys(ar);
}
public void future_recourse(String ar)
{
	dr.findElement(By.xpath(rb.getString("future_recourse"))).sendKeys(ar);
}
public void CaseOwnerIRDA()
{
   dr.findElement(By.id(rb.getString("CaseOwnerIRDA"))).sendKeys("case owner comments...");	
}
public void noActionRequired()
{
  Select select= new Select(dr.findElement(By.id(rb.getString("noActionRequired"))));
  select.selectByValue("Yes");
}
public void AmtInWords()
{
  dr.findElement(By.id(rb.getString("AmtInWords"))).sendKeys("amt in words");
}

public void nongrvSection()
{
  dr.findElement(By.xpath(rb.getString("nongrvSection"))).click();	
}
}
