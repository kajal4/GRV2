package testcases;

import java.sql.SQLException;

import methods.CSE_Screen_Flow;
import methods.Policy_360;
import methods.Policy_Search;
import methods.Forwardee_dept_flow;
import methods.grv_flow;
import methods.login;
import methods.scrutiny_flow;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import base.Browser;

public class Premium_clearance extends Browser
{ 
@ Test (enabled=false)
public void GRV2_TS006 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	login lg = new login (dr);
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
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
    System.out.println("Return Flag is : "+return_flag);
    if(return_flag.equalsIgnoreCase("Yes"))
    {
    	ps1.return_to_policy();
    }
    String cust = ps1.cust_class();
    System.out.println("Customer type is : "+cust);
    String Agent_status = ps1.agent_status();
	System.out.println("Agent status is : "+Agent_status);
	ps1.csrequest();
    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
    cse.window_handling(dr);
    cse.caller_type("Axis Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Premium clearance/posting delay");
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
	
	
	cse.type_of_payment("Cheque/DD Payment");  //Debit Card Payment , Bank Transfer , Cheque/DD Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
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
	sc.edit();
	sc.scrutiny_section();
	sc.cd("payment of premium not acted upon");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to : "+assigned_to_1);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);  //******************
	ts.fetchButton();
	ts.Validate();
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("No");
	fl.save();
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

	
@ Test (enabled=false)
public void GRV2_TS008 () throws InterruptedException, SQLException
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
	cse.ssc("Premium clearance/posting delay");
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
	
	cse.type_of_payment("Credit Card Payment");  //Credit Card Payment,  Internet Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println("Case id is : "+case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println("Assigned To : "+assigned_to);
	System.out.println("Case Owner is : "+case_owner);
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
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	sc.edit();
	sc.scrutiny_section();
	
	sc.cd("payment of premium not acted upon");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("Scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println("Assigned To : "+assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to is :"+assigned_to_1);
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
	
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("No");
	fl.save();
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

@ Test (enabled=false)
public void GRV2_TS010  () throws InterruptedException, SQLException
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
	cse.ssc("Premium clearance/posting delay");
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
	
	cse.type_of_payment("ECS Payment");  //Credit Card Payment,  Internet Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println("Case id is : "+case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println("Assigned To : "+assigned_to);
	System.out.println("Case Owner is : "+case_owner);
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
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	sc.edit();
	sc.scrutiny_section();
	
	sc.cd("payment of premium not acted upon");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("Scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println("Assigned To : "+assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to is :"+assigned_to_1);
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
	
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("No");
	fl.save();
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
 
@ Test (enabled=false)
public void GRV2_TS005 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	login lg = new login (dr);
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
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
    System.out.println("Return Flag is : "+return_flag);
    if(return_flag.equalsIgnoreCase("Yes"))
    {
    	ps1.return_to_policy();
    }
    String cust = ps1.cust_class();
    System.out.println("Customer type is : "+cust);
    String Agent_status = ps1.agent_status();
	System.out.println("Agent status is : "+Agent_status);
	ps1.csrequest();
    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
    cse.window_handling(dr);
    cse.caller_type("Axis Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Premium clearance/posting delay");
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
	
	
	cse.type_of_payment("Cheque/DD Payment");  //Debit Card Payment , Bank Transfer , Cheque/DD Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
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
	sc.edit();
	sc.scrutiny_section();
	sc.cd("payment of premium not acted upon");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to : "+assigned_to_1);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);  //******************
	ts.fetchButton();
	ts.Validate();
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("Yes");
	fl.amtAdded("5000");
	fl.save();
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
	fl.edit();
	fl.forwardee_section();
	fl.forwardee2_comment("POS Comments");
	fl.decision2_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.save();
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

@ Test (enabled=false)
public void GRV2_TS007 () throws InterruptedException, SQLException
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
	cse.ssc("Premium clearance/posting delay");
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
	
	cse.type_of_payment("Credit Card Payment");  //Credit Card Payment,  Internet Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println("Case id is : "+case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println("Assigned To : "+assigned_to);
	System.out.println("Case Owner is : "+case_owner);
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
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	sc.edit();
	sc.scrutiny_section();
	
