package methods;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class New_Number 
{
WebDriver dr;
ResourceBundle rb;

public New_Number(WebDriver dr)
{
	this.dr=dr;
	rb=ResourceBundle.getBundle("grv2");
}
public void fetch_case()
{
	WebDriverWait wait = new WebDriverWait(dr,60);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(rb.getString("Case_id_xpath"))));
	dr.findElement(By.xpath(rb.getString("Case_id_xpath"))).click();

}
public void edit()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)",dr.findElement(By.xpath(rb.getString("edit_xpath"))) );
	dr.findElement(By.xpath(rb.getString("edit_xpath"))).click();
}
public void forwardee_section() 
{
	dr.findElement(By.xpath(rb.getString("forwardee_section"))).click();
}

public void new_number()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)",dr.findElement(By.xpath(rb.getString("new_no_field"))) );
	dr.findElement(By.xpath(rb.getString("new_no_field"))).sendKeys("100472876");
}

public void forwardee_comment(String ar)
{
	dr.findElement(By.xpath(rb.getString("forwardee1_comment"))).sendKeys(ar);
}
public void decision_picker()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("descision_picker"))));
	dr.findElement(By.xpath(rb.getString("descision_picker"))).click();
}
public void window_handling(WebDriver dr) throws InterruptedException
{
    Thread.sleep(2000);
    dr.switchTo().activeElement();
}
public void desicion_search_box(String decision) throws InterruptedException
{
   for(int i=0;i<10;i++)
  {
	  try
	  {
			  dr.findElement(By.xpath(rb.getString("desicion_search_box"))).sendKeys(decision);
			  Thread.sleep(1000);
			 
			  dr.findElement(By.xpath(rb.getString("search_icon"))).click();
			  Thread.sleep(1000);
			 
			  dr.findElement(By.id(rb.getString("decision"))).click();
			  Thread.sleep(1000);
		 
	  }
	  catch(Exception e)
	  {
		  System.out.println("entered catch");
	  }
  }
 
}
public void desicion_search_box1(String decision) throws InterruptedException
{
   for(int i=0;i<10;i++)
  {
	  try
	  {
		  if(By.xpath(rb.getString("desicion_search_box"))!= null)
		  {	  
			  dr.findElement(By.xpath(rb.getString("desicion_search_box"))).sendKeys(decision);
			 
			  dr.findElement(By.xpath(rb.getString("search_icon"))).click();
			  Thread.sleep(1000);
			 
			  dr.findElement(By.xpath(rb.getString("nb_decision1"))).click();
			  Thread.sleep(1000);
			  
		  }
	  }
	  catch(Exception e)
	  {
		  System.out.println("entered catch");
	  }
  }
 
}

public void save() 
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)",dr.findElement(By.xpath(rb.getString("renewal_save"))));
	dr.findElement(By.xpath(rb.getString("renewal_save"))).click();
}


}
