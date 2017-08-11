package base;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestConnectivity 
{
  static java.sql.Connection con= null;
  private static java.sql.Statement stmt;
  public static String DB_URL= "jdbc:msql:192.168.0.7\\mssql2012:1433;database=Max_Prod_SA";
  public static String DB_USER = "crmnext";
  public static String DB_PASSWORD = "abc123";
  public static String database="Max_Prod_SA";
  @BeforeMethod
  public void setup()
  {
	  try
	  {
		  String dbClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
          Class.forName(dbClass).newInstance();
		  con=DriverManager.getConnection("jdbc:sqlserver://192.168.0.7\\mssql2012:55461;user=crmnext;password=abc123;database=Max_Prod_SA");
		  System.out.println("Database sucessfully connected.");
		  stmt = con.createStatement();
	  }
	  catch(Exception e)
	  {
		  System.out.println("first catch");
		  e.printStackTrace();
	  }
  }
  @Test
  public String querry(String ar) throws SQLException
  {
	  ResultSet res = null;
	  String abc=null;
	  try
	  {
		  String query= ar;
		  res = stmt.executeQuery(query);
		  ResultSetMetaData rsMetaData = res.getMetaData();
	      int numberOfColumns = rsMetaData.getColumnCount();
	      for(int i = 1; i <= numberOfColumns; i++)
          {
               System.out.print(rsMetaData.getColumnLabel(i) + " ");
          }
	      System.out.println();
          while (res.next())
          {
               for(int e = 1; e <= rsMetaData.getColumnCount(); e++)
            {
            	 abc = res.getString(1);
                 System.out.print(res.getString(e) + " ");
            }
           System.out.println();
          }
	  }
	  catch(Exception e)
	  {
		  System.out.println("second catch");
		  e.printStackTrace();
	  }
     return(abc);
  }
 
  @AfterMethod
  public void teardown () throws SQLException
  {
	  if(con!=null)
	  {
		  con.close();
	  }
  }
}