	sc.cd("payment of premium not acted upon");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("Scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println("Assigned To : "+assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to is :"+assigned_to_1);
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
	
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("credit card dept comments......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("Yes");
	fl.amtAdded("5000");
	fl.save();
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
	fl.edit();
	fl.forwardee_section();
	fl.forwardee2_comment("POS Comments");
	fl.decision2_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.save();
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

@ Test 
public void GRV2_TS009  () throws InterruptedException, SQLException
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
    cse.caller_type("Axis Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Premium clearance/posting delay");
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
	
	cse.type_of_payment("ECS Payment");  //Credit Card Payment,  Internet Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println("Case id is : "+case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println("Assigned To : "+assigned_to);
	System.out.println("Case Owner is : "+case_owner);
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
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	sc.edit();
	sc.scrutiny_section();
	
	sc.cd("payment of premium not acted upon");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("Scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println("Assigned To : "+assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to is :"+assigned_to_1);
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
	
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("Yes");
	fl.amtAdded("4000");
	fl.save();
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
	fl.edit();
	fl.forwardee_section();
	fl.forwardee2_comment("POS Comments");
	fl.decision2_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.save();
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

//*********************************************************************************

@ Test 
public void GRV2_TS038 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	login lg = new login (dr);
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
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
    System.out.println("Return Flag is : "+return_flag);
    if(return_flag.equalsIgnoreCase("Yes"))
    {
    	ps1.return_to_policy();
    }
    String cust = ps1.cust_class();
    System.out.println("Customer type is : "+cust);
    String Agent_status = ps1.agent_status();
	System.out.println("Agent status is : "+Agent_status);
	ps1.csrequest();
    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
    cse.window_handling(dr);
    cse.caller_type("Axis Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Premium clearance/posting delay");
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
	
	
	cse.type_of_payment("Cheque/DD Payment");  //Debit Card Payment , Bank Transfer , Cheque/DD Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
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
	sc.edit();
	sc.scrutiny_section();
	sc.cd("Not Applicable");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to : "+assigned_to_1);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);  //******************
	ts.fetchButton();
	ts.Validate();
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("Yes");
	fl.amtAdded("5000");
	fl.save();
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
	fl.edit();
	fl.forwardee_section();
	fl.forwardee2_comment("POS Comments");
	fl.decision2_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.save();
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
	grv_flow grv= new grv_flow(dr);
	grv.edit();
	grv.nongrvSection();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("grv comments.......");
	grv.save_proceed();
	grv.ticket_status();
}  

@ Test 
public void GRV2_TS039 () throws InterruptedException, SQLException
{
	base.TestConnectivity con = new base.TestConnectivity();
	con.setup();
	login lg = new login (dr);
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
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
    System.out.println("Return Flag is : "+return_flag);
    if(return_flag.equalsIgnoreCase("Yes"))
    {
    	ps1.return_to_policy();
    }
    String cust = ps1.cust_class();
    System.out.println("Customer type is : "+cust);
    String Agent_status = ps1.agent_status();
	System.out.println("Agent status is : "+Agent_status);
	ps1.csrequest();
    CSE_Screen_Flow cse= new CSE_Screen_Flow(dr);
    cse.window_handling(dr);
    cse.caller_type("Axis Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Premium clearance/posting delay");
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
	
	
	cse.type_of_payment("Cheque/DD Payment");  //Debit Card Payment , Bank Transfer , Cheque/DD Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
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
	sc.edit();
	sc.scrutiny_section();
	sc.cd("Not Applicable");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println(assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to : "+assigned_to_1);
	lg.logout();
	abc= con.querry("select loginid from az_user where UserID=(select CurrentOwnerID from cases where caseid="+case_id+")");
	System.out.println("assignee by db : "+abc);
	lg.username(abc);
	lg.pswd("acid_qa");
	lg.login1();
	
	
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);  //******************
	ts.fetchButton();
	ts.Validate();
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("No");
	fl.save();
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
	grv_flow grv= new grv_flow(dr);
	grv.edit();
	grv.nongrvSection();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("grv comments.......");
	grv.save_proceed();
	grv.ticket_status();
}  

@ Test 
public void GRV2_TS040 () throws InterruptedException, SQLException
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
	cse.ssc("Premium clearance/posting delay");
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
	
