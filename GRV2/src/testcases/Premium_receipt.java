package testcases;

import java.sql.SQLException;

import methods.CSE_Screen_Flow;
import methods.Policy_360;
import methods.Policy_Search;
import methods.Ticket_search_flow;
import methods.grv_flow;
import methods.login;
import methods.scrutiny_flow;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import base.Browser;

public class Premium_receipt extends Browser
{

	@Test
	public void GRV2_TS004 () throws InterruptedException, SQLException
	{
		base.TestConnectivity con = new base.TestConnectivity();
		con.setup();
		login lg = new login (dr);
		grv_flow grv= new grv_flow(dr);
		lg.username("cse");
		lg.pswd("acid_qa");
		lg.login1();
		Policy_Search ps= new Policy_Search(dr);
		ps.quick_link();
		ps.policy_search();
		ps.policy_number_text("000003632");  //100472893(active, hni) ,100350016(not active, low value segment) on qa
		ps.fetch();
		ps.check_mark();
		ps.validate();
		ps.popup_handling();
	    Policy_360 ps1= new Policy_360(dr);
	    String return_flag= ps1.return_flag();
	    System.out.println("Return flag is "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	    {
	    	ps1.return_to_policy();
	    }
	    String cust = ps1.cust_class();
	    System.out.println("Customer classification is : "+ cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("Agent Status is "+Agent_status);
		ps1.csrequest();
	    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
	    cse.window_handling(dr);
	    cse.caller_type("Axis Customer");
		cse.caller_name();
		cse.problem_box("case problem box");
		cse.ssc("Premium Receipt not received");
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
		cse.doc_id();
		cse.doc_send_to();
		cse.mode();
		cse.form_yr();
		cse.to_yr();
		cse.save();
		Thread.sleep(2000);
		String case_id=cse.case_id();
		System.out.println("Case id is : " + case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("Assigned To : "+assigned_to);
		System.out.println("Case Owner is : "+case_owner);
		String status=grv.ticket_status();
		cse.subStatus();
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
		sc.edit();
		sc.scrutiny_section();
		sc.cd("Non-receipt of Premium receipt");
		sc.rca_comment("scrutiny comment");
		sc.IRDAProb("scrutiny IRDA comments");
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println("Assigned To : "+assigned_to_1);
		cse.subStatus();
		grv.ticket_status();
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
		Thread.sleep(5000);
		status=grv.ticket_status();
		grv.edit();
		grv.owner_section();
		grv.resolution_ud();
		grv.resolution_by();
		grv.is_forwarding();
		grv.acceptance_status();
		grv.noActionRequired();
		grv.resolution_box("grv comments.......");
		grv.CaseOwnerIRDA();
		grv.save_proceed();
		status=grv.ticket_status();
		cse.subStatus();
		System.out.println("Assigned To : "+assigned_to_1);
		cse.subStatus();
		grv.ticket_status();
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
		Thread.sleep(5000);
		status=grv.ticket_status();
		grv.edit();
		grv.save_proceed();
		status=grv.ticket_status();
		Thread.sleep(2000);
		if(status.equalsIgnoreCase("Pending"))
		{
			grv.edit();
			grv.save_proceed();
			status=grv.ticket_status();
		}
	}
	
	
	@Test
	public void GRV2_TS0037 () throws InterruptedException, SQLException
	{
		base.TestConnectivity con = new base.TestConnectivity();
		con.setup();
		login lg = new login (dr);
		grv_flow grv= new grv_flow(dr);
		lg.username("cse");
		lg.pswd("acid_qa");
		lg.login1();
		Policy_Search ps= new Policy_Search(dr);
		ps.quick_link();
		ps.policy_search();
		ps.policy_number_text("000003632");  //100472893(active, hni) ,100350016(not active, low value segment) on qa
		ps.fetch();
		ps.check_mark();
		ps.validate();
		ps.popup_handling();
	    Policy_360 ps1= new Policy_360(dr);
	    String return_flag= ps1.return_flag();
	    System.out.println("Return flag is "+return_flag);
	    if(return_flag.equalsIgnoreCase("Yes"))
	    {
	    	ps1.return_to_policy();
	    }
	    String cust = ps1.cust_class();
	    System.out.println("Customer classification is : "+ cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("Agent Status is "+Agent_status);
		ps1.csrequest();
	    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
	    cse.window_handling(dr);
	    cse.caller_type("Axis Customer");
		cse.caller_name();
		cse.problem_box("case problem box");
		cse.ssc("Premium Receipt not received");
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
		cse.doc_id();
		cse.doc_send_to();
		cse.mode();
		cse.form_yr();
		cse.to_yr();
		cse.save();
		Thread.sleep(2000);
		String case_id=cse.case_id();
		System.out.println("Case id is : " + case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("Assigned To : "+assigned_to);
		System.out.println("Case Owner is : "+case_owner);
		String status=grv.ticket_status();
		cse.subStatus();
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
		sc.edit();
		sc.scrutiny_section();
		sc.cd("Not Applicable");
		sc.rca_comment("scrutiny comment");
		sc.IRDAProb("scrutiny IRDA comments");
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println("Assigned To : "+assigned_to_1);
		cse.subStatus();
		grv.ticket_status();
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
		Thread.sleep(5000);
		status=grv.ticket_status();
		grv.edit();
		grv.nongrvSection();
		grv.is_forwarding();
		grv.acceptance_status();
		grv.noActionRequired();
		grv.resolution_box("grv comments.......");
		grv.save_proceed();
		status=grv.ticket_status();
		cse.subStatus();
		System.out.println("Assigned To : "+assigned_to_1);
		cse.subStatus();
		grv.ticket_status();
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
		Thread.sleep(5000);
		status=grv.ticket_status();
		grv.edit();
		grv.save_proceed();
		status=grv.ticket_status();
		Thread.sleep(2000);
		if(status.equalsIgnoreCase("Pending"))
		{
			grv.edit();
			grv.save_proceed();
			status=grv.ticket_status();
		}
	}
}

//refactored on 19july'17// 21stjul
