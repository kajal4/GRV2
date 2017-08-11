package methods;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Forwardee_dept_flow
{
  public WebDriver dr;
  ResourceBundle rb;
  
  public Forwardee_dept_flow(WebDriver dr)
  {
	  this.dr=dr;
	  rb= ResourceBundle.getBundle("grv2");
  }

 public void edit()
 {
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("edit_xpath"))));
	dr.findElement(By.xpath(rb.getString("edit_xpath"))).click();
 }
  
 public void forwardee_section()
 {
	dr.findElement(By.xpath(rb.getString("forwardee_section"))).click();
 }

public void forwardee_comment(String ar)
{
	dr.findElement(By.xpath(rb.getString("forwardee1_comment"))).sendKeys(ar);
}
public void decision_picker()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)",dr.findElement(By.xpath(rb.getString("descision_picker"))));
	dr.findElement(By.xpath(rb.getString("descision_picker"))).click();
}
public void window_handling(WebDriver dr) throws InterruptedException
{
    Thread.sleep(2000);
    dr.switchTo().activeElement();
    Thread.sleep(2000);
}
public void desicion_search_box(String decision) throws InterruptedException
{
  WebDriverWait wt= new  WebDriverWait(dr, 4000);
  wt.until(ExpectedConditions.visibilityOf(dr.findElement(By.id(rb.getString("decision_search_box1")))));
  dr.findElement(By.id(rb.getString("decision_search_box1"))).sendKeys(decision);
  dr.findElement(By.xpath(rb.getString("search_icon"))).click();
  wt.until(ExpectedConditions.visibilityOf(dr.findElement(By.id(rb.getString("decision")))));
  for(int i=0;i<5;i++)
  {
	  try
	  {
		  WebElement el=dr.findElement(By.id(rb.getString("decision")));
		  if(el.getText()!= null)
		  {	  
		  dr.findElement(By.id(rb.getString("decision"))).click();
		  Thread.sleep(500);
		  }
	  }
	  catch(Exception e)
	  {
		  Thread.sleep(1000);
		  System.out.println("entered catch");
	  }
  }
 
}
public void save() 
{
	dr.findElement(By.xpath(rb.getString("renewal_save"))).click();
}
public void fetch_case()
{
	dr.findElement(By.xpath(rb.getString("Case_id_xpath"))).click();
}

public void forwardee3_comment()
{
	dr.findElement(By.xpath(rb.getString("forwardee3_comment"))).sendKeys("forwardee3 comment.....");
}

public void decision3_picker()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("forwardee3_picker"))));
	dr.findElement(By.xpath(rb.getString("forwardee3_picker"))).click();
}

public void forwardee3_desicion_search(String decision) throws InterruptedException
{
  Thread.sleep(500);
  dr.switchTo().activeElement();
  Thread.sleep(1200);
  dr.findElement(By.id(rb.getString("decision_search_box1"))).sendKeys(decision);
  dr.findElement(By.xpath(rb.getString("search_icon"))).click();
  Thread.sleep(500);
  dr.findElement(By.id(rb.getString("decision"))).click();
  Thread.sleep(500);
}

public void forwardee2_comment(String ar)
{
	dr.findElement(By.xpath(rb.getString("forwardee2_comment"))).sendKeys(ar);
}
public void decision2_picker()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("forwardee2_picker"))));
	dr.findElement(By.xpath(rb.getString("forwardee2_picker"))).click();
}

public void forwardee4_comment(String ar)
{
	dr.findElement(By.xpath(rb.getString("forwardee4_comment"))).sendKeys(ar);
}
public void decision4_picker()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("forwardee4_picker"))));
	dr.findElement(By.xpath(rb.getString("forwardee4_picker"))).click();
}

public void save1() 
{
	WebElement element= dr.findElement(By.xpath(rb.getString("renewal_save")));
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].click();", element);
}

public void forwardee3_nb_desicion_search(String decision) throws InterruptedException
{
  Thread.sleep(500);
  dr.switchTo().activeElement();
  Thread.sleep(500);
  dr.findElement(By.id(rb.getString("decision_search_box1"))).sendKeys(decision);
  dr.findElement(By.xpath(rb.getString("search_icon"))).click();
  Thread.sleep(800);
  dr.findElement(By.xpath(rb.getString("nb_decision"))).click();
  Thread.sleep(500);
}

public void cross_popup()
{
 dr.findElement(By.xpath(rb.getString("policy_popup_cross"))).click();	
}

public void AmtSuspenseAcc(String ar)
{
  Select select= new Select(dr.findElement(By.id(rb.getString("AmtComesToSuspenseAccount"))));
  select.selectByValue(ar);
}

public void amtAdded(String ar)
{
 dr.findElement(By.id(rb.getString("amtAdded"))).sendKeys(ar);	
}
}
