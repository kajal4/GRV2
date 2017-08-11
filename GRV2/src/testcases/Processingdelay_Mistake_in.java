package testcases;

import java.sql.SQLException;

import methods.Approver;
import methods.CSE_Screen_Flow;
import methods.New_Number;
import methods.Policy_360;
import methods.Policy_Search;
import methods.Ticket_search_flow;
import methods.Forwardee_dept_flow;
import methods.grv_flow;
import methods.login;
import methods.scrutiny_flow;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Browser;

public class Processingdelay_Mistake_in extends Browser
{
@ Test (priority=1)
public void GRV2_TS012 () throws InterruptedException, SQLException
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
	ps.policy_number_text("000003632");       // for policy status : premium paying
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box.....problem box.....box....");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts = new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.policy_issue_date();
	sc.Reason();
	sc.cd("Mistake in Date of Commencement (DOC).");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny irda comments");
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
		Thread.sleep(2000);
	}
	System.out.println(assigned_to_1);
	String assigned_to_2=cse.assigned_to();
	System.out.println(assigned_to_2);
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	nb.forwardee_comment("new number dept comment");
	nb.decision_picker();
	nb.window_handling(dr);
	nb.desicion_search_box("New Number Taken");
	nb.save();
	Thread.sleep(2000);
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

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	Thread.sleep(1000);
	JavascriptExecutor jse = (JavascriptExecutor)dr;
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
	Forwardee_dept_flow fin= new Forwardee_dept_flow(dr);
	role=cse.Role();
	if(role!="Finance RFP")
	{
		cse.switchRole("Finance RFP");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
//	WebDriverWait wt1= new WebDriverWait(dr, 60);
//	wt1.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	fin.edit();
	fin.forwardee_section();
	fin.forwardee3_comment();
	fin.decision3_picker();
	fin.forwardee3_desicion_search("Accept");
	fin.save();
	String assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
	role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
	
	assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Thread.sleep(800);
	ps1.return_to_policy();
	pos.edit();
	pos.forwardee_section();
	pos.forwardee4_comment("pos comments here...");
	pos.decision4_picker();
	pos.forwardee3_desicion_search("Accept");
	pos.save();
	
	assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
	role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_ud();
	grv.resolution_by();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.CaseOwnerIRDA();
	grv.save_proceed();
}

// When impact on age is no       
@Test  (priority=3)
public void GRV2_TS014 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	JavascriptExecutor jse = (JavascriptExecutor)dr;
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts = new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.Reason();
	sc.policy_issue_date1("28","Jan","2013");
	sc.cd("Mistake in Date of Commencement (DOC).");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny IRDA comments...");
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	
	Forwardee_dept_flow poscf = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	poscf.edit();
	Thread.sleep(8000);
	poscf.forwardee_section();
	poscf.forwardee_comment("poscf comments...");
	JavascriptExecutor js= (JavascriptExecutor)dr;
	js.executeScript("window.scrollBy(0,200)", "");
	poscf.decision_picker();
	poscf.window_handling(dr);
	poscf.desicion_search_box("Accept");
	poscf.save1();
	Thread.sleep(2000);
	assigned_to=cse.assigned_to();
	case_id=cse.case_id();
	System.out.println(case_id);
	case_owner=cse.Case_owner();
	System.out.println(assigned_to);
	System.out.println(case_owner);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	Forwardee_dept_flow nb1= new Forwardee_dept_flow(dr);
	nb1.forwardee2_comment("new number dept comment");
	nb1.decision2_picker();
	nb1.forwardee3_nb_desicion_search("New Number Taken");
	nb1.save();
	Thread.sleep(2000);
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
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
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
	

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	Thread.sleep(1000);
	
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium "
			+ "Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
	Forwardee_dept_flow fin= new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt1= new WebDriverWait(dr, 60);
	wt1.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));	fin.edit();
	fin.forwardee_section();
	fin.forwardee3_comment();
	fin.decision3_picker();
	fin.forwardee3_desicion_search("Accept");
	fin.save();
	String assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
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
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
	
	assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Thread.sleep(800);
	ps1.return_to_policy();
	pos.edit();
	pos.forwardee_section();
	pos.forwardee4_comment("pos comments here...");
	pos.decision4_picker();
	pos.forwardee3_desicion_search("Accept");
	pos.save();
	
	assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
	role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_ud();
	grv.resolution_by();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.CaseOwnerIRDA();
	grv.save_proceed();

}


