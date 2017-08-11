package methods;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Approver 
{
WebDriver dr;
ResourceBundle rb;

public Approver(WebDriver dr)
{
this.dr=dr;
rb= ResourceBundle.getBundle("grv2");
}
public void cases () throws InterruptedException
{
	 Actions action= new Actions(dr);
	 action.moveToElement(dr.findElement(By.xpath(rb.getString("service")))).build().perform();
	 action.moveToElement(dr.findElement(By.xpath(rb.getString("Case")))).click().perform();
	 Thread.sleep(2000);
}
public void edit()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("edit_xpath"))));
	dr.findElement(By.xpath(rb.getString("edit_xpath"))).click();
}

public void fetch_case()
{
	dr.findElement(By.xpath(rb.getString("Case_id_xpath"))).click();
}

public void approver_comment()
{
	dr.findElement(By.xpath(rb.getString("approver_comment"))).sendKeys("approver comments...");
}

public void approver_decision(String ar)
{
	Select select= new Select(dr.findElement(By.xpath(rb.getString("approver_decision"))));
	select.selectByVisibleText(ar);
}
public void save() 
{
	dr.findElement(By.xpath(rb.getString("save_proceed"))).click();
}
}
