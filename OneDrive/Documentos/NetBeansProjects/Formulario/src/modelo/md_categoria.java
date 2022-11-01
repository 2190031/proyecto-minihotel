/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Connection.myConnection;
import clases.c_categoria;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class md_categoria {
     public ArrayList<c_categoria> getCategorias(){
    
        Connection con = myConnection.getConnection();
        Statement stmt;
        ResultSet rs;
        
        ArrayList<c_categoria> ListaCategorias = new ArrayList<>();
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM categoria");
            
            while(rs.next()){
             
                c_categoria categorias = new c_categoria();
                categorias.setId_categoria(rs.getInt("id_categoria"));
                categorias.setDescripcion_categoria(rs.getString("descripcion"));
                ListaCategorias.add(categorias);
              
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(md_categoria.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return ListaCategorias;
    }
}
