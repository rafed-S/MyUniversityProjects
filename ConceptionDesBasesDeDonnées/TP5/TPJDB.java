import java.sql.*;
import java.io.*;

public class TPJDB{

        static String server = "jdbc:oracle:thin:@ens-oracle.fst.univ-lorraine.fr:1521:atelis";
        static Connection con;


        public static void openConnection(){
           try{
                 Class.forName("oracle.jdbc.driver.OracleDriver");


              }catch(ClassNotFoundException e){
                System.out.println("driver non trouvÃ©");
                }

                try{
                        con = DriverManager.getConnection(server,"XXX","Oracle23");


                }catch(SQLException e){
                        System.out.println("there");
                        System.out.println(e.getMessage());
                }
        }

        public static void q1(){

            try{
                Statement stmt = con.createStatement();
                ResultSet rset = stmt.executeQuery("SELECT * FROM HR.EMPLOYEES WHERE salary >(Select avg(salary) from hr.employees)");
                while(rset.next()){
                 System.out.println(rset.getString("employee_id") + " " + rset.getString("first_name") + " "
 + rset.getString("last_name") + "  " + rset.getDouble("salary"));

                }
                rset.close();
                stmt.close();

                }catch(SQLException e){
                //      System.out.println("here");
                        System.out.println(e.getMessage());
                }
			}
			
					
public static void main(String[] args){
                openConnection();
                q1();
                try{
                        con.close();
                } catch (SQLException ex){
                        System.out.println("SQLException : " + ex.getMessage());
                }
        }}




