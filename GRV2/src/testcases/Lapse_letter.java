package testcases;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import base.Browser;
import methods.CSE_Screen_Flow;
import methods.Policy_360;
import methods.Policy_Search;
import methods.Ticket_search_flow;
import methods.grv_flow;
import methods.login;
import methods.renewal_dept;
import methods.scrutiny_flow;


public class Lapse_letter extends Browser
{
   @Test (enabled=false)
   public void GRV2_TS001() throws InterruptedException, SQLException
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
    System.out.println("Return Flag : "+return_flag);
   
    String cust = ps1.cust_class();
    System.out.println("Customer type : "+cust);
    String Agent_status = ps1.agent_status();
	System.out.println("Agent status is : "+Agent_status);
	ps1.csrequest();
    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
    cse.window_handling(dr);
    cse.caller_type("Axis Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Lapse letter not received");
	cse.next_button();
	
	 try
	    {
		  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
		  System.out.println(el);
		}
		catch(Exception e)
		 {
		   dr.switchTo().alert().accept();
		 }
	
	cse.is_lapse("Yes");
	cse.calendar("1", "1", "2016");
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println("Case id : "+case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println("Assigned to : "+assigned_to);
	System.out.println("Case owner : "+case_owner);
	grv.ticket_status();
	
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
	sc.cd("insurer failed to send lap");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("scrutiny IRDA comments..");
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
//	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to : "+assigned_to_1);
	case_owner=cse.Case_owner();
	String status=grv.ticket_status();
	System.out.println("Status of the case is : "+ status);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	System.out.println("Case owner : "+case_owner); 
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
	while(status.equalsIgnoreCase("Acknowledged"))
	{
		dr.navigate().refresh();
		Thread.sleep(1000);
		status=grv.ticket_status();
	}
	Thread.sleep(2000);
	grv.edit();
	grv.owner_section();
	grv.resolution_ud();
	grv.resolution_by();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("grv comments.......");
	grv.CaseOwnerIRDA();
	grv.save_proceed();
	grv.ticket_status();
	
	assigned_to=cse.assigned_to();
	case_owner=cse.Case_owner();
	System.out.println("Assigned to : "+assigned_to);
	System.out.println("Case owner : "+case_owner);
	}  
	
	@Test (enabled=false)
	public void GRV2_TS002 () throws InterruptedException, SQLException
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
		ps.policy_number_text("000003632");  //100472893(active, hni) ,100350016(not active, low value segment) on qa
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
	    cse.caller_type("Axis Customer");
		cse.caller_name();
		cse.problem_box("case problem box");
		cse.ssc("Lapse letter not received");
		cse.next_button();
		 try
		    {
			  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
			  System.out.println(el);
			}
			catch(Exception e)
			 {
			   dr.switchTo().alert().accept();
			 }
		cse.is_lapse("No");
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
		System.out.println("assignee by db "+abc);
		 
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
		sc.cd("insurer failed to send lapse");
		sc.rca_comment("scrutiny comment");
		sc.IRDAProb("scrutiny IRDA comments");
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
		System.out.println(assigned_to_1);
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println(assigned_to_1);
		lg.logout();

		abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
		System.out.println("assignee by db "+abc);
		lg.username(abc);
		lg.pswd("acid_qa");
		lg.login1();
		renewal_dept ren = new renewal_dept(dr);
		ts.Ticketsearchlink1();
		ts.EnterTcktNum(case_id);
		ts.fetchButton();
		ts.Validate();
		ren.edit();
		Thread.sleep(2000);
		ren.forwardee_section();
		ren.forwardee_comment("renewals comment......");
		ren.decision_picker();
		ren.window_handling(dr);
		ren.desicion_search_box("accept");
		ren.save();
		lg.logout();
		abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
		System.out.println("assignee by db "+abc);
		 
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
		grv_flow grv= new grv_flow(dr);
		grv.edit();
		grv.owner_section();
		grv.resolution_ud();
		grv.resolution_by();
		grv.is_forwarding();
		grv.acceptance_status();
		grv.resolution_box("grv comments.......");
		grv.CaseOwnerIRDA();
		grv.save_proceed();
		grv.ticket_status();
	}
	
	
	
