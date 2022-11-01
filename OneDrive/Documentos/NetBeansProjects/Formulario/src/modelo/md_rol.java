/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Connection.myConnection;
import java.util.ArrayList;
import clases.c_rol;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 18432
 */
public class md_rol {
    
    public ArrayList<c_rol> getRoles(){
    
        Connection con = myConnection.getConnection();
        Statement stmt;
        ResultSet rs;
        
        ArrayList<c_rol> ListaRoles = new ArrayList<>();
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM rol");
            
            while(rs.next()){
             
                c_rol roles = new c_rol();
                roles.setId_rol(rs.getInt("id_rol"));
                roles.setDescripcion_rol(rs.getString("descripcion"));
                ListaRoles.add(roles);
              
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(md_rol.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return ListaRoles;
    }
    
}
