/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Connection.myConnection;
import clases.c_cargo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 18432
 */
public class md_proveedor {
      public ArrayList<c_cargo> getCargos(){
    
        Connection con = myConnection.getConnection();
        Statement stmt;
        ResultSet rs;
        
        ArrayList<c_cargo> ListaCargos = new ArrayList<>();
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM cargo_empleado");
            
            while(rs.next()){
             
                c_cargo cargos = new c_cargo();
                cargos.setId_cargo(rs.getInt("id_cargo"));
                cargos.setDescripcion(rs.getString("descripcion_cargo"));
                ListaCargos.add(cargos);
              
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(md_proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return ListaCargos;
    }
}
