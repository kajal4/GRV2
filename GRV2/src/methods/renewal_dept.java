package methods;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class renewal_dept
{
public WebDriver dr;
ResourceBundle rb;

public renewal_dept(WebDriver dr)
{
this.dr=dr;
rb=ResourceBundle.getBundle("grv2");
}
public void renewal_arrow() throws InterruptedException
{
	dr.findElement(By.xpath(rb.getString("renewal_arrow"))).click();;
	
}

public void fetch_case()
{
	WebDriverWait wait = new WebDriverWait(dr,80);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(rb.getString("Caseid_xpath"))));
	dr.findElement(By.xpath(rb.getString("Caseid_xpath"))).click();

}
public void edit() throws InterruptedException 
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("edit_xpath"))));
	dr.findElement(By.xpath(rb.getString("edit_xpath"))).click();
	Thread.sleep(500);
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
  dr.findElement(By.xpath(rb.getString("desicion_search_box"))).sendKeys(decision);
  dr.findElement(By.xpath(rb.getString("search_icon"))).click();
  Thread.sleep(1000);
//  WebDriverWait wt= new WebDriverWait(dr, 60);
//  wt.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(rb.getString("decision")))));
  System.out.println("working......");
  dr.findElement(By.id(rb.getString("decision"))).click();
  Thread.sleep(500);
}

public void save() 
{
	dr.findElement(By.xpath(rb.getString("renewal_save"))).click();
}

}