//	**********************************************************************************
	
	 @Test 
	   public void GRV2_TS0035() throws InterruptedException, SQLException
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
	    System.out.println("Return Flag : "+return_flag);
	   
	    String cust = ps1.cust_class();
	    System.out.println("Customer type : "+cust);
	    String Agent_status = ps1.agent_status();
		System.out.println("Agent status is : "+Agent_status);
		ps1.csrequest();
	    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
	    cse.window_handling(dr);
	    cse.caller_type("Axis Customer");
		cse.caller_name();
		cse.problem_box("case problem box");
		cse.ssc("Lapse letter not received");
		cse.next_button();
		
		 try
		    {
			  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
			  System.out.println(el);
			}
			catch(Exception e)
			 {
			   dr.switchTo().alert().accept();
			 }
		
		cse.is_lapse("Yes");
		cse.calendar("1", "1", "2016");
		cse.save();
		Thread.sleep(2000);
		String case_id=cse.case_id();
		System.out.println("Case id : "+case_id);
		String assigned_to=cse.assigned_to();
		String case_owner=cse.Case_owner();
		System.out.println("Assigned to : "+assigned_to);
		System.out.println("Case owner : "+case_owner);
		grv.ticket_status();
		
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
		sc.cd("not applicable");
		sc.rca_comment("scrutiny comment");
		sc.IRDAProb("scrutiny IRDA comments..");
		sc.scrutiny_save();
		String assigned_to_1=cse.assigned_to();
//		System.out.println(assigned_to_1);
		while(assigned_to_1.equalsIgnoreCase(abc))
		{
			sc.refresh();
			assigned_to_1=cse.assigned_to();
		}
		System.out.println("Assigned to : "+assigned_to_1);
		case_owner=cse.Case_owner();
		String status=grv.ticket_status();
		System.out.println("Status of the case is : "+ status);
		lg.logout();
		abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
		System.out.println("assignee by db : "+abc);
		System.out.println("Case owner : "+case_owner); 
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
		while(status.equalsIgnoreCase("Acknowledged"))
		{
			dr.navigate().refresh();
			Thread.sleep(1000);
			status=grv.ticket_status();
		}
		Thread.sleep(2000);
		grv.edit();
        grv.nongrvSection();
//		grv.resolution_ud();
//		grv.resolution_by();
		grv.is_forwarding();
		grv.acceptance_status();
		grv.resolution_box("grv comments.......");
//		grv.CaseOwnerIRDA();
		grv.save_proceed();
		grv.ticket_status();
		
		assigned_to=cse.assigned_to();
		case_owner=cse.Case_owner();
		System.out.println("Assigned to : "+assigned_to);
		System.out.println("Case owner : "+case_owner);
		}  
		
	 @Test
	 public void GRV2_TS0036 () throws InterruptedException, SQLException
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
			ps.policy_number_text("000003632");  //100472893(active, hni) ,100350016(not active, low value segment) on qa
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
		    cse.caller_type("Axis Customer");
			cse.caller_name();
			cse.problem_box("case problem box");
			cse.ssc("Lapse letter not received");
			cse.next_button();
			 try
			    {
				  Boolean el = dr.findElement(By.xpath(".//*[@id='Cas_ex5_44']")).isEnabled();
				  System.out.println(el);
				}
				catch(Exception e)
				 {
				   dr.switchTo().alert().accept();
				 }
			cse.is_lapse("No");
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
			System.out.println("assignee by db "+abc);
			 
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
			System.out.println(assigned_to_1);
			while(assigned_to_1.equalsIgnoreCase(abc))
			{
				sc.refresh();
				assigned_to_1=cse.assigned_to();
			}
			System.out.println(assigned_to_1);
			lg.logout();

			abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
			System.out.println("assignee by db "+abc);
			lg.username(abc);
			lg.pswd("acid_qa");
			lg.login1();
			renewal_dept ren = new renewal_dept(dr);
			ts.Ticketsearchlink1();
			ts.EnterTcktNum(case_id);
			ts.fetchButton();
			ts.Validate();
			ren.edit();
			Thread.sleep(2000);
			ren.forwardee_section();
			ren.forwardee_comment("renewals comment......");
			ren.decision_picker();
			ren.window_handling(dr);
			ren.desicion_search_box("accept");
			ren.save();
			lg.logout();
			abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
			System.out.println("assignee by db "+abc);
			 
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
			grv_flow grv= new grv_flow(dr);
			grv.edit();
			grv.nongrvSection();
			grv.is_forwarding();
			grv.acceptance_status();
			grv.resolution_box("grv comments.......");
			grv.save_proceed();
			grv.ticket_status();
		}
}