@Test (priority=5) // when policy status is not premium paying
public void GRV2_TS015 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	JavascriptExecutor jse = (JavascriptExecutor)dr;
    login lg = new login (dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000001396");  
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts = new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.Reason();
	sc.policy_issue_date1("2","Dec","2015");
	sc.cd("Mistake in Date of Commencement (DOC).");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny IRDA comments...");
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	
	Forwardee_dept_flow poscf = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	poscf.edit();
	Thread.sleep(8000);
	poscf.forwardee_section();
	poscf.forwardee_comment("poscf comments...");
	JavascriptExecutor js= (JavascriptExecutor)dr;
	js.executeScript("window.scrollBy(0,200)", "");
	poscf.decision_picker();
	poscf.window_handling(dr);
	poscf.desicion_search_box("Accept");
	poscf.save1();
	Thread.sleep(2000);
	assigned_to=cse.assigned_to();
	case_id=cse.case_id();
	System.out.println(case_id);
	case_owner=cse.Case_owner();
	System.out.println(assigned_to);
	System.out.println(case_owner);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	Forwardee_dept_flow nb1= new Forwardee_dept_flow(dr);
	nb1.forwardee2_comment("new number dept comment");
	nb1.decision2_picker();
	nb1.forwardee3_nb_desicion_search("New Number Taken");
	nb1.save();
	Thread.sleep(2000);
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
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
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

	
	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	Thread.sleep(1000);
	
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium "
			+ "Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
	Forwardee_dept_flow fin= new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt1= new WebDriverWait(dr, 60);
	wt1.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));	fin.edit();
	fin.forwardee_section();
	fin.forwardee3_comment();
	fin.decision3_picker();
	fin.forwardee3_desicion_search("Accept");
	fin.save();
	String assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
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
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");

	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
	
	assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Thread.sleep(800);
	ps1.return_to_policy();
	pos.edit();
	pos.forwardee_section();
	pos.forwardee4_comment("pos comments here...");
	pos.decision4_picker();
	pos.forwardee3_desicion_search("Accept");
	pos.save();
	
	assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
	role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_ud();
	grv.resolution_by();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.CaseOwnerIRDA();
	grv.save_proceed();

}


@ Test (enabled=false)
public void GRV2_TS016 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
    login lg = new login (dr);
    Ticket_search_flow ts = new Ticket_search_flow(dr);
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.policy_issue_date();
	sc.Reason();
	sc.cd("Mistake in Date of Commencement (DOC).");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny IRDA comments...");
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	nb.forwardee_comment("new number dept comment");
	nb.decision_picker();
	nb.window_handling(dr);
	nb.desicion_search_box("New Number Taken");
	nb.save();
	Thread.sleep(2000);
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
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
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

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	Thread.sleep(1000);
	JavascriptExecutor jse = (JavascriptExecutor)dr;
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Rejected");
    apr.save();
    Thread.sleep(1000);
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
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_ud();
	grv.resolution_by();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.CaseOwnerIRDA();
	grv.save_proceed();
}

@ Test (enabled=false) // policy having plan type: trad
public void GRV2_TS017 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
    login lg = new login (dr);
    Ticket_search_flow ts = new Ticket_search_flow(dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000006619");     // pass policy number having policy type :"Trad"
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.policy_issue_date();
	sc.cd("Mistake in Date of Commencement (DOC).");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny IRDA comments");
	sc.Reason();
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Thread.sleep(6000);
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	Forwardee_dept_flow nb1= new Forwardee_dept_flow(dr);
	nb1.forwardee_comment("new number comments...");
	nb1.decision_picker();
	nb1.forwardee3_nb_desicion_search("New Number Taken");
	Thread.sleep(6000);
	nb.save();
	Thread.sleep(2000);
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
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
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

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	Thread.sleep(1000);
	JavascriptExecutor jse = (JavascriptExecutor)dr;
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    
    Forwardee_dept_flow rfp= new Forwardee_dept_flow(dr);
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
    ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
    rfp.edit();
    rfp.forwardee_section();
    rfp.forwardee3_comment();
    rfp.decision3_picker();
    rfp.forwardee3_desicion_search("Accept");
    rfp.save1();
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
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_ud();
	grv.resolution_by();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.CaseOwnerIRDA();
	grv.save_proceed();
}
	