	cse.type_of_payment("Credit Card Payment");  //Credit Card Payment,  Internet Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println("Case id is : "+case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println("Assigned To : "+assigned_to);
	System.out.println("Case Owner is : "+case_owner);
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
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	sc.edit();
	sc.scrutiny_section();
	sc.cd("Not Applicable");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("Scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println("Assigned To : "+assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to is :"+assigned_to_1);
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
	
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("credit card dept comments......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("Yes");
	fl.amtAdded("5000");
	fl.save();
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
	fl.edit();
	fl.forwardee_section();
	fl.forwardee2_comment("POS Comments");
	fl.decision2_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.save();
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
	grv_flow grv= new grv_flow(dr);
	grv.edit();
	grv.nongrvSection();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("grv comments.......");
	grv.save_proceed();
	grv.ticket_status();
}   

@ Test 
public void GRV2_TS041 () throws InterruptedException, SQLException
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
	cse.ssc("Premium clearance/posting delay");
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
	
	cse.type_of_payment("Credit Card Payment");  //Credit Card Payment,  Internet Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println("Case id is : "+case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println("Assigned To : "+assigned_to);
	System.out.println("Case Owner is : "+case_owner);
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
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	sc.edit();
	sc.scrutiny_section();
	
	sc.cd("Not Applicable");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("Scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println("Assigned To : "+assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to is :"+assigned_to_1);
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
	
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("No");
	fl.save();
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
	grv_flow grv= new grv_flow(dr);
	grv.edit();
	grv.nongrvSection();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("grv comments.......");
	grv.save_proceed();
	grv.ticket_status();

}   

@ Test (enabled=false)
public void GRV2_TS042  () throws InterruptedException, SQLException
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
    cse.caller_type("Axis Customer");
	cse.caller_name();
	cse.problem_box("case problem box");
	cse.ssc("Premium clearance/posting delay");
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
	
	cse.type_of_payment("ECS Payment");  //Credit Card Payment,  Internet Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println("Case id is : "+case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println("Assigned To : "+assigned_to);
	System.out.println("Case Owner is : "+case_owner);
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
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	sc.edit();
	sc.scrutiny_section();
	
	sc.cd("Not Applicable");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("Scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println("Assigned To : "+assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to is :"+assigned_to_1);
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
	
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("Yes");
	fl.amtAdded("4000");
	fl.save();
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
	fl.edit();
	fl.forwardee_section();
	fl.forwardee2_comment("POS Comments");
	fl.decision2_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.save();
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
	grv_flow grv= new grv_flow(dr);
	grv.edit();
	grv.nongrvSection();
	grv.is_forwarding();
	grv.acceptance_status();
	grv.resolution_box("grv comments.......");
	grv.save_proceed();
	grv.ticket_status();
  }

@ Test 
public void GRV2_TS043  () throws InterruptedException, SQLException
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
	cse.ssc("Premium clearance/posting delay");
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
	
	cse.type_of_payment("ECS Payment");  //Credit Card Payment,  Internet Payment
	cse.date_of_payment("14", "5", "2008");
	cse.amount();
	cse.save();
	Thread.sleep(2000);
	String case_id=cse.case_id();
	System.out.println("Case id is : "+case_id);
	String assigned_to=cse.assigned_to();
	String case_owner=cse.Case_owner();
	System.out.println("Assigned To : "+assigned_to);
	System.out.println("Case Owner is : "+case_owner);
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
	methods.Ticket_search_flow ts= new methods.Ticket_search_flow(dr);
	ts.Ticketsearchlink1();
	ts.EnterTcktNum(case_id);
	ts.fetchButton();
	ts.Validate();
	sc.edit();
	sc.scrutiny_section();
	
	sc.cd("Not Applicable");
	sc.rca_comment("scrutiny comment");
	sc.IRDAProb("Scrutiny IRDA comments");
	sc.rca_picker();
	Thread.sleep(500);
	sc.scrutiny_save();
	String assigned_to_1=cse.assigned_to();
	System.out.println("Assigned To : "+assigned_to_1);
	while(assigned_to_1.equalsIgnoreCase(abc))
	{
		sc.refresh();
		assigned_to_1=cse.assigned_to();
	}
	System.out.println("Assigned to is :"+assigned_to_1);
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
	
	Forwardee_dept_flow fl= new Forwardee_dept_flow(dr);
	fl.edit();
	Thread.sleep(2000);
	fl.forwardee_section();
	fl.forwardee_comment("renewals comment......");
	fl.decision_picker();
	fl.window_handling(dr);
	fl.desicion_search_box("Accept");
	fl.AmtSuspenseAcc("No");
	fl.save();
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

// refactored on 20 jul'17s
