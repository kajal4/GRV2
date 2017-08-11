package testcases;

import java.sql.SQLException;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Browser;

public class Mistake_In_Other extends Browser
{
	@Test (enabled=false)
	public void GRV2_TS022 () throws InterruptedException, SQLException
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
	    System.out.println("return flag is "+" "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	     {
	 	   ps1.return_to_policy();
	     }
	    String cust = ps1.cust_class();
	    System.out.println("customer type is"+" "+cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("agent status is "+" "+Agent_status);
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
		System.out.println("case id is"+" "+case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
		sc.resolution_code("Mistakes in any other policy schedule item");
		sc.cd("Mistakes in any other policy schedule item");
		sc.type_of_error("Others");
		sc.IRDAProb("scrutiny IRDA comments..");
		sc.rca_comment("comments by scrutiny....");
		Thread.sleep(500);
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println(assigned_to_1);
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
	

	@Test (enabled=false)
	public void GRV2_TS034 () throws InterruptedException, SQLException
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
	    System.out.println("return flag is "+" "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	     {
	 	   ps1.return_to_policy();
	     }
	    String cust = ps1.cust_class();
	    System.out.println("customer type is"+" "+cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("agent status is "+" "+Agent_status);
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
		String case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
		Ticket_search_flow ts= new Ticket_search_flow(dr);
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		WebDriverWait wt= new WebDriverWait(dr, 60);
		wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
		sc.edit();
		sc.scrutiny_section();
		sc.resolution_code("Mistakes in any other policy schedule item");
		sc.cd("Mistakes in any other policy schedule item");
		sc.type_of_error("Others");
		sc.rca_type("Incorrect appointee name");
		sc.rca_comment("comments by scrutiny....");
		sc.IRDAProb("scrutiny IRDA comments");
		Thread.sleep(500);
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println(assigned_to_1);
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
	
	@Test (enabled=false)
	public void GRV2_TS033 () throws InterruptedException, SQLException
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
	    System.out.println("return flag is "+" "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	     {
	 	   ps1.return_to_policy();
	     }
	    String cust = ps1.cust_class();
	    System.out.println("customer type is"+" "+cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("agent status is "+" "+Agent_status);
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
		String case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
		Ticket_search_flow ts= new Ticket_search_flow(dr);
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		WebDriverWait wt= new WebDriverWait(dr, 60);
		wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
		sc.edit();
		sc.scrutiny_section();
		sc.resolution_code("Mistakes in any other policy schedule item");
		sc.cd("Mistakes in any other policy schedule item");
		sc.type_of_error("Client ID merger issue");
		sc.rca_type("Client ID merged");
		sc.rca_comment("comments by scrutiny....");
		sc.IRDAProb("Scrutiny IRDA section");
		Thread.sleep(500);
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println(assigned_to_1);
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
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		New_Number nb =  new  New_Number(dr);
		nb.edit();
		nb.forwardee_section();
		nb.new_number();
		nb.forwardee_comment("comments by new number dept");
		nb.decision_picker();
		nb.window_handling(dr);
		nb.desicion_search_box1("Accept");
		nb.save();
		Thread.sleep(2000);
		case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		assigned_to=cse.assigned_to();
		case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
	    lg.logout();
		
	    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
		System.out.println("assignee by db : "+abc);
		lg.username(abc);
		lg.pswd("acid_qa");
		lg.login1();
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		
		Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
		pos.edit();
		pos.forwardee_section();
		pos.forwardee2_comment("pos comments");
		pos.decision2_picker();
		pos.window_handling(dr);
		pos.forwardee3_desicion_search("Accept");
		pos.save();
		Thread.sleep(2000);
		case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		assigned_to=cse.assigned_to();
		case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
	public void GRV2_TS021 () throws InterruptedException, SQLException
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
	    System.out.println("return flag is "+" "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	     {
	 	   ps1.return_to_policy();
	     }
	    String cust = ps1.cust_class();
	    System.out.println("customer type is"+" "+cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("agent status is "+" "+Agent_status);
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
		String case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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


		Ticket_search_flow ts= new Ticket_search_flow(dr);
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		WebDriverWait wt= new WebDriverWait(dr, 60);
		wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
		sc.edit();
		sc.scrutiny_section();
		sc.resolution_code("Mistakes in any other policy schedule item");
		sc.cd("Mistakes in any other policy schedule item");
		sc.type_of_error("Client ID merger issue");
//		sc.rca_type("Client ID merged");
		sc.rca_comment("comments by scrutiny....");
		sc.IRDAProb("scrutiny IRDA comments");
		Thread.sleep(500);
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println(assigned_to_1);
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
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		New_Number nb =  new  New_Number(dr);
		nb.edit();
		nb.forwardee_section();
		nb.new_number();
		nb.forwardee_comment("comments by new number dept");
		nb.decision_picker();
		nb.window_handling(dr);
		nb.desicion_search_box1("Accept");
		nb.save();
		Thread.sleep(2000);
		case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		assigned_to=cse.assigned_to();
		case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
	    lg.logout();
		
	    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
		System.out.println("assignee by db : "+abc);
		lg.username(abc);
		lg.pswd("acid_qa");
		lg.login1();
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		
		Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
		pos.edit();
		pos.forwardee_section();
		pos.forwardee2_comment("pos comments");
		pos.decision2_picker();
		pos.window_handling(dr);
		pos.forwardee3_desicion_search("Accept");
		pos.save();
		Thread.sleep(2000);
		case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		assigned_to=cse.assigned_to();
		case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
	
	
	
//	***************************************************************************************
	
	@Test 
	public void GRV2_TS053 () throws InterruptedException, SQLException
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
	    System.out.println("return flag is "+" "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	     {
	 	   ps1.return_to_policy();
	     }
	    String cust = ps1.cust_class();
	    System.out.println("customer type is"+" "+cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("agent status is "+" "+Agent_status);
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
		String case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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


		Ticket_search_flow ts= new Ticket_search_flow(dr);
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		WebDriverWait wt= new WebDriverWait(dr, 60);
		wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
		sc.edit();
		sc.scrutiny_section();
		sc.resolution_code("Mistakes in any other policy schedule item");
		sc.cd("Not Applicable");
		sc.type_of_error("Client ID merger issue");
//		sc.rca_type("Client ID merged");
		sc.rca_comment("comments by scrutiny....");
		sc.IRDAProb("scrutiny IRDA comments");
		Thread.sleep(500);
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println(assigned_to_1);
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
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		New_Number nb =  new  New_Number(dr);
		nb.edit();
		nb.forwardee_section();
		nb.new_number();
		nb.forwardee_comment("comments by new number dept");
		nb.decision_picker();
		nb.window_handling(dr);
		nb.desicion_search_box1("Accept");
		nb.save();
		Thread.sleep(2000);
		case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		assigned_to=cse.assigned_to();
		case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
	    lg.logout();
		
	    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
		System.out.println("assignee by db : "+abc);
		lg.username(abc);
		lg.pswd("acid_qa");
		lg.login1();
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		
		Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
		pos.edit();
		pos.forwardee_section();
		pos.forwardee2_comment("pos comments");
		pos.decision2_picker();
		pos.window_handling(dr);
		pos.forwardee3_desicion_search("Accept");
		pos.save();
		Thread.sleep(2000);
		case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		assigned_to=cse.assigned_to();
		case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
	public void GRV2_TS054 () throws InterruptedException, SQLException
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
	    System.out.println("return flag is "+" "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	     {
	 	   ps1.return_to_policy();
	     }
	    String cust = ps1.cust_class();
	    System.out.println("customer type is"+" "+cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("agent status is "+" "+Agent_status);
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
		System.out.println("case id is"+" "+case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
		sc.resolution_code("Mistakes in any other policy schedule item");
		sc.cd("Not Applicable");
		sc.type_of_error("Others");
		sc.IRDAProb("scrutiny IRDA comments..");
		sc.rca_comment("comments by scrutiny....");
		Thread.sleep(500);
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println(assigned_to_1);
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
	public void GRV2_TS065 () throws InterruptedException, SQLException
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
	    System.out.println("return flag is "+" "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	     {
	 	   ps1.return_to_policy();
	     }
	    String cust = ps1.cust_class();
	    System.out.println("customer type is"+" "+cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("agent status is "+" "+Agent_status);
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
		String case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
		Ticket_search_flow ts= new Ticket_search_flow(dr);
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		WebDriverWait wt= new WebDriverWait(dr, 60);
		wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
		sc.edit();
		sc.scrutiny_section();
		sc.resolution_code("Mistakes in any other policy schedule item");
		sc.cd("Not Applicable");
		sc.type_of_error("Client ID merger issue");
		sc.rca_type("Client ID merged");
		sc.rca_comment("comments by scrutiny....");
		sc.IRDAProb("Scrutiny IRDA section");
		Thread.sleep(500);
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println(assigned_to_1);
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
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		New_Number nb =  new  New_Number(dr);
		nb.edit();
		nb.forwardee_section();
		nb.new_number();
		nb.forwardee_comment("comments by new number dept");
		nb.decision_picker();
		nb.window_handling(dr);
		nb.desicion_search_box1("Accept");
		nb.save();
		Thread.sleep(2000);
		case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		assigned_to=cse.assigned_to();
		case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
	    lg.logout();
		
	    abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
		System.out.println("assignee by db : "+abc);
		lg.username(abc);
		lg.pswd("acid_qa");
		lg.login1();
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		
		Forwardee_dept_flow pos = new Forwardee_dept_flow(dr);
		pos.edit();
		pos.forwardee_section();
		pos.forwardee2_comment("pos comments");
		pos.decision2_picker();
		pos.window_handling(dr);
		pos.forwardee3_desicion_search("Accept");
		pos.save();
		Thread.sleep(2000);
		case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		assigned_to=cse.assigned_to();
		case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
	public void GRV2_TS066 () throws InterruptedException, SQLException
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
	    System.out.println("return flag is "+" "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	     {
	 	   ps1.return_to_policy();
	     }
	    String cust = ps1.cust_class();
	    System.out.println("customer type is"+" "+cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("agent status is "+" "+Agent_status);
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
		String case_id=cse.case_id1();
		System.out.println("case id is"+" "+case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("case assignee is"+" "+assigned_to);
		System.out.println("case owner is"+" "+case_owner);
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
		Ticket_search_flow ts= new Ticket_search_flow(dr);
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		WebDriverWait wt= new WebDriverWait(dr, 60);
		wt.until(ExpectedConditions.elementToBeClickable(dr.findElement(By.xpath(".//*[@id='BTN_EDIT']/i"))));
		sc.edit();
		sc.scrutiny_section();
		sc.resolution_code("Mistakes in any other policy schedule item");
		sc.cd("Not Applicable");
		sc.type_of_error("Others");
		sc.rca_type("Incorrect appointee name");
		sc.rca_comment("comments by scrutiny....");
		sc.IRDAProb("scrutiny IRDA comments");
		Thread.sleep(500);
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println(assigned_to_1);
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

// refactored on 26 jul'17
