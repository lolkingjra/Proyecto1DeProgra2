/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ja215
 */
public class DataBaseConnect {
    
   public static Connection getConnection(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:oracle:thin:@Josearias:1521:XE";
            String user = "rifa";
            String password = "1234";
            
            Connection cnx = DriverManager.getConnection(myDB, user, password);
            
            return cnx;
            
        } catch(SQLException ex){
            
            System.out.println(ex.getMessage());
            
        } catch(ClassNotFoundException ex){
            
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return null;
            
           
   }
   
}
