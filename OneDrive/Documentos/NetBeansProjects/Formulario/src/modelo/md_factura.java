
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
import clases.c_guardar_factura;
import clases.c_detalle_factura;

public class md_factura {
       myConnection cc = new myConnection();
       Connection cn = myConnection.getConnection();
       PreparedStatement ps; 
       ResultSet rs;
       int r=0;
       
       
    public int GuardarFactura(c_guardar_factura Fact){
       c_guardar_factura factura=new c_guardar_factura();
       String sql="insert into factura(id_cliente, id_empleado, NCF, DNI, Subtotal, ITBIS, Total, estado_factura ) values(?,?,?,?,?,?,?,1)";
       try{
         
           Connection cn = myConnection.getConnection();
           ps = cn.prepareStatement(sql);
           ps.setInt(1,Fact.getIdCliente());
           ps.setInt(2,Fact.getIdEmp());
           ps.setString(3,Fact.getNCF());
           ps.setString(4,Fact.getDNI());
           ps.setFloat(5,Fact.getSubtotal());
           ps.setFloat(6,Fact.getItbis());
           ps.setFloat(7,Fact.getTotal());
           r=ps.executeUpdate();
           
        }catch(SQLException ex){
        Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE,null,ex);
            JOptionPane.showMessageDialog(null, "Error: "+ex);
        }
    
        return r;
    }
    
    public int GuardarDetalleVenta(c_detalle_factura dv){
        String sql = "insert into detalle_factura(id_factura,id_producto,cantidad_vendida,importe) values(?,?,?,?)";
        
        try{
        Connection cn = myConnection.getConnection();
        ps = cn.prepareStatement(sql);
        ps.setInt(1, dv.getIdFact());
        ps.setInt(2, dv.getIdProd());
        ps.setInt(3, dv.getCantidad_vendida());
        ps.setFloat(4, dv.getImporte());
        
        ps.executeUpdate();
         
         
        }catch(SQLException ex){
        Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE,null,ex);
            JOptionPane.showMessageDialog(null, "Error:: "+ex);
        }
        
    return r;
    }
    
    
}