@ Test 
public void GRV2_TS018 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
    login lg = new login (dr);
    Ticket_search_flow ts = new Ticket_search_flow(dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000003632");     // pass policy number having policy type :"ULIP"
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.policy_issue_date();
	sc.cd("Mistake in Date of Commencement (DOC).");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.Reason();
	sc.IRDAProb("scrutiny IRDA comments..");
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
	String Policy_status =sc.policy_status();
	System.out.println("Policy status is "+ Policy_status);
	Assert.assertNotSame(Policy_status, "Premium paying", "Policy status should be premium paying");
	Thread.sleep(2000);
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
//	JOptionPane.showMessageDialog(null,"Wait for some time js is getting load");
//	Thread.sleep(8000);
	nb.new_number(); 
	Thread.sleep(6000);
	Forwardee_dept_flow nb1= new Forwardee_dept_flow(dr);
	nb1.forwardee_comment("new number comments...");
	nb1.decision_picker();
	nb1.forwardee3_nb_desicion_search("New Number Taken");
	nb.save();
	Thread.sleep(2000);
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
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

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	Thread.sleep(1000);
	JavascriptExecutor jse = (JavascriptExecutor)dr;
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    
    Forwardee_dept_flow rfp= new Forwardee_dept_flow(dr);
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
    ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
    rfp.edit();
    rfp.forwardee_section();
    rfp.forwardee3_comment();
    rfp.decision3_picker();
    rfp.forwardee3_desicion_search("Accept");
    rfp.save1();
    lg.logout();
   
    abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	grv.save_proceed();
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
	pos.cross_popup();
	pos.edit();
	pos.forwardee_section();
	pos.forwardee4_comment("pos comments...");
	pos.decision4_picker();
	pos.forwardee3_desicion_search("Accept");
	pos.save1();
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
	try
	{
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	grv.owner_section();
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_ud();
	grv.resolution_by();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.CaseOwnerIRDA();
	grv.save_proceed();
  }

//************************************************************************************

@ Test  (priority=2)
public void GRV2_TS045 () throws InterruptedException, SQLException
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
	ps.policy_number_text("000003632");       // for policy status : premium paying
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box.....problem box.....box....");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts = new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.policy_issue_date();
	sc.Reason();
	sc.cd("Not Applicable");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny irda comments");
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
		Thread.sleep(2000);
	}
	System.out.println(assigned_to_1);
	String assigned_to_2=cse.assigned_to();
	System.out.println(assigned_to_2);
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	nb.forwardee_comment("new number dept comment");
	nb.decision_picker();
	nb.window_handling(dr);
	nb.desicion_search_box("New Number Taken");
	nb.save();
	Thread.sleep(2000);
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

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	Thread.sleep(1000);
	JavascriptExecutor jse = (JavascriptExecutor)dr;
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
	Forwardee_dept_flow fin= new Forwardee_dept_flow(dr);
	role=cse.Role();
	if(role!="Finance RFP")
	{
		cse.switchRole("Finance RFP");
	}
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
//	WebDriverWait wt1= new WebDriverWait(dr, 60);
//	wt1.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	fin.edit();
	fin.forwardee_section();
	fin.forwardee3_comment();
	fin.decision3_picker();
	fin.forwardee3_desicion_search("Accept");
	fin.save();
	String assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
	
	assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Thread.sleep(800);
	ps1.return_to_policy();
	pos.edit();
	pos.forwardee_section();
	pos.forwardee4_comment("pos comments here...");
	pos.decision4_picker();
	pos.forwardee3_desicion_search("Accept");
	pos.save();
	
	assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
}

@Test  (priority=4)
public void GRV2_TS046 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	JavascriptExecutor jse = (JavascriptExecutor)dr;
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts = new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.Reason();
	sc.policy_issue_date1("28","Jan","2013");
	sc.cd("Not Applicable");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny IRDA comments...");
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	
	Forwardee_dept_flow poscf = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	poscf.edit();
	Thread.sleep(8000);
	poscf.forwardee_section();
	poscf.forwardee_comment("poscf comments...");
	JavascriptExecutor js= (JavascriptExecutor)dr;
	js.executeScript("window.scrollBy(0,200)", "");
	poscf.decision_picker();
	poscf.window_handling(dr);
	poscf.desicion_search_box("Accept");
	poscf.save1();
	Thread.sleep(2000);
	assigned_to=cse.assigned_to();
	case_id=cse.case_id();
	System.out.println(case_id);
	case_owner=cse.Case_owner();
	System.out.println(assigned_to);
	System.out.println(case_owner);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	Forwardee_dept_flow nb1= new Forwardee_dept_flow(dr);
	nb1.forwardee2_comment("new number dept comment");
	nb1.decision2_picker();
	nb1.forwardee3_nb_desicion_search("New Number Taken");
	nb1.save();
	Thread.sleep(2000);
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
	

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	Thread.sleep(1000);
	
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium "
			+ "Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
	Forwardee_dept_flow fin= new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt1= new WebDriverWait(dr, 60);
	wt1.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));	fin.edit();
	fin.forwardee_section();
	fin.forwardee3_comment();
	fin.decision3_picker();
	fin.forwardee3_desicion_search("Accept");
	fin.save();
	String assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
	
	assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Thread.sleep(800);
	ps1.return_to_policy();
	pos.edit();
	pos.forwardee_section();
	pos.forwardee4_comment("pos comments here...");
	pos.decision4_picker();
	pos.forwardee3_desicion_search("Accept");
	pos.save();
	
	assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();

}

