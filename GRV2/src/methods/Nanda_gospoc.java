package methods;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import base.Browser;

public class Nanda_gospoc extends Browser
{
 WebDriver dr;
 ResourceBundle rb;
  
 public Nanda_gospoc(WebDriver dr)
 {
	 this.dr=dr;
	 rb=ResourceBundle.getBundle("grv2");
 }
 
 public void cases ()
 {
	 Actions action= new Actions(dr);
	 action.moveToElement(dr.findElement(By.xpath(rb.getString("service")))).build().perform();
	 action.moveToElement(dr.findElement(By.xpath(rb.getString("Case")))).click().perform();
	 System.out.println("cases page");
 }
 
 public void comment(String comment)
 {
	 dr.findElement(By.xpath(rb.getString("forwardee1_comment"))).sendKeys(comment);
 }
 public void edit()
 {
	 JavascriptExecutor jse= (JavascriptExecutor)dr;
	 jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("edit_xpath"))));
 	 dr.findElement(By.xpath(rb.getString("edit_xpath"))).click();
 }
 public void fetch()
	{
		dr.findElement(By.xpath(rb.getString("fetch_data"))).click();
	}
 
 public void quick_link()
	{
		dr.findElement(By.xpath(rb.getString("nanda_quick_link"))).click();
	}
 
 public void ticket_search_link()
 {
	 dr.findElement(By.xpath(rb.getString("nanda_tct_search_link"))).click();
 }
 
 public void window_handling(WebDriver dr) throws InterruptedException
 {
     Thread.sleep(2000);
     dr.switchTo().activeElement();
 }
 public void desicion_search_box(String decision) throws InterruptedException
 {
   dr.findElement(By.xpath(rb.getString("desicion_search_box"))).sendKeys(decision);
   dr.findElement(By.xpath(rb.getString("search_icon"))).click();
   Thread.sleep(500);
   dr.findElement(By.id(rb.getString("decision"))).click();
   Thread.sleep(500);
 }

 public void save() 
 {
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("renewal_save"))));
 	dr.findElement(By.xpath(rb.getString("renewal_save"))).click();
 }

public void decision_picker()
{
	JavascriptExecutor jse=(JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("descision_picker"))));
	dr.findElement(By.xpath(rb.getString("descision_picker"))).click();
}

public  void problem_box()
{
	dr.switchTo().frame(0);
	dr.findElement(By.xpath(rb.getString("problembox_xpath"))).sendKeys("nanda problem box");
	dr.switchTo().defaultContent();
} 

}
