package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class myConnection {
    public static Connection getConnection() {
        Connection con = null;
        
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_emi","root","c55h32o5n4Mg");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        return con;
    }

   
    
}

