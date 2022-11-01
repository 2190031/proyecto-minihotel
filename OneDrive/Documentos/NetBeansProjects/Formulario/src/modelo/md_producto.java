
package modelo;

import forms.frm_menu;
import Connection.myConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import clases.c_producto;

public class md_producto {
    int r;
    PreparedStatement ps;
    ResultSet rs;
    Connection cn;
    
    public int ActualizarStock(int id_p, int cantidad){
        
        String sql= "update producto set cant_disponible=? where id_producto=?";
        try{
        Connection cn = myConnection.getConnection();
        ps=cn.prepareStatement(sql);
        ps.setInt(2, id_p);
        ps.setInt(1, cantidad);
        ps.executeUpdate();
        
        }catch(SQLException ex){
           Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE,null,ex);
           JOptionPane.showMessageDialog(null, "Error:: "+ex);
        
        }
        
        
    
        
        
    return r;
    }
    public c_producto ListarId(int id_p){
    c_producto p= new c_producto();     
    String sql="select * from producto where id_producto=?";
   try{
        Connection cn = myConnection.getConnection();
        ps = cn.prepareStatement(sql);
        ps.setInt(1, id_p);
        rs = ps.executeQuery();
        while(rs.next()){
            p.setId_p(rs.getInt(1));
            p.setNombre_p(rs.getString(2));
            p.setPresentacion(rs.getString(3));
            p.setCant_disponible(rs.getInt(4));
            p.setId_categoira(rs.getInt(5));
            p.setUnidad_precio(rs.getFloat(6));
            p.setPuntos_producto(rs.getInt(7));
            p.setDescuento(rs.getInt(8));
            p.setEstado(rs.getInt(9));
        }
   
   
   }catch(SQLException ex){
           Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE,null,ex);
           JOptionPane.showMessageDialog(null, "Error: "+ex);
        
        }
    
        return p;
    }
    
    
      
}
