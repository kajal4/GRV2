package methods;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Policy_360 {

	WebDriver dr;
	ResourceBundle rb;
	
	public Policy_360(WebDriver dr)
	{
		this.dr=dr;
		rb=  ResourceBundle.getBundle("grv2");
		
	}
	
	public void csrequest()
	{
		dr.findElement(By.xpath(rb.getString("Flow_icon_xpath"))).click();
		dr.findElement(By.xpath(rb.getString("cs_request_xpath"))).click();
	}
	
   public String agent_status()
   {
	   return(dr.findElement(By.xpath(rb.getString("agent_status_xpath"))).getText());
   }
   public String cust_class()
   {
	   return(dr.findElement(By.xpath(rb.getString("cust_classification"))).getText());
   }
   
   public void return_to_policy()
	{
	   dr.switchTo().activeElement();
	   dr.findElement(By.xpath(rb.getString("policy_popup_cross"))).click();
	}
	 public String return_flag()
	 {
		String flag=(dr.findElement(By.xpath(rb.getString("return_mail_flag"))).getText());
		if(flag.equalsIgnoreCase("Yes")||flag.equalsIgnoreCase("No"))
		{
			return(flag);
		}
		else
		{
			return("No value");
		}
	 }
   
	
}
