package testcases;

import java.sql.SQLException;

import methods.CSE_Screen_Flow;
import methods.Nanda_gospoc;
import methods.Policy_360;
import methods.Policy_Search;
import methods.Ticket_search_flow;
import methods.grv_flow;
import methods.login;
import methods.scrutiny_flow;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Browser;

public class Processing_delay_proposal extends Browser
{
@Test (enabled=false)
public void GRV2_TS011 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	login lg = new login (dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000003632");  
	ps.fetch();
	ps.check_mark();
	ps.validate();
	ps.popup_handling();
    Policy_360 ps1= new Policy_360(dr);
    String return_flag= ps1.return_flag();
    System.out.println(return_flag);
    if(return_flag.equalsIgnoreCase("Yes"))
     {
 	   ps1.return_to_policy();
     }
    String cust = ps1.cust_class();
    System.out.println(cust);
    String Agent_status = ps1.agent_status();
	System.out.println(Agent_status);
	ps1.csrequest();
    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
    cse.window_handling(dr);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	try
    {
	  Boolean el = dr.findElement(By.xpath("html/body/div[1]/div[4]/div/ul/li[2]/a/span")).isEnabled();
	  System.out.println(el);
	}
	catch(Exception e)
	 {
	   dr.switchTo().alert().accept();
	 }
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println(case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println(assigned_to);
	System.out.println(case_owner);
    lg.logout();
	
    String abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	String role=cse.Role();
	System.out.println(role);
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts= new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Proposal papers submitted but misplaced by Insurer");
	sc.cd("Proposal papers submitted but misplaced by Insurer");
	sc.rca_comment("comments by scrutiny....");
	sc.IRDAProb("scrutiny IRDA section");
	sc.go_code_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println(assigned_to_1);
	String assigned_to_2=cse.assigned_to();
	System.out.println(assigned_to_2);
	String Policy_status =sc.policy_status();
	System.out.println("Policy status is "+ Policy_status);
	Assert.assertNotSame(Policy_status, "Premium paying", "Policy status should be premium paying");
	Thread.sleep(2000);
	lg.logout();

	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Nanda_gospoc go= new Nanda_gospoc(dr);
	go.cases();
	ts.Ticketsearchlink2();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	go.edit();
	go.comment("comments...");
	go.decision_picker();
	go.window_handling(dr);
	go.desicion_search_box("Accept");
	cse.problem_box("go problem box comment");
	go.save();
	Thread.sleep(2000);
	String case_id1=cse.case_id();
	System.out.println(case_id1);
	String assigned_to1=cse.assigned_to();
	String case_owner1=cse.Case_owner();
	System.out.println(assigned_to1);
	System.out.println(case_owner1);
    lg.logout();
	
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
    
	grv_flow grv= new grv_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	grv.owner_section();
	grv.resolution_ud();
	grv.resolution_by();
	grv.acceptance_status();
	grv.is_forwarding();
	grv.resolution_box("resolution comments....");
	grv.CaseOwnerIRDA();
	grv.save_proceed();
}

@Test 
public void GRV2_TS023 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	login lg = new login (dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000003632");  
	ps.fetch();
	ps.check_mark();
	ps.validate();
	ps.popup_handling();
    Policy_360 ps1= new Policy_360(dr);
    String return_flag= ps1.return_flag();
    System.out.println(return_flag);
    if(return_flag.equalsIgnoreCase("Yes"))
     {
 	   ps1.return_to_policy();
     }
    String cust = ps1.cust_class();
    System.out.println(cust);
    String Agent_status = ps1.agent_status();
	System.out.println(Agent_status);
	ps1.csrequest();
    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
    cse.window_handling(dr);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Error in policy pack");
	cse.next_button();
	Thread.sleep(2000);
	try
    {
	  Boolean el = dr.findElement(By.xpath("html/body/div[1]/div[4]/div/ul/li[2]/a/span")).isEnabled();
	  System.out.println(el);
	}
	catch(Exception e)
	 {
	   dr.switchTo().alert().accept();
	 }
	
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println(case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println(assigned_to);
	System.out.println(case_owner);
    lg.logout();
	
    String abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	String role=cse.Role();
	System.out.println(role);
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts= new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Proposal papers submitted but misplaced by Insurer");
	sc.cd("Proposal papers submitted but misplaced by Insurer");
	sc.rca_comment("comments by scrutiny....");
	sc.rca_type("Incorrect appointee name");
	sc.IRDAProb("scrutiny IRDA comments");
	sc.go_code_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println(assigned_to_1);
	String assigned_to_2=cse.assigned_to();
	System.out.println(assigned_to_2);
	String Policy_status =sc.policy_status();
	System.out.println("Policy status is "+ Policy_status);
	Assert.assertNotSame(Policy_status, "Premium paying", "Policy status should be premium paying");
	Thread.sleep(2000);
	lg.logout();

	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Nanda_gospoc go= new Nanda_gospoc(dr);
	go.cases();
	ts.Ticketsearchlink2();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	go.edit();
	go.comment("comments...");
	go.decision_picker();
	go.window_handling(dr);
	go.desicion_search_box("Accept");
	cse.problem_box("go problem box comment");
	go.save();
	Thread.sleep(2000);
	String case_id1=cse.case_id();
	System.out.println(case_id1);
	String assigned_to1=cse.assigned_to();
	String case_owner1=cse.Case_owner();
	System.out.println(assigned_to1);
	System.out.println(case_owner1);
    lg.logout();
	
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
    
	grv_flow grv= new grv_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	grv.owner_section();
	grv.resolution_ud();
	grv.resolution_by();
	grv.acceptance_status();
	grv.is_forwarding();
	grv.resolution_box("resolution comments....");
	grv.CaseOwnerIRDA();
	grv.save_proceed();
}

//*****************************************************************

@Test 
public void GRV2_TS044 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	login lg = new login (dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000003632");  
	ps.fetch();
	ps.check_mark();
	ps.validate();
	ps.popup_handling();
    Policy_360 ps1= new Policy_360(dr);
    String return_flag= ps1.return_flag();
    System.out.println(return_flag);
    if(return_flag.equalsIgnoreCase("Yes"))
     {
 	   ps1.return_to_policy();
     }
    String cust = ps1.cust_class();
    System.out.println(cust);
    String Agent_status = ps1.agent_status();
	System.out.println(Agent_status);
	ps1.csrequest();
    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
    cse.window_handling(dr);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	try
    {
	  Boolean el = dr.findElement(By.xpath("html/body/div[1]/div[4]/div/ul/li[2]/a/span")).isEnabled();
	  System.out.println(el);
	}
	catch(Exception e)
	 {
	   dr.switchTo().alert().accept();
	 }
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println(case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println(assigned_to);
	System.out.println(case_owner);
    lg.logout();
	
    String abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	String role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Scrutiny"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts= new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Proposal papers submitted but misplaced by Insurer");
	sc.cd("Not Applicable");
	sc.rca_comment("comments by scrutiny....");
	sc.IRDAProb("scrutiny IRDA section");
	sc.go_code_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println(assigned_to_1);
	String assigned_to_2=cse.assigned_to();
	System.out.println(assigned_to_2);
	String Policy_status =sc.policy_status();
	System.out.println("Policy status is "+ Policy_status);
	Assert.assertNotSame(Policy_status, "Premium paying", "Policy status should be premium paying");
	Thread.sleep(2000);
	lg.logout();

	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Nanda_gospoc go= new Nanda_gospoc(dr);
	go.cases();
	ts.Ticketsearchlink2();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	go.edit();
	go.comment("comments...");
	go.decision_picker();
	go.window_handling(dr);
	go.desicion_search_box("Accept");
	cse.problem_box("go problem box comment");
	go.save();
	Thread.sleep(2000);
	String case_id1=cse.case_id();
	System.out.println(case_id1);
	String assigned_to1=cse.assigned_to();
	String case_owner1=cse.Case_owner();
	System.out.println(assigned_to1);
	System.out.println(case_owner1);
    lg.logout();
	
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Non-Grievance Executive"))
	{
		cse.switchRole("Non-Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	grv_flow grv= new grv_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	grv.nongrvSection();
	grv.acceptance_status();
	grv.is_forwarding();
	grv.resolution_box("resolution comments....");
	grv.save_proceed();
}

@Test 
public void GRV2_TS055 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	login lg = new login (dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000003632");  
	ps.fetch();
	ps.check_mark();
	ps.validate();
	ps.popup_handling();
    Policy_360 ps1= new Policy_360(dr);
    String return_flag= ps1.return_flag();
    System.out.println(return_flag);
    if(return_flag.equalsIgnoreCase("Yes"))
     {
 	   ps1.return_to_policy();
     }
    String cust = ps1.cust_class();
    System.out.println(cust);
    String Agent_status = ps1.agent_status();
	System.out.println(Agent_status);
	ps1.csrequest();
    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
    cse.window_handling(dr);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Error in policy pack");
	cse.next_button();
	Thread.sleep(2000);
	try
    {
	  Boolean el = dr.findElement(By.xpath("html/body/div[1]/div[4]/div/ul/li[2]/a/span")).isEnabled();
	  System.out.println(el);
	}
	catch(Exception e)
	 {
	   dr.switchTo().alert().accept();
	 }
	
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println(case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println(assigned_to);
	System.out.println(case_owner);
    lg.logout();
	
    String abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	String role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Scrutiny"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts= new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Proposal papers submitted but misplaced by Insurer");
	sc.cd("Not Applicable");
	sc.rca_comment("comments by scrutiny....");
	sc.rca_type("Incorrect appointee name");
	sc.IRDAProb("scrutiny IRDA comments");
	sc.go_code_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println(assigned_to_1);
	String assigned_to_2=cse.assigned_to();
	System.out.println(assigned_to_2);
	String Policy_status =sc.policy_status();
	System.out.println("Policy status is "+ Policy_status);
	Assert.assertNotSame(Policy_status, "Premium paying", "Policy status should be premium paying");
	Thread.sleep(2000);
	lg.logout();

	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Nanda_gospoc go= new Nanda_gospoc(dr);
	go.cases();
	ts.Ticketsearchlink2();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	go.edit();
	go.comment("comments...");
	go.decision_picker();
	go.window_handling(dr);
	go.desicion_search_box("Accept");
	cse.problem_box("go problem box comment");
	go.save();
	Thread.sleep(2000);
	String case_id1=cse.case_id();
	System.out.println(case_id1);
	String assigned_to1=cse.assigned_to();
	String case_owner1=cse.Case_owner();
	System.out.println(assigned_to1);
	System.out.println(case_owner1);
    lg.logout();
	
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Non-Grievance Executive"))
	{
		cse.switchRole("Non-Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	grv_flow grv= new grv_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	grv.nongrvSection();
	grv.acceptance_status();
	grv.is_forwarding();
	grv.resolution_box("resolution comments....");
	grv.save_proceed();
}

}

// Refactored on 26 jul'17
