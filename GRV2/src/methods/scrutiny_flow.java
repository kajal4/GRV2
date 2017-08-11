package methods;

import java.util.List;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class scrutiny_flow 
{
public WebDriver dr;
ResourceBundle rb;

public scrutiny_flow (WebDriver dr)
{
 this.dr = dr;
 rb= ResourceBundle.getBundle("grv2");
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
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("edit_xpath"))));
	dr.findElement(By.xpath(rb.getString("edit_xpath"))).click();
}

public void scrutiny_section()
{
	dr.findElement(By.xpath(rb.getString("scrutiny_section"))).click();
}
public void cd(String cd) throws InterruptedException 
{
	dr.findElement(By.xpath(rb.getString("cd_xpath"))).sendKeys(cd);
	Thread.sleep(800);
	WebDriverWait wait= new WebDriverWait(dr, 3000);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.id(rb.getString("AutoPopulateCD")))));
	dr.findElement(By.xpath(rb.getString("cd_xpath"))).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
}
public void rca_comment(String ar)
{
	dr.findElement(By.xpath(rb.getString("RCA_comment"))).sendKeys(ar);	
}

public void scrutiny_save()
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("save_proceed"))));
	dr.findElement(By.xpath(rb.getString("save_proceed"))).click();
}

public void refresh() throws InterruptedException
{
	Thread.sleep(5000);
	dr.navigate().refresh();
	
}

public void rca_picker() throws InterruptedException
{
	JavascriptExecutor jse= (JavascriptExecutor)dr;
	jse.executeScript("arguments[0].scrollIntoView(true)", dr.findElement(By.xpath(rb.getString("rca_picker"))));
	dr.findElement(By.xpath(rb.getString("rca_picker"))).click();
	WebDriverWait wait= new WebDriverWait(dr, 4000);
	Thread.sleep(2000);
	dr.switchTo().activeElement();
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(rb.getString("rca1")))));
	dr.findElement(By.xpath(rb.getString("rca1"))).click();

}


public void rca_type(String ar) throws InterruptedException
{
	dr.findElement(By.xpath(rb.getString("rca_type"))).sendKeys(ar);
	WebDriverWait wait= new WebDriverWait(dr, 80);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.id("ui-id-25"))));
	dr.findElement(By.xpath(rb.getString("rca_type"))).sendKeys(Keys.DOWN,Keys.ENTER);

}

public void resolution_code(String ar) throws InterruptedException
{
	dr.findElement(By.xpath(rb.getString("resolution_code"))).sendKeys(ar);
	Thread.sleep(1000);
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.visibilityOf(dr.findElement(By.id("ui-id-16"))));
	dr.findElement(By.xpath(rb.getString("resolution_code"))).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
}

public void go_code_picker() throws InterruptedException
{   
	dr.findElement(By.xpath(rb.getString("go_code_picker"))).click();
	Thread.sleep(1000);
	dr.switchTo().activeElement();
	Thread.sleep(1000);
	WebDriverWait wait= new WebDriverWait(dr, 60);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.id(rb.getString("decision")))));
	dr.findElement(By.id(rb.getString("decision"))).click();
}

public void policy_issue_date()
{
	dr.findElement(By.xpath(rb.getString("policy_issue_date"))).click();
}

public void policy_issue_date1(String date,String month, String Year)
{
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(rb.getString("Calendar1")))));
	
	dr.findElement(By.xpath(rb.getString("Calendar1"))).click();
	Select month1= new Select(dr.findElement(By.xpath(rb.getString("Month"))));
	month1.selectByVisibleText(month);
	
	Select yr=new Select(dr.findElement(By.xpath(rb.getString("Year"))));
	yr.selectByVisibleText(Year);
	
	List<WebElement> dates = dr.findElements(By.xpath(rb.getString("Dates")));
	for(WebElement k : dates)
	{
		if((k.getText().equalsIgnoreCase(date)))
				{
			      k.click();
				}
	}

}



public void incorrect_info()
{
	dr.findElement(By.xpath(rb.getString("incorrect_info"))).sendKeys("incorrect info");
}
public void correct_info()
{
	dr.findElement(By.xpath(rb.getString("correct_info"))).sendKeys("correct info");
}
public String impact()
{
return(dr.findElement(By.xpath(rb.getString("impact"))).getText());	
}

public String policy_status()
{
	return(dr.findElement(By.xpath(rb.getString("policy_status"))).getText());	

}

public void type_of_error(String ar)
{
	Select select = new Select(dr.findElement(By.id(rb.getString("type_of_error_id"))));
	select.selectByVisibleText(ar);
}

public void correct_mode()
{
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.visibilityOf(dr.findElement(By.id(rb.getString("correct_mode")))));
	System.out.println("wait passed");
	Select select = new Select(dr.findElement(By.id(rb.getString("correct_mode"))));
	select.selectByValue("01");
}

public void IRDAProb(String ar)
{
  dr.findElement(By.id(rb.getString("IRDAProblem"))).sendKeys(ar);
}

public void Reason()
{
	dr.findElement(By.id(rb.getString("Reason"))).sendKeys("reason field comments...");
}
}