@Test (priority=5) // when policy status is not premium paying
public void GRV2_TS047 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	JavascriptExecutor jse = (JavascriptExecutor)dr;
    login lg = new login (dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000001396");  
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	Ticket_search_flow ts = new Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.Reason();
	sc.policy_issue_date1("2","Dec","2015");
	sc.cd("Not Applicable");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny IRDA comments...");
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	
	Forwardee_dept_flow poscf = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	poscf.edit();
	Thread.sleep(8000);
	poscf.forwardee_section();
	poscf.forwardee_comment("poscf comments...");
	JavascriptExecutor js= (JavascriptExecutor)dr;
	js.executeScript("window.scrollBy(0,200)", "");
	poscf.decision_picker();
	poscf.window_handling(dr);
	poscf.desicion_search_box("Accept");
	poscf.save1();
	Thread.sleep(2000);
	assigned_to=cse.assigned_to();
	case_id=cse.case_id();
	System.out.println(case_id);
	case_owner=cse.Case_owner();
	System.out.println(assigned_to);
	System.out.println(case_owner);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	Forwardee_dept_flow nb1= new Forwardee_dept_flow(dr);
	nb1.forwardee2_comment("new number dept comment");
	nb1.decision2_picker();
	nb1.forwardee3_nb_desicion_search("New Number Taken");
	nb1.save();
	Thread.sleep(2000);
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

	
	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	Thread.sleep(1000);
	
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium "
			+ "Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
	Forwardee_dept_flow fin= new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt1= new WebDriverWait(dr, 60);
	wt1.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));	fin.edit();
	fin.forwardee_section();
	fin.forwardee3_comment();
	fin.decision3_picker();
	fin.forwardee3_desicion_search("Accept");
	fin.save();
	String assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");

	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
	
	assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Thread.sleep(800);
	ps1.return_to_policy();
	pos.edit();
	pos.forwardee_section();
	pos.forwardee4_comment("pos comments here...");
	pos.decision4_picker();
	pos.forwardee3_desicion_search("Accept");
	pos.save();
	
	assigned_to_4=cse.assigned_to();
	System.out.println(assigned_to_4);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	role=cse.Role();
	System.out.println(role);
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();

}

@ Test 
public void GRV2_TS048 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
    login lg = new login (dr);
    Ticket_search_flow ts = new Ticket_search_flow(dr);
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.policy_issue_date();
	sc.Reason();
	sc.cd("Not Applicable");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny IRDA comments...");
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	nb.forwardee_comment("new number dept comment");
	nb.decision_picker();
	nb.window_handling(dr);
	nb.desicion_search_box("New Number Taken");
	nb.save();
	Thread.sleep(2000);
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
	if(role!=("Grievance Executive"))
	{
		cse.switchRole("Grievance Executive");
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

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	Thread.sleep(1000);
	JavascriptExecutor jse = (JavascriptExecutor)dr;
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Rejected");
    apr.save();
    Thread.sleep(1000);
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
}

@ Test  // policy having plan type: trad
public void GRV2_TS049 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
    login lg = new login (dr);
    Ticket_search_flow ts = new Ticket_search_flow(dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000006619");     // pass policy number having policy type :"Trad"
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.policy_issue_date();
	sc.cd("Not Applicable");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.IRDAProb("scrutiny IRDA comments");
	sc.Reason();
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
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
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Thread.sleep(6000);
	nb.edit();
	nb.forwardee_section();
	nb.new_number();
	Forwardee_dept_flow nb1= new Forwardee_dept_flow(dr);
	nb1.forwardee_comment("new number comments...");
	nb1.decision_picker();
	nb1.forwardee3_nb_desicion_search("New Number Taken");
	Thread.sleep(6000);
	nb.save();
	Thread.sleep(2000);
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

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	Thread.sleep(1000);
	JavascriptExecutor jse = (JavascriptExecutor)dr;
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    
    Forwardee_dept_flow rfp= new Forwardee_dept_flow(dr);
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
    ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
    rfp.edit();
    rfp.forwardee_section();
    rfp.forwardee3_comment();
    rfp.decision3_picker();
    rfp.forwardee3_desicion_search("Accept");
    rfp.save1();
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	jse.executeScript("window.scrollBy(0,500)", "");
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
}
@ Test 
public void GRV2_TS050 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
    login lg = new login (dr);
    Ticket_search_flow ts = new Ticket_search_flow(dr);
	lg.username("cse");
	lg.pswd("acid_qa");
	lg.login1();
	Policy_Search ps= new Policy_Search(dr);
	ps.quick_link();
	ps.policy_search();
	ps.policy_number_text("000003632");     // pass policy number having policy type :"ULIP"
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
    Thread.sleep(2000);
    cse.caller_type("Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Processing delay");
	cse.next_button();
	Thread.sleep(2000);
	
	try
    {
	  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
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
	if(role.contains("Claims"))
	{
		cse.switchRole("Scrutiny");
	}
	}
	catch(Exception e)
	{
		System.out.println("single role");
	}
	scrutiny_flow sc= new scrutiny_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	WebDriverWait wt= new WebDriverWait(dr, 60);
	wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	sc.edit();
	sc.scrutiny_section();
	sc.resolution_code("Incorrect in Date of Commencement");
	WebDriverWait wait= new WebDriverWait(dr, 500);
	wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='cas_ex10_61']"))));
	sc.incorrect_info();
	sc.correct_info();
	sc.policy_issue_date();
	sc.cd("Not Applicable");
	sc.rca_comment("Mistake in Date of Commencement (DOC)");
	sc.Reason();
	sc.IRDAProb("scrutiny IRDA comments..");
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
	String impact=sc.impact();
	System.out.println("impact is "+ impact);
	String Policy_status =sc.policy_status();
	System.out.println("Policy status is "+ Policy_status);
	Assert.assertNotSame(Policy_status, "Premium paying", "Policy status should be premium paying");
	Thread.sleep(2000);
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	New_Number nb= new New_Number(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	nb.edit();
	nb.forwardee_section();
	nb.new_number(); 
	Thread.sleep(6000);
	Forwardee_dept_flow nb1= new Forwardee_dept_flow(dr);
	nb1.forwardee_comment("new number comments...");
	nb1.decision_picker();
	nb1.forwardee3_nb_desicion_search("New Number Taken");
	nb.save();
	Thread.sleep(2000);
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
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

	WebDriverWait wt3= new WebDriverWait(dr, 60);
	wt3.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	Thread.sleep(1000);
	JavascriptExecutor jse = (JavascriptExecutor)dr;
	jse.executeScript("window.scrollBy(0,200)", "");
	
	grv.new_no_policy_status();
	Thread.sleep(1000);
	grv.new_no_status("Standard and Premium Awaited");
	grv.required_premium("1200");
	grv.case_history("case history");
	grv.delay("delay...");
	grv.went_wrong("wrong....");
	grv.is_recovery("yes.......");
	grv.future_recourse("future... recourse...");
	grv.AmtInWords();
	Thread.sleep(3000);
	WebDriverWait wt2= new WebDriverWait(dr, 80);
	wt2.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath("//span[contains(text(),'Save and Proceed')]"))));
	grv.new_save();
	String assigned_to_3=cse.assigned_to();
	System.out.println(assigned_to_3);
	Thread.sleep(2000);
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	Approver apr= new Approver(dr);
	apr.cases();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	apr.edit();
	Thread.sleep(1000);
    apr.approver_comment();
    apr.approver_decision("Approved");
    apr.save();
    Thread.sleep(1000);
    lg.logout();
    
    Forwardee_dept_flow rfp= new Forwardee_dept_flow(dr);
    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
    lg.pswd("acid_qa");
    lg.login1();
    ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
    rfp.edit();
    rfp.forwardee_section();
    rfp.forwardee3_comment();
    rfp.decision3_picker();
    rfp.forwardee3_desicion_search("Accept");
    rfp.save1();
    lg.logout();
   
    abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
	Thread.sleep(2000);
	grv.nongrvSection();
	jse.executeScript("window.scrollBy(0,200)", "");
	grv.new_no_policy_status();
	grv.new_no_status_1("Premium paying");
	Thread.sleep(1000);
	grv.save_proceed();
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
   	System.out.println("assignee by db : "+abc);
   	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
	pos.cross_popup();
	pos.edit();
	pos.forwardee_section();
	pos.forwardee4_comment("pos comments...");
	pos.decision4_picker();
	pos.forwardee3_desicion_search("Accept");
	pos.save1();
	lg.logout();
	
	abc=con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
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
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	grv.edit();
    grv.nongrvSection();
    jse.executeScript("window.scrollBy(0,500)", "");
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("resolution comment by grv");
	grv.save_proceed();
  }	

}

// refactored on 25th Jul'17
