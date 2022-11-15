/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases.c_proveedor;
import Connection.myConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.table.DefaultTableModel;
import java.awt.HeadlessException;
import java.text.DecimalFormat;
import clases.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import modelo.md_hash;
import modelo.*;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class frm_menu extends javax.swing.JFrame {

    
   c_usuario C_usu;
   public static frm_producto prod;
   public static frm_empleado emp;
   public static frm_cliente cli;
   public static frm_login log;
   
    
    DefaultTableModel Factura;
    DefaultTableModel Almacen;
    DefaultTableModel Usuario;
    DefaultTableModel Empleado;
    DefaultTableModel Cliente;
    DefaultTableModel RepFacturas;
    DefaultTableModel Proveedor;
    DefaultTableModel FactFiltro;
    Object[] detalle = new Object[5];
    
    c_guardar_factura Factu = new c_guardar_factura();
    c_detalle_factura dv = new c_detalle_factura();
    c_proveedor prov = new c_proveedor();
    md_producto modpro = new md_producto();
    md_factura gFactu = new md_factura();
    md_usuario modUsu = new md_usuario();

    public static int id_cliente;
    public static int id_empleado;
   
    
    // -----------------------------------------------------------------------------------------------------------------
    public frm_menu(c_usuario C_usu){
        initComponents();
        setLocationRelativeTo(null);
        this.Factura = (DefaultTableModel) tbl_detalle.getModel();
        this.Almacen = (DefaultTableModel) tbl_almacen.getModel();
        this.Usuario = (DefaultTableModel) tbl_usuario.getModel();
        this.Empleado = (DefaultTableModel) tbl_empleado.getModel();
        this.RepFacturas = (DefaultTableModel) tbl_repFacturas.getModel();
        this.Cliente = (DefaultTableModel) tbl_cliente.getModel();
        this.Proveedor = (DefaultTableModel) tbl_proveedores.getModel();
        this.FactFiltro = (DefaultTableModel) tbl_factFiltro.getModel();
        MostrarDatosAlmacen("","");
        MostrarDatosUsuario("","");
        MostrarDatosEmpleado("","");
        MostrarDatosFactura("");
        MostrarDatosProveedor("","");
        MostrarTablaRepFactura("");
        MostrarDatosCliente("","");
        MostrarDatosFactFiltro("");
        //Generar serie de factura
        generarIdFact();
        SetFacturaID();
        //Generar NCF de factura
        generarNCF_Fact();
        SetNCF_ID();
        //Llenar los roles del CBX
        llenarRoles();
        //Llenar los cargos de empleado del CBX
        llenarCargos();
        //Llenar los categorias de los productos
        llenarCategorias();
        
        
        this.C_usu = C_usu;
        //verificacion de roles
        if(C_usu.getId_rol() == 3){
          
          jTabbedPane1.remove(3);
          jTabbedPane1.remove(4);
          jTabbedPane1.remove(2);
          btn_añadir_prod.setVisible(false);
          btn_borrar_prod.setVisible(false);
          btn_editar_prod.setVisible(false);
          
        }
    }
    
    public frm_menu(){
         initComponents();
         setLocationRelativeTo(null);
        this.Factura = (DefaultTableModel) tbl_detalle.getModel();
        this.Almacen = (DefaultTableModel) tbl_almacen.getModel();
        this.Usuario = (DefaultTableModel) tbl_usuario.getModel();
        this.Empleado = (DefaultTableModel) tbl_empleado.getModel();
        this.RepFacturas = (DefaultTableModel) tbl_repFacturas.getModel();
        this.Cliente = (DefaultTableModel) tbl_cliente.getModel();
        this.Proveedor = (DefaultTableModel) tbl_proveedores.getModel();
        this.FactFiltro = (DefaultTableModel) tbl_factFiltro.getModel();
        MostrarDatosAlmacen("","");
        MostrarDatosUsuario("","");
        MostrarDatosEmpleado("","");
        MostrarDatosProveedor("","");
        MostrarDatosFactura("");
        MostrarTablaRepFactura("");
        MostrarDatosCliente("","");
        MostrarDatosFactFiltro("");
        //Generar serie de factura
        generarIdFact();
        SetFacturaID();
        //Generar NCF de factura
        generarNCF_Fact();
        SetNCF_ID();
        //Llenar los roles del CBX
        llenarRoles();
        //Llenar los cargos de empleado del CBX
        llenarCargos();
        //Llenar los categorias de los productos
        llenarCategorias();
    }
     
     
    //Clases  ----------------------------------------------------------------------------------------------------------
    public static void llenarRoles() {
        md_rol modRoles = new md_rol();
        ArrayList<c_rol> listaRoles = modRoles.getRoles();

        cbb_rol_empleado.removeAllItems();
        cbb_rol_cliente.removeAllItems();
        for (int i = 0; i < listaRoles.size(); i++) {
            cbb_rol_empleado.addItem(new c_rol(listaRoles.get(i).getId_rol(), listaRoles.get(i).getDescripcion_rol()));
            cbb_rol_cliente.addItem(new c_rol(listaRoles.get(i).getId_rol(), listaRoles.get(i).getDescripcion_rol()));
        }
        cbb_rol_cliente.removeItemAt(2);
        cbb_rol_empleado.removeItemAt(1);
    
    }

    public static void llenarCargos() {
        md_cargo modCargos = new md_cargo();
        ArrayList<c_cargo> listaCargos = modCargos.getCargos();
        cbb_cargo_empleado.removeAllItems();
        for (int i = 0; i < listaCargos.size(); i++) {
            cbb_cargo_empleado.addItem(new c_cargo(listaCargos.get(i).getId_cargo(), listaCargos.get(i).getDescripcion()));
        }
    }

    public static void llenarCategorias() {
        md_categoria modCategorias = new md_categoria();
        ArrayList<c_categoria> listaCategorias = modCategorias.getCategorias();
        cbb_categoria_producto.removeAllItems();
        for (int i = 0; i < listaCategorias.size(); i++) {
            cbb_categoria_producto.addItem(new c_categoria(listaCategorias.get(i).getId_categoria(), listaCategorias.get(i).getDescripcion_categoria()));
        }
    }

    public static void llenarProveedores() {
        md_categoria modCategorias = new md_categoria();
        ArrayList<c_categoria> listaCategorias = modCategorias.getCategorias();
        cbb_categoria_producto.removeAllItems();
        for (int i = 0; i < listaCategorias.size(); i++) {
            cbb_categoria_producto.addItem(new c_categoria(listaCategorias.get(i).getId_categoria(), listaCategorias.get(i).getDescripcion_categoria()));
        }
    }

    
   //Refrescar ---------------------------------------------------------------------------------------------------------
    public void RefrescarTablaProveedor() {
        //Funcion encargada de Refrescar la tabla utilizando Revalidate
        try {
            Proveedor.setColumnCount(0);
            Proveedor.setRowCount(0);
            tbl_proveedores.revalidate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    public void RefrescarTablaUsuario() {
        //Funcion encargada de Refrescar la tabla utilizando Revalidate
        try {
            Usuario.setColumnCount(0);
            Usuario.setRowCount(0);
            tbl_usuario.revalidate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    public void RefrescarTablaAlmacen() {
        //Funcion encargada de Refrescar la tabla utilizando Revalidate
        try {
            Almacen.setColumnCount(0);
            Almacen.setRowCount(0);
            tbl_almacen.revalidate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    public void RefrescarTablaEmpleado() {
        //Funcion encargada de Refrescar la tabla utilizando Revalidate
        try {
            Empleado.setColumnCount(0);
            Empleado.setRowCount(0);
            tbl_empleado.revalidate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }
    
    public void RefrescarTablaCliente() {
        //Funcion encargada de Refrescar la tabla utilizando Revalidate
        try {
            Cliente.setColumnCount(0);
            Cliente.setRowCount(0);
            tbl_cliente.revalidate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    public void RefrescarTablaFactura() {
        //Funcion encargada de Refrescar la tabla utilizando Revalidate
        try {
            Factura.setColumnCount(0);
            Factura.setRowCount(0);
            tbl_detalle.revalidate();
            DecimalFormat formato_decimal = new DecimalFormat("#.00");
            lbl_subtotal.setText(String.valueOf("$" + suma()));
            lbl_itbis.setText(String.valueOf("$" + ITBIS()));
            lbl_total_.setText(String.valueOf("$" + total()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }
    
    public void RefrescarTablaRepFactura() {
        //Funcion encargada de Refrescar la tabla utilizando Revalidate
        try {
            RepFacturas.setColumnCount(0);
            RepFacturas.setRowCount(0);
            tbl_repFacturas.revalidate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }
    
    public void RefrescarTablaFactFiltro() {
        try {
        FactFiltro.setColumnCount(0);
        FactFiltro.setRowCount(0);
        tbl_factFiltro.revalidate();
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }
    //Limpiar ----------------------------------------------------------------------------------------------------------
    public void LimpiarCamposFactura() {

        txt_producto_fact.setText("");
        txt_precio_prod_fact.setText("");
        txt_producto_cant.setValue(1);
        txt_id_prod_fact.setText("Buscar producto");

    }
    
    public void LimpiarCamposCliente() {

        txt_id_cliente.setText("");
        txt_nom_cliente.setText("");
        txt_apellido_cliente.setText("");
        txt_dni_cliente.setText("");
        txt_direccion_cliente.setText("");
        txt_correo_cliente.setText("");
        

    }

    public void LimpiarCamposPersonalesFactura() {

    
        txt_nom_user_fact.setText("Buscar cliente");
        txt_dni_usu_fact.setText("");
        txt_id_prod_fact.setText("Buscar producto");
    }

    public void LimpiarCamposUsuario() {
        txt_id_usu.setText("");
        txt_nom_usu.setText("");
        txt_nom_com.setText("");
        txt_correo.setText("");
        txt_clave.setText("");
        txt_correo.setText("");

    }

    public void LimpiarCamposAlmacen() {
        txt_id_prod.setText("");
        txt_nom_prod.setText("");
        txt_pres_prod.setText("");
        txt_cantidad_prod.setText("");
        cbb_categoria_producto.setSelectedIndex(0);
        txt_precio_venta_prod.setText("");
        txt_puntos_prod.setText("");
    }

    public void LimpiarCamposProveedor() {
        txt_id_proveedor.setText("");
        txt_nom_proveedor.setText("");
        txt_apellido_proveedor.setText("");
        txt_tel_proveedor.setText("");
        txt_direccion_proveedor.setText("");
    }
    
    public void LimpiarCamposEmpleado() {
        txt_id_emp.setText("");
        txt_nombre_emp.setText("");
        txt_apellido_emp.setText("");
        txt_dni.setText("");
        txt_direccion.setText("");
        txt_correo_emp.setText("");
        txt_clave_emp.setText("");
        txt_telefono.setText("");

    }

    public void LimpiarDatosProdFact() {
        txt_id_prod_fact.setText("Buscar producto");
        txt_producto_fact.setText("");
        txt_precio_prod_fact.setText("");
        txt_producto_cant.setValue(1);
    }

    
    //Mostrar  ---------------------------------------------------------------------------------------------------------
    public final void MostrarDatosFactura(String valor) {
        myConnection cc = new myConnection();
        Connection cn = myConnection.getConnection();
        RefrescarTablaFactura();
        Factura.addColumn("Id_factura");
        Factura.addColumn("Id_producto");
        Factura.addColumn("Cantidad_vendida");
        Factura.addColumn("Importe");
        Factura.addColumn("ITBIS");
        this.tbl_detalle.setModel(Factura);

    }
    
    public final void MostrarDatosEmpleado(String valor, String tipo) {
        //Funcion para llenar la jtable de Usuarios desde la BD
        myConnection cc = new myConnection();
        Connection cn = myConnection.getConnection();
        RefrescarTablaEmpleado();
        Empleado.addColumn("id_empleado");
        Empleado.addColumn("nombre_empleado");
        Empleado.addColumn("apellido_empleado");
        Empleado.addColumn("dni");
        Empleado.addColumn("telefono");
        Empleado.addColumn("direccion_empleado");
        Empleado.addColumn("id_cargo");
        Empleado.addColumn("id_rol");
        Empleado.addColumn("correo_empleado");
        Empleado.addColumn("clave_empleado");
        Empleado.addColumn("ultimo acceso");

        this.tbl_empleado.setModel(Empleado);

        String sql = "";

        if (valor.equals("")) {
            sql = "SELECT * FROM empleado";

        } else {
            int tipo_filtrar = Integer.parseInt(tipo);
            switch (tipo_filtrar) {
                case 0:
                    sql = "SELECT * FROM empleado WHERE id_empleado like '%" + valor + "%'";
                    break;
                case 1:
                    sql = "SELECT * FROM empleado WHERE nombre_empleado like '%" + valor + "%'";
                    break;
                case 2:
                    sql = "SELECT * FROM empleado WHERE dni like '%" + valor + "%'";
                    break;
                default:
                    break;
            }

        }

        String[] datos = new String[11];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);

                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                datos[9] = rs.getString(10);
                datos[10] = rs.getString(11);

                Empleado.addRow(datos);
            }
            tbl_empleado.setModel(Empleado);
        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }
  
    public final void MostrarDatosCliente(String valor, String tipo) {
        //Funcion para llenar la jtable de Usuarios desde la BD
        myConnection cc = new myConnection();
        Connection cn = myConnection.getConnection();
        RefrescarTablaCliente();
        Cliente.addColumn("id_cliente");
        Cliente.addColumn("nombre_cliente");
        Cliente.addColumn("apellido_cliente");
        Cliente.addColumn("dni");
        Cliente.addColumn("telefono");
        Cliente.addColumn("correo_contacto");
        Cliente.addColumn("direccion_cliente");
     
 

        this.tbl_cliente.setModel(Cliente);

        String sql = "";

        if (valor.equals("")) {
            sql = "SELECT * FROM cliente";

        } else {
            int tipo_filtrar = Integer.parseInt(tipo);
            switch (tipo_filtrar) {
                case 0:
                    sql = "SELECT * FROM cliente WHERE id_cliente like '%" + valor + "%'";
                    break;
                case 1:
                    sql = "SELECT * FROM cliente WHERE nombre_cliente like '%" + valor + "%'";
                    break;
                case 2:
                    sql = "SELECT * FROM cliente WHERE apellido_cliente like '%" + valor + "%'";
                    break;
                 case 3:
                    sql = "SELECT * FROM cliente WHERE dni_cliente like '%" + valor + "%'";
                    break;    
                case 4:
                    sql = "SELECT * FROM cliente WHERE correo_cliente like '%" + valor + "%'";
                    break;    
                    
                default:
                    break;
            }

        }

        String[] datos = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);

                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
              

                Cliente.addRow(datos);
            }
            tbl_cliente.setModel(Cliente);
        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    public final void MostrarDatosAlmacen(String valor, String tipo) {
        //Funcion para llenar la jtable de Usuarios desde la BD
        myConnection cc = new myConnection();
        Connection cn = myConnection.getConnection();
        RefrescarTablaAlmacen();
        Almacen.addColumn("id_producto");
        Almacen.addColumn("nombre_producto");
        Almacen.addColumn("presentacion");
        Almacen.addColumn("cant_disponible");
        Almacen.addColumn("id_categoria");
        Almacen.addColumn("unidad_precio");
        Almacen.addColumn("puntos_producto");

        this.tbl_almacen.setModel(Almacen);

        String sql = "";

        if (valor.equals("")) {
            sql = "SELECT * FROM producto";

        } else {
            int tipo_filtrar = Integer.parseInt(tipo);
            switch (tipo_filtrar) {
                case 0:
                    sql = "SELECT * FROM producto WHERE id_producto like '%" + valor + "%'";
                    break;
                case 1:
                    sql = "SELECT * FROM producto WHERE nombre_producto like '%" + valor + "%'";
                    break;
                case 2:
                    sql = "SELECT * FROM producto WHERE cant_disponible like '%" + valor + "%'";
                    break;
                default:
                    break;
            }

        }
        String[] datos = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);

                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);

                Almacen.addRow(datos);
            }
            tbl_almacen.setModel(Almacen);
        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    public final void MostrarDatosUsuario(String valor, String tipo) {
        //Funcion para llenar la jtable de Usuarios desde la BD
        myConnection cc = new myConnection();
        Connection cn = myConnection.getConnection();
        RefrescarTablaUsuario();
        Usuario.addColumn("id_usuario");
        Usuario.addColumn("nombre_completo");
        Usuario.addColumn("nombre_usuario");
        Usuario.addColumn("correo_electronico");
        Usuario.addColumn("Clave");
        Usuario.addColumn("afiliacion");
        Usuario.addColumn("id_rol");

        this.tbl_usuario.setModel(Usuario);

        String sql = "";

        if (valor.equals("")) {
            sql = "SELECT * FROM usuario";

        } else {
            int tipo_filtrar = Integer.parseInt(tipo);
            switch (tipo_filtrar) {
                case 0:
                    sql = "SELECT * FROM usuario WHERE id_usuario like '%" + valor + "%'";
                    break;
                case 1:
                    sql = "SELECT * FROM usuario WHERE nombre_usuario like '%" + valor + "%'";
                    break;
                case 2:
                    sql = "SELECT * FROM usuario WHERE correo_electronico like '%" + valor + "%'";
                    break;
                default:
                    break;
            }

        }

        String[] datos = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);

                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);

                Usuario.addRow(datos);
            }
            tbl_usuario.setModel(Usuario);
        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    public final void MostrarTablaRepFactura(String valor) {
        myConnection cc = new myConnection();
        Connection cn = myConnection.getConnection();
        RefrescarTablaRepFactura();
        RepFacturas.addColumn("id_factura");
        RepFacturas.addColumn("id_cliente");
        RepFacturas.addColumn("id_empleado");
        RepFacturas.addColumn("NCF");
        RepFacturas.addColumn("DNI");
        RepFacturas.addColumn("tipo_pago");
        RepFacturas.addColumn("documento_transaccional");
        RepFacturas.addColumn("subtotal");
        RepFacturas.addColumn("ITBIS");
        RepFacturas.addColumn("Total");
        RepFacturas.addColumn("fecha_compra");
        RepFacturas.addColumn("estado_factura");
        this.tbl_repFacturas.setModel(RepFacturas);
        
        String sql = "";
        
        if( valor.equals("")){
         sql = "SELECT * FROM factura";
         
        }

        String[] datos = new String[12];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                datos[9] = rs.getString(10);
                datos[10] = rs.getString(11);
                datos[11] = rs.getString(12);
                
                RepFacturas.addRow(datos);
            }
            tbl_repFacturas.setModel(RepFacturas);
        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }

    }
//    
//    public final void MostrarDatosRepFactura(String valor, String tipo) {
//        //Funcion para llenar la jtable de Usuarios desde la BD
//        myConnection cc = new myConnection();
//        Connection cn = myConnection.getConnection();
//        RefrescarTablaEmpleado();
//        Empleado.addColumn("id_empleado");
//        Empleado.addColumn("nombre_empleado");
//        Empleado.addColumn("apellido_empleado");
//        Empleado.addColumn("dni");
//        Empleado.addColumn("telefono");
//        Empleado.addColumn("direccion_empleado");
//        Empleado.addColumn("id_cargo");
//
//        this.tbl_empleado.setModel(Empleado);
//        
//       String sql = "";
//        
//        if( valor.equals("")){
//         sql = "SELECT * FROM empleado";
//         
//        } else {
//         int tipo_filtrar = Integer.parseInt(tipo);
//         switch (tipo_filtrar) {
//            case 0:
//                sql = "SELECT * FROM empleado WHERE id_empleado like '%" + valor + "%'";
//                break;
//            case 1:
//                sql = "SELECT * FROM empleado WHERE nombre_empleado like '%" + valor + "%'";
//                break;
//            case 2:
//                sql = "SELECT * FROM empleado WHERE cant_disponible like '%" + valor + "%'";
//                break;
//            default:
//                break;
//        }
//         
//        }
//
//        String[] datos = new String[7];
//        try {
//            Statement st = cn.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            while (rs.next()) {
//                datos[0] = rs.getString(1);
//                datos[1] = rs.getString(2);
//                datos[2] = rs.getString(3);
//                datos[3] = rs.getString(4);
//
//                datos[4] = rs.getString(5);
//                datos[5] = rs.getString(6);
//                datos[6] = rs.getString(7);
//
//                Empleado.addRow(datos);
//            }
//            tbl_empleado.setModel(Empleado);
//        } catch (SQLException ex) {
//            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, "error " + ex);
//        }
//    }

    public final void MostrarDatosProveedor(String valor, String tipo) {
        //Funcion para llenar la jtable de Usuarios desde la BD
        myConnection cc = new myConnection();
        Connection cn = myConnection.getConnection();
        RefrescarTablaProveedor();
        Proveedor.addColumn("id_proveedor");
        Proveedor.addColumn("nombre_proveedor");
        Proveedor.addColumn("apellido_proveedor");
        Proveedor.addColumn("telefono");
        Proveedor.addColumn("direccion");
     
        this.tbl_proveedores.setModel(Proveedor);

        String sql = "";

        if (valor.equals("")) {
            sql = "SELECT * FROM proveedor";

        } else {
         int tipo_filtrar = Integer.parseInt(tipo);
         switch (tipo_filtrar) {
            case 0:
                sql = "SELECT * FROM proveedor WHERE id_proveedor like '%" + valor + "%'";
                break;
            case 1:
                sql = "SELECT * FROM proveedor WHERE nombre_proveedor like '%" + valor + "%'";
                break;
            case 2:
                sql = "SELECT * FROM proveedor WHERE apellido_proveedor like '%" + valor + "%'";
                break;
            case 3:
                sql = "SELECT * FROM proveedor WHERE telefono like '%" + valor + "%'";
                break;
            default:
                break;
        }
         
        }

        String[] datos = new String[5];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
              

                Proveedor.addRow(datos);
            }
            tbl_proveedores.setModel(Proveedor);
            
           
        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }

    public final void MostrarDatosFactFiltro(String valor) {
        myConnection cc = new myConnection();
        Connection cn = myConnection.getConnection();
        RefrescarTablaFactFiltro();
        FactFiltro.addColumn("id_factura");
        FactFiltro.addColumn("id_cliente");
        FactFiltro.addColumn("id_empleado");
        FactFiltro.addColumn("NCF");
        FactFiltro.addColumn("DNI");
        FactFiltro.addColumn("tipo_pago");
        FactFiltro.addColumn("documento_transaccional");
        FactFiltro.addColumn("subtotal");
        FactFiltro.addColumn("ITBIS");
        FactFiltro.addColumn("Total");
        FactFiltro.addColumn("fecha_compra");
        FactFiltro.addColumn("estado_factura");
        this.tbl_factFiltro.setModel(FactFiltro);
        
       
        
        
       String sql = "SELECT * FROM factura WHERE Fecha_compra LIKE '______"+ valor +"____________'";
         
        // INCLUIR MENSAJE CUANDO NO HAY RESULTADOS

        String[] datos = new String[12];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                datos[9] = rs.getString(10);
                datos[10] = rs.getString(11);
                datos[11] = rs.getString(12);
                
                FactFiltro.addRow(datos);
            }
            tbl_factFiltro.setModel(FactFiltro);
        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        img_logo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        t_rep_facturas = new javax.swing.JPanel();
        lbl_factura1 = new javax.swing.JLabel();
        img_factura1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_repFacturas = new javax.swing.JTable();
        btn_rep_facts = new javax.swing.JButton();
        t_empleado = new javax.swing.JPanel();
        pnl_empleado = new javax.swing.JPanel();
        lbl_nombre1 = new javax.swing.JLabel();
        lbl_apellido = new javax.swing.JLabel();
        txt_nombre_emp = new javax.swing.JTextField();
        txt_apellido_emp = new javax.swing.JTextField();
        lbl_cargo = new javax.swing.JLabel();
        lbl_dni = new javax.swing.JLabel();
        txt_dni = new javax.swing.JTextField();
        btn_borrar_empleado = new javax.swing.JButton();
        btn_agregar_empleado = new javax.swing.JButton();
        btn_modificar_empleado = new javax.swing.JButton();
        lbl_direccion = new javax.swing.JLabel();
        txt_direccion = new javax.swing.JTextField();
        btn_reporte_empleado = new javax.swing.JButton();
        lbl_email = new javax.swing.JLabel();
        txt_correo_emp = new javax.swing.JTextField();
        txt_id_emp = new javax.swing.JTextField();
        lbl_id_emp = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_empleado = new javax.swing.JTable();
        Filtro_emp = new javax.swing.JComboBox<>();
        txt_filtro_emp = new javax.swing.JTextField();
        lbl_afiliacion_usuario3 = new javax.swing.JLabel();
        cbb_rol_empleado = new javax.swing.JComboBox<>();
        lbl_cargo1 = new javax.swing.JLabel();
        cbb_cargo_empleado = new javax.swing.JComboBox<>();
        lbl_telefono1 = new javax.swing.JLabel();
        txt_telefono = new javax.swing.JTextField();
        lbl_clave = new javax.swing.JLabel();
        txt_clave_emp = new javax.swing.JPasswordField();
        lbl_empleado = new javax.swing.JLabel();
        img_empleado = new javax.swing.JLabel();
        t_cliente = new javax.swing.JPanel();
        pnl_usuario1 = new javax.swing.JPanel();
        txt_dni_cliente = new javax.swing.JTextField();
        txt_nom_cliente = new javax.swing.JTextField();
        txt_correo_cliente = new javax.swing.JTextField();
        lbl_nom_com1 = new javax.swing.JLabel();
        lbl_nom_usu1 = new javax.swing.JLabel();
        lbl_correo_usuario2 = new javax.swing.JLabel();
        lbl_apellido_cliente = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_cliente = new javax.swing.JTable();
        btn_añadir_cliente = new javax.swing.JButton();
        btn_borrar_cliente = new javax.swing.JButton();
        lbl_id_usuario1 = new javax.swing.JLabel();
        btn_editar_cliente = new javax.swing.JButton();
        txt_id_cliente = new javax.swing.JTextField();
        txt_filtro_cliente = new javax.swing.JTextField();
        Filtro_cli = new javax.swing.JComboBox<>();
        lbl_afiliacion_client = new javax.swing.JLabel();
        lbl_correo_usuario3 = new javax.swing.JLabel();
        txt_direccion_cliente = new javax.swing.JTextField();
        lbl_apellido_cliente1 = new javax.swing.JLabel();
        txt_telf_cliente = new javax.swing.JTextField();
        txt_apellido_cliente = new javax.swing.JTextField();
        lbl_usuario_titulo1 = new javax.swing.JLabel();
        img_cliente = new javax.swing.JLabel();
        t_usuario = new javax.swing.JPanel();
        pnl_usuario = new javax.swing.JPanel();
        txt_nom_com = new javax.swing.JTextField();
        txt_clave = new javax.swing.JPasswordField();
        txt_nom_usu = new javax.swing.JTextField();
        lbl_rol_usuario = new javax.swing.JLabel();
        txt_correo = new javax.swing.JTextField();
        lbl_nom_com = new javax.swing.JLabel();
        lbl_nom_usu = new javax.swing.JLabel();
        lbl_correo_usuario = new javax.swing.JLabel();
        lbl_clave_usuario = new javax.swing.JLabel();
        lbl_afiliacion_usuario = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_usuario = new javax.swing.JTable();
        btn_añadir_usuario = new javax.swing.JButton();
        btn_borrar_usuario = new javax.swing.JButton();
        lbl_id_usuario = new javax.swing.JLabel();
        btn_editar_usuario = new javax.swing.JButton();
        txt_id_usu = new javax.swing.JTextField();
        txt_filtro_usu = new javax.swing.JTextField();
        Filtro_usu = new javax.swing.JComboBox<>();
        lbl_afiliacion_usuario1 = new javax.swing.JLabel();
        cbb_rol_cliente = new javax.swing.JComboBox<>();
        cbb_afiliacion = new javax.swing.JComboBox<>();
        lbl_usuario_titulo = new javax.swing.JLabel();
        img_usuario = new javax.swing.JLabel();
        t_proveedor = new javax.swing.JPanel();
        pnl_proveedor = new javax.swing.JPanel();
        txt_nom_proveedor = new javax.swing.JTextField();
        lbl_nom_proveedor = new javax.swing.JLabel();
        lbl_apellido_proveedor = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_proveedores = new javax.swing.JTable();
        btn_añadir_proveedor = new javax.swing.JButton();
        btn_borrar_proveedor = new javax.swing.JButton();
        lbl_id_proveedor = new javax.swing.JLabel();
        btn_editar_proveedor = new javax.swing.JButton();
        txt_id_proveedor = new javax.swing.JTextField();
        txt_filtro_proveedor = new javax.swing.JTextField();
        Filtro_prov = new javax.swing.JComboBox<>();
        lbl_filtro_prov = new javax.swing.JLabel();
        lbl_direccion_proveedor = new javax.swing.JLabel();
        txt_apellido_proveedor = new javax.swing.JTextField();
        txt_tel_proveedor = new javax.swing.JTextField();
        txt_direccion_proveedor = new javax.swing.JTextField();
        lbl_telefono_proveedor = new javax.swing.JLabel();
        lbl_proveedor_titulo = new javax.swing.JLabel();
        img_proveedor = new javax.swing.JLabel();
        t_almacen = new javax.swing.JPanel();
        lbl_productos = new javax.swing.JLabel();
        img_producto = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_almacen = new javax.swing.JTable();
        btn_añadir_prod = new javax.swing.JButton();
        btn_borrar_prod = new javax.swing.JButton();
        btn_editar_prod = new javax.swing.JButton();
        lbl_id_prod = new javax.swing.JLabel();
        lbl_nom_prod = new javax.swing.JLabel();
        lbl_pres_prod = new javax.swing.JLabel();
        lbl_cant_prod = new javax.swing.JLabel();
        lbl_puntos_prod = new javax.swing.JLabel();
        lbl_precio_prod = new javax.swing.JLabel();
        lbl_categoria_prod = new javax.swing.JLabel();
        txt_id_prod = new javax.swing.JTextField();
        txt_nom_prod = new javax.swing.JTextField();
        txt_pres_prod = new javax.swing.JTextField();
        txt_cantidad_prod = new javax.swing.JTextField();
        txt_precio_venta_prod = new javax.swing.JTextField();
        txt_puntos_prod = new javax.swing.JTextField();
        lbl_afiliacion_usuario2 = new javax.swing.JLabel();
        Filtro_prod = new javax.swing.JComboBox<>();
        txt_filtro_prod = new javax.swing.JTextField();
        cbb_categoria_producto = new javax.swing.JComboBox<>();
        lbl_precio_prod1 = new javax.swing.JLabel();
        txt_precio_compra = new javax.swing.JTextField();
        t_factura = new javax.swing.JPanel();
        lbl_factura = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lbl_cif = new javax.swing.JLabel();
        lbl_rnc = new javax.swing.JLabel();
        txt_ncf_fact = new javax.swing.JTextField();
        txt_id_factura = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        txt_nom_user_fact = new javax.swing.JTextField();
        lbl_user_fact = new javax.swing.JLabel();
        lbl_dni_fact = new javax.swing.JLabel();
        txt_dni_usu_fact = new javax.swing.JTextField();
        txt_nom_emp_fact = new javax.swing.JTextField();
        lbl_emp_fact = new javax.swing.JLabel();
        img_factura = new javax.swing.JLabel();
        t_venta = new javax.swing.JPanel();
        btn_añadir_prod_fact = new javax.swing.JButton();
        lbl_producto_fact = new javax.swing.JLabel();
        lbl_cant = new javax.swing.JLabel();
        txt_producto_fact = new javax.swing.JTextField();
        lbl_precio = new javax.swing.JLabel();
        txt_precio_prod_fact = new javax.swing.JFormattedTextField();
        lbl_id_prod_fact = new javax.swing.JLabel();
        txt_id_prod_fact = new javax.swing.JTextField();
        txt_producto_cant = new javax.swing.JSpinner();
        btn_eliminar_prod_fact = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_detalle = new javax.swing.JTable();
        btn_fin_fact = new javax.swing.JButton();
        lbl_total = new javax.swing.JLabel();
        lbl_itbis = new javax.swing.JLabel();
        lbl_total2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lbl_total_ = new javax.swing.JLabel();
        lbl_total4 = new javax.swing.JLabel();
        lbl_subtotal = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btn_reporte_empleado1 = new javax.swing.JButton();
        t_reporte = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_factFiltro = new javax.swing.JTable();
        rep_fecha = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        btn_log_out = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menú principal");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(244, 244, 244));

        img_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(219, 35, 35));

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N

        t_rep_facturas.setBackground(new java.awt.Color(255, 255, 255));

        lbl_factura1.setBackground(new java.awt.Color(170, 0, 0));
        lbl_factura1.setFont(new java.awt.Font("Corbel Light", 0, 48)); // NOI18N
        lbl_factura1.setForeground(new java.awt.Color(170, 0, 0));
        lbl_factura1.setText("Facturas hechas");

        img_factura1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bill-icon.png"))); // NOI18N

        tbl_repFacturas.setFont(new java.awt.Font("Corbel Light", 0, 24)); // NOI18N
        tbl_repFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_repFacturas.setGridColor(new java.awt.Color(255, 204, 204));
        tbl_repFacturas.setRowHeight(24);
        tbl_repFacturas.setSelectionBackground(new java.awt.Color(255, 51, 51));
        tbl_repFacturas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_repFacturasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_repFacturas);

        btn_rep_facts.setBackground(new java.awt.Color(255, 255, 255));
        btn_rep_facts.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        btn_rep_facts.setText("Generar reporte");
        btn_rep_facts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rep_factsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout t_rep_facturasLayout = new javax.swing.GroupLayout(t_rep_facturas);
        t_rep_facturas.setLayout(t_rep_facturasLayout);
        t_rep_facturasLayout.setHorizontalGroup(
            t_rep_facturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_rep_facturasLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(lbl_factura1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(img_factura1)
                .addGap(52, 52, 52))
            .addGroup(t_rep_facturasLayout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addGroup(t_rep_facturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_rep_facts, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1349, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(269, Short.MAX_VALUE))
        );
        t_rep_facturasLayout.setVerticalGroup(
            t_rep_facturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_rep_facturasLayout.createSequentialGroup()
                .addGroup(t_rep_facturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, t_rep_facturasLayout.createSequentialGroup()
                        .addContainerGap(32, Short.MAX_VALUE)
                        .addComponent(lbl_factura1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addGroup(t_rep_facturasLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(img_factura1)))
                .addGap(7, 7, 7)
                .addComponent(btn_rep_facts, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(165, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Facturas", t_rep_facturas);

        t_empleado.setBackground(new java.awt.Color(255, 255, 255));
        t_empleado.setFont(new java.awt.Font("Tahoma", 2, 20)); // NOI18N

        pnl_empleado.setBackground(new java.awt.Color(255, 255, 255));
        pnl_empleado.setForeground(new java.awt.Color(51, 51, 51));

        lbl_nombre1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_nombre1.setForeground(new java.awt.Color(51, 51, 51));
        lbl_nombre1.setText("Nombre");

        lbl_apellido.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_apellido.setForeground(new java.awt.Color(51, 51, 51));
        lbl_apellido.setText("Apellido");

        txt_nombre_emp.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_nombre_emp.setSelectionColor(new java.awt.Color(255, 102, 102));

        txt_apellido_emp.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_cargo.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_cargo.setForeground(new java.awt.Color(51, 51, 51));
        lbl_cargo.setText("Cargo");

        lbl_dni.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_dni.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dni.setText("DNI");

        txt_dni.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        btn_borrar_empleado.setBackground(new java.awt.Color(255, 255, 255));
        btn_borrar_empleado.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        btn_borrar_empleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/remove.png"))); // NOI18N
        btn_borrar_empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_borrar_empleadoActionPerformed(evt);
            }
        });

        btn_agregar_empleado.setBackground(new java.awt.Color(255, 255, 255));
        btn_agregar_empleado.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        btn_agregar_empleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plus.png"))); // NOI18N
        btn_agregar_empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_empleadoActionPerformed(evt);
            }
        });

        btn_modificar_empleado.setBackground(new java.awt.Color(255, 255, 255));
        btn_modificar_empleado.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        btn_modificar_empleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btn_modificar_empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificar_empleadoActionPerformed(evt);
            }
        });

        lbl_direccion.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_direccion.setForeground(new java.awt.Color(51, 51, 51));
        lbl_direccion.setText("Dirección");

        txt_direccion.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        btn_reporte_empleado.setBackground(new java.awt.Color(255, 255, 255));
        btn_reporte_empleado.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        btn_reporte_empleado.setText("Generar reporte");
        btn_reporte_empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporte_empleadoActionPerformed(evt);
            }
        });

        lbl_email.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_email.setForeground(new java.awt.Color(51, 51, 51));
        lbl_email.setText("Email");

        txt_correo_emp.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_id_emp.setEditable(false);
        txt_id_emp.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_id_emp.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_id_emp.setText("ID");

        tbl_empleado.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        tbl_empleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_empleado.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        tbl_empleado.setGridColor(new java.awt.Color(204, 204, 204));
        tbl_empleado.setRowHeight(24);
        tbl_empleado.setSelectionBackground(new java.awt.Color(255, 51, 51));
        tbl_empleado.getTableHeader().setReorderingAllowed(false);
        tbl_empleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_empleadoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_empleado);

        Filtro_emp.setBackground(new java.awt.Color(255, 204, 204));
        Filtro_emp.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        Filtro_emp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Nombre", "DNI" }));

        txt_filtro_emp.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_filtro_emp.setMinimumSize(new java.awt.Dimension(6, 33));
        txt_filtro_emp.setPreferredSize(new java.awt.Dimension(6, 33));
        txt_filtro_emp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_filtro_empKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filtro_empKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_filtro_empKeyTyped(evt);
            }
        });

        lbl_afiliacion_usuario3.setFont(new java.awt.Font("Corbel Light", 0, 22)); // NOI18N
        lbl_afiliacion_usuario3.setText("Filtrar por:");

        cbb_rol_empleado.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_cargo1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_cargo1.setForeground(new java.awt.Color(51, 51, 51));
        lbl_cargo1.setText("Rol");

        cbb_cargo_empleado.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_telefono1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_telefono1.setForeground(new java.awt.Color(51, 51, 51));
        lbl_telefono1.setText("Teléfono");

        txt_telefono.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_clave.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_clave.setForeground(new java.awt.Color(51, 51, 51));
        lbl_clave.setText("Clave");

        txt_clave_emp.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_clave_emp.setEchoChar('*');

        javax.swing.GroupLayout pnl_empleadoLayout = new javax.swing.GroupLayout(pnl_empleado);
        pnl_empleado.setLayout(pnl_empleadoLayout);
        pnl_empleadoLayout.setHorizontalGroup(
            pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_empleadoLayout.createSequentialGroup()
                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_empleadoLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_telefono1)
                            .addComponent(lbl_email)
                            .addComponent(lbl_apellido)
                            .addComponent(lbl_nombre1)
                            .addComponent(lbl_id_emp))
                        .addGap(42, 42, 42)
                        .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_empleadoLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_id_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_apellido_emp)
                                        .addComponent(txt_nombre_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(txt_correo_emp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_direccion)
                                .addComponent(cbb_cargo_empleado, 0, 300, Short.MAX_VALUE)
                                .addComponent(txt_dni))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(pnl_empleadoLayout.createSequentialGroup()
                                        .addComponent(lbl_clave)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_clave_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnl_empleadoLayout.createSequentialGroup()
                                        .addComponent(lbl_direccion)
                                        .addGap(375, 375, 375))
                                    .addComponent(cbb_rol_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lbl_cargo)
                                .addComponent(lbl_dni)
                                .addComponent(lbl_cargo1)))
                        .addGap(161, 161, 161))
                    .addGroup(pnl_empleadoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_empleadoLayout.createSequentialGroup()
                                .addComponent(btn_agregar_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btn_borrar_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(btn_modificar_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(btn_reporte_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_afiliacion_usuario3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnl_empleadoLayout.createSequentialGroup()
                                        .addComponent(Filtro_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_filtro_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnl_empleadoLayout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_empleadoLayout.setVerticalGroup(
            pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_empleadoLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_dni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_dni)
                    .addComponent(lbl_id_emp)
                    .addComponent(txt_id_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_direccion)
                    .addComponent(txt_nombre_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_nombre1))
                .addGap(18, 18, 18)
                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbb_cargo_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cargo)
                    .addComponent(lbl_apellido)
                    .addComponent(txt_apellido_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbb_rol_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cargo1)
                    .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_telefono1))
                .addGap(16, 16, 16)
                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_clave_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_clave)
                    .addComponent(txt_correo_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_email))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_borrar_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_agregar_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btn_reporte_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_modificar_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_empleadoLayout.createSequentialGroup()
                        .addComponent(lbl_afiliacion_usuario3)
                        .addGap(3, 3, 3)
                        .addGroup(pnl_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Filtro_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_filtro_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))))
        );

        lbl_empleado.setBackground(new java.awt.Color(255, 255, 255));
        lbl_empleado.setFont(new java.awt.Font("Corbel Light", 0, 48)); // NOI18N
        lbl_empleado.setForeground(new java.awt.Color(170, 0, 0));
        lbl_empleado.setText("Empleado");

        img_empleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/employee.png"))); // NOI18N

        javax.swing.GroupLayout t_empleadoLayout = new javax.swing.GroupLayout(t_empleado);
        t_empleado.setLayout(t_empleadoLayout);
        t_empleadoLayout.setHorizontalGroup(
            t_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_empleadoLayout.createSequentialGroup()
                .addGroup(t_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(t_empleadoLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(lbl_empleado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(img_empleado))
                    .addGroup(t_empleadoLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(pnl_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 254, Short.MAX_VALUE)))
                .addContainerGap())
        );
        t_empleadoLayout.setVerticalGroup(
            t_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_empleadoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(t_empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_empleado)
                    .addComponent(img_empleado))
                .addGap(12, 12, 12)
                .addComponent(pnl_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Empleado", t_empleado);

        t_cliente.setBackground(new java.awt.Color(255, 255, 255));
        t_cliente.setForeground(new java.awt.Color(255, 255, 255));
        t_cliente.setFont(new java.awt.Font("Tahoma", 2, 20)); // NOI18N

        pnl_usuario1.setBackground(new java.awt.Color(255, 255, 255));

        txt_dni_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_nom_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_correo_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_nom_com1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_nom_com1.setText("DNI");

        lbl_nom_usu1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_nom_usu1.setText("Nombre");

        lbl_correo_usuario2.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_correo_usuario2.setText("Correo");

        lbl_apellido_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_apellido_cliente.setText("Apellido");

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));

        tbl_cliente.setAutoCreateRowSorter(true);
        tbl_cliente.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        tbl_cliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_cliente.setRowHeight(24);
        tbl_cliente.setSelectionBackground(new java.awt.Color(255, 51, 51));
        tbl_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_clienteMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_cliente);

        btn_añadir_cliente.setBackground(new java.awt.Color(255, 255, 255));
        btn_añadir_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plus.png"))); // NOI18N
        btn_añadir_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_añadir_clienteActionPerformed(evt);
            }
        });

        btn_borrar_cliente.setBackground(new java.awt.Color(255, 255, 255));
        btn_borrar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/remove.png"))); // NOI18N
        btn_borrar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_borrar_clienteActionPerformed(evt);
            }
        });

        lbl_id_usuario1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_id_usuario1.setText("ID");

        btn_editar_cliente.setBackground(new java.awt.Color(255, 255, 255));
        btn_editar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btn_editar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_clienteActionPerformed(evt);
            }
        });

        txt_id_cliente.setEditable(false);
        txt_id_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_filtro_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_filtro_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_filtro_clienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filtro_clienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_filtro_clienteKeyTyped(evt);
            }
        });

        Filtro_cli.setBackground(new java.awt.Color(255, 204, 204));
        Filtro_cli.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        Filtro_cli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Nombre", "Apellido", "DNI" }));

        lbl_afiliacion_client.setFont(new java.awt.Font("Corbel Light", 0, 24)); // NOI18N
        lbl_afiliacion_client.setText("Filtrar por:");

        lbl_correo_usuario3.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_correo_usuario3.setText("Dirección");

        txt_direccion_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_apellido_cliente1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_apellido_cliente1.setText("Teléfono/celular");

        txt_telf_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_apellido_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        javax.swing.GroupLayout pnl_usuario1Layout = new javax.swing.GroupLayout(pnl_usuario1);
        pnl_usuario1.setLayout(pnl_usuario1Layout);
        pnl_usuario1Layout.setHorizontalGroup(
            pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_usuario1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_usuario1Layout.createSequentialGroup()
                        .addComponent(btn_añadir_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btn_borrar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btn_editar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_usuario1Layout.createSequentialGroup()
                                .addComponent(Filtro_cli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(txt_filtro_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_usuario1Layout.createSequentialGroup()
                                .addComponent(lbl_afiliacion_client, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))))
                    .addComponent(jScrollPane6))
                .addContainerGap())
            .addGroup(pnl_usuario1Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_apellido_cliente)
                    .addComponent(lbl_nom_usu1)
                    .addComponent(lbl_id_usuario1)
                    .addComponent(lbl_apellido_cliente1))
                .addGap(55, 55, 55)
                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_id_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nom_cliente)
                    .addComponent(txt_apellido_cliente)
                    .addComponent(txt_telf_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_usuario1Layout.createSequentialGroup()
                        .addComponent(lbl_correo_usuario3)
                        .addGap(24, 24, 24))
                    .addGroup(pnl_usuario1Layout.createSequentialGroup()
                        .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_nom_com1)
                            .addComponent(lbl_correo_usuario2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_direccion_cliente, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_correo_cliente)
                    .addComponent(txt_dni_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(162, 162, 162))
        );
        pnl_usuario1Layout.setVerticalGroup(
            pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_usuario1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_usuario1Layout.createSequentialGroup()
                        .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_usuario1Layout.createSequentialGroup()
                                .addComponent(txt_id_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(txt_nom_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(txt_apellido_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_usuario1Layout.createSequentialGroup()
                                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_nom_com1)
                                    .addComponent(txt_dni_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_correo_usuario3)
                                    .addComponent(txt_direccion_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_correo_usuario2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_correo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_telf_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_usuario1Layout.createSequentialGroup()
                        .addComponent(lbl_id_usuario1)
                        .addGap(19, 19, 19)
                        .addComponent(lbl_nom_usu1)
                        .addGap(19, 19, 19)
                        .addComponent(lbl_apellido_cliente)
                        .addGap(16, 16, 16)
                        .addComponent(lbl_apellido_cliente1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_editar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_borrar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_añadir_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_usuario1Layout.createSequentialGroup()
                        .addComponent(lbl_afiliacion_client)
                        .addGap(3, 3, 3)
                        .addGroup(pnl_usuario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Filtro_cli, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_filtro_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        lbl_usuario_titulo1.setFont(new java.awt.Font("Corbel Light", 0, 48)); // NOI18N
        lbl_usuario_titulo1.setForeground(new java.awt.Color(170, 0, 0));
        lbl_usuario_titulo1.setText("Clientes");

        img_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/target_cli.png"))); // NOI18N

        javax.swing.GroupLayout t_clienteLayout = new javax.swing.GroupLayout(t_cliente);
        t_cliente.setLayout(t_clienteLayout);
        t_clienteLayout.setHorizontalGroup(
            t_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_clienteLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(lbl_usuario_titulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(img_cliente)
                .addContainerGap())
            .addGroup(t_clienteLayout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(pnl_usuario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );
        t_clienteLayout.setVerticalGroup(
            t_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_clienteLayout.createSequentialGroup()
                .addGroup(t_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(t_clienteLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(img_cliente))
                    .addGroup(t_clienteLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lbl_usuario_titulo1)))
                .addGap(10, 10, 10)
                .addComponent(pnl_usuario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cliente", t_cliente);

        t_usuario.setBackground(new java.awt.Color(255, 255, 255));
        t_usuario.setForeground(new java.awt.Color(255, 255, 255));
        t_usuario.setFont(new java.awt.Font("Tahoma", 2, 20)); // NOI18N

        pnl_usuario.setBackground(new java.awt.Color(255, 255, 255));

        txt_nom_com.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_clave.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_nom_usu.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_rol_usuario.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_rol_usuario.setText("Rol");

        txt_correo.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_nom_com.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_nom_com.setText("Nombre");

        lbl_nom_usu.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_nom_usu.setText("Usuario");

        lbl_correo_usuario.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_correo_usuario.setText("Correo");

        lbl_clave_usuario.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_clave_usuario.setText("Clave");

        lbl_afiliacion_usuario.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_afiliacion_usuario.setText("Afiliación");

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        tbl_usuario.setAutoCreateRowSorter(true);
        tbl_usuario.setFont(new java.awt.Font("Corbel Light", 0, 24)); // NOI18N
        tbl_usuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_usuario.setRowHeight(24);
        tbl_usuario.setSelectionBackground(new java.awt.Color(255, 51, 51));
        tbl_usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_usuarioMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_usuario);

        btn_añadir_usuario.setBackground(new java.awt.Color(255, 255, 255));
        btn_añadir_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plus.png"))); // NOI18N
        btn_añadir_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_añadir_usuarioActionPerformed(evt);
            }
        });

        btn_borrar_usuario.setBackground(new java.awt.Color(255, 255, 255));
        btn_borrar_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/remove.png"))); // NOI18N
        btn_borrar_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_borrar_usuarioActionPerformed(evt);
            }
        });

        lbl_id_usuario.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_id_usuario.setText("ID");

        btn_editar_usuario.setBackground(new java.awt.Color(255, 255, 255));
        btn_editar_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btn_editar_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_usuarioActionPerformed(evt);
            }
        });

        txt_id_usu.setEditable(false);
        txt_id_usu.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_filtro_usu.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_filtro_usu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_filtro_usuKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filtro_usuKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_filtro_usuKeyTyped(evt);
            }
        });

        Filtro_usu.setBackground(new java.awt.Color(255, 204, 204));
        Filtro_usu.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        Filtro_usu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Nombre", "Correo" }));

        lbl_afiliacion_usuario1.setFont(new java.awt.Font("Corbel Light", 0, 22)); // NOI18N
        lbl_afiliacion_usuario1.setText("Filtrar por:");

        cbb_rol_cliente.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        cbb_afiliacion.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        cbb_afiliacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No especificado", "Por recomendación", "Voluntario" }));

        javax.swing.GroupLayout pnl_usuarioLayout = new javax.swing.GroupLayout(pnl_usuario);
        pnl_usuario.setLayout(pnl_usuarioLayout);
        pnl_usuarioLayout.setHorizontalGroup(
            pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_usuarioLayout.createSequentialGroup()
                .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_usuarioLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btn_añadir_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_borrar_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(btn_editar_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(805, 805, 805)
                        .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_usuarioLayout.createSequentialGroup()
                                .addComponent(Filtro_usu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_filtro_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_afiliacion_usuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_usuarioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3)))
                .addContainerGap())
            .addGroup(pnl_usuarioLayout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_id_usuario)
                    .addComponent(lbl_nom_usu)
                    .addComponent(lbl_clave_usuario)
                    .addComponent(lbl_rol_usuario))
                .addGap(18, 18, 18)
                .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_usuarioLayout.createSequentialGroup()
                        .addComponent(cbb_rol_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_usuarioLayout.createSequentialGroup()
                        .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_id_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nom_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_clave, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_afiliacion_usuario, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_correo_usuario)
                            .addComponent(lbl_nom_com))
                        .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_usuarioLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(txt_nom_com, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_usuarioLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbb_afiliacion, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(237, 237, 237))))
        );
        pnl_usuarioLayout.setVerticalGroup(
            pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_usuarioLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_usuarioLayout.createSequentialGroup()
                        .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnl_usuarioLayout.createSequentialGroup()
                                .addComponent(lbl_nom_usu)
                                .addGap(8, 8, 8))
                            .addGroup(pnl_usuarioLayout.createSequentialGroup()
                                .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_id_usu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_id_usuario))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_nom_usu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_correo_usuario)
                                    .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)))
                        .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_clave_usuario)
                            .addComponent(txt_clave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_afiliacion_usuario)
                            .addComponent(cbb_afiliacion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_rol_usuario)
                            .addComponent(cbb_rol_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_nom_com, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_nom_com)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_usuarioLayout.createSequentialGroup()
                        .addComponent(lbl_afiliacion_usuario1)
                        .addGap(1, 1, 1)
                        .addGroup(pnl_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_filtro_usu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Filtro_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_editar_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_añadir_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_borrar_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lbl_usuario_titulo.setFont(new java.awt.Font("Corbel Light", 0, 48)); // NOI18N
        lbl_usuario_titulo.setForeground(new java.awt.Color(170, 0, 0));
        lbl_usuario_titulo.setText("Usuario");

        img_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/people.png"))); // NOI18N

        javax.swing.GroupLayout t_usuarioLayout = new javax.swing.GroupLayout(t_usuario);
        t_usuario.setLayout(t_usuarioLayout);
        t_usuarioLayout.setHorizontalGroup(
            t_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_usuarioLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lbl_usuario_titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(img_usuario)
                .addGap(46, 46, 46))
            .addGroup(t_usuarioLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(pnl_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(162, Short.MAX_VALUE))
        );
        t_usuarioLayout.setVerticalGroup(
            t_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_usuarioLayout.createSequentialGroup()
                .addGroup(t_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(t_usuarioLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbl_usuario_titulo))
                    .addGroup(t_usuarioLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(img_usuario)))
                .addGap(8, 8, 8)
                .addComponent(pnl_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Usuario", t_usuario);

        t_proveedor.setBackground(new java.awt.Color(255, 255, 255));

        pnl_proveedor.setBackground(new java.awt.Color(255, 255, 255));

        txt_nom_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_nom_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_nom_proveedor.setText("Nombre");

        lbl_apellido_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_apellido_proveedor.setText("Apellido");

        jScrollPane7.setBackground(new java.awt.Color(255, 255, 255));

        tbl_proveedores.setAutoCreateRowSorter(true);
        tbl_proveedores.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        tbl_proveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_proveedores.setRowHeight(24);
        tbl_proveedores.setSelectionBackground(new java.awt.Color(255, 51, 51));
        tbl_proveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_proveedoresMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tbl_proveedores);

        btn_añadir_proveedor.setBackground(new java.awt.Color(255, 255, 255));
        btn_añadir_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plus.png"))); // NOI18N
        btn_añadir_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_añadir_proveedorActionPerformed(evt);
            }
        });

        btn_borrar_proveedor.setBackground(new java.awt.Color(255, 255, 255));
        btn_borrar_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/remove.png"))); // NOI18N
        btn_borrar_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_borrar_proveedorActionPerformed(evt);
            }
        });

        lbl_id_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_id_proveedor.setText("ID");

        btn_editar_proveedor.setBackground(new java.awt.Color(255, 255, 255));
        btn_editar_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btn_editar_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_proveedorActionPerformed(evt);
            }
        });

        txt_id_proveedor.setEditable(false);
        txt_id_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_filtro_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_filtro_proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_filtro_proveedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filtro_proveedorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_filtro_proveedorKeyTyped(evt);
            }
        });

        Filtro_prov.setBackground(new java.awt.Color(255, 204, 204));
        Filtro_prov.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        Filtro_prov.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Nombre", "Apellido", "Teléfono" }));

        lbl_filtro_prov.setFont(new java.awt.Font("Corbel Light", 0, 24)); // NOI18N
        lbl_filtro_prov.setText("Filtrar por:");

        lbl_direccion_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_direccion_proveedor.setText("Dirección");

        txt_apellido_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_tel_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_direccion_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_telefono_proveedor.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_telefono_proveedor.setText("Teléfono/celular");

        javax.swing.GroupLayout pnl_proveedorLayout = new javax.swing.GroupLayout(pnl_proveedor);
        pnl_proveedor.setLayout(pnl_proveedorLayout);
        pnl_proveedorLayout.setHorizontalGroup(
            pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_proveedorLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_proveedorLayout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_apellido_proveedor)
                            .addComponent(lbl_nom_proveedor)
                            .addComponent(lbl_id_proveedor))
                        .addGap(69, 69, 69)
                        .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_proveedorLayout.createSequentialGroup()
                                .addComponent(txt_apellido_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl_proveedorLayout.createSequentialGroup()
                                .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_id_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_nom_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                                .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnl_proveedorLayout.createSequentialGroup()
                                        .addComponent(lbl_direccion_proveedor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_direccion_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnl_proveedorLayout.createSequentialGroup()
                                        .addComponent(lbl_telefono_proveedor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_tel_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(231, 231, 231))))
                    .addGroup(pnl_proveedorLayout.createSequentialGroup()
                        .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7)
                            .addGroup(pnl_proveedorLayout.createSequentialGroup()
                                .addComponent(btn_añadir_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(btn_borrar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btn_editar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_filtro_prov, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_proveedorLayout.createSequentialGroup()
                                        .addComponent(Filtro_prov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_filtro_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        pnl_proveedorLayout.setVerticalGroup(
            pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_proveedorLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_id_proveedor)
                    .addComponent(txt_id_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_telefono_proveedor)
                    .addComponent(txt_tel_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nom_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_nom_proveedor)
                    .addComponent(lbl_direccion_proveedor)
                    .addComponent(txt_direccion_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_apellido_proveedor)
                    .addComponent(txt_apellido_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_editar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_borrar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_añadir_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_proveedorLayout.createSequentialGroup()
                        .addComponent(lbl_filtro_prov)
                        .addGap(3, 3, 3)
                        .addGroup(pnl_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Filtro_prov, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_filtro_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        lbl_proveedor_titulo.setFont(new java.awt.Font("Corbel Light", 0, 48)); // NOI18N
        lbl_proveedor_titulo.setForeground(new java.awt.Color(170, 0, 0));
        lbl_proveedor_titulo.setText("Proveedores");

        img_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delivery-man.png"))); // NOI18N

        javax.swing.GroupLayout t_proveedorLayout = new javax.swing.GroupLayout(t_proveedor);
        t_proveedor.setLayout(t_proveedorLayout);
        t_proveedorLayout.setHorizontalGroup(
            t_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_proveedorLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(lbl_proveedor_titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(img_proveedor)
                .addContainerGap())
            .addGroup(t_proveedorLayout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(pnl_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );
        t_proveedorLayout.setVerticalGroup(
            t_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_proveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(t_proveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_proveedor_titulo)
                    .addComponent(img_proveedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(pnl_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );

        jTabbedPane1.addTab("Proveedor", t_proveedor);

        t_almacen.setBackground(new java.awt.Color(255, 255, 255));

        lbl_productos.setFont(new java.awt.Font("Corbel Light", 0, 48)); // NOI18N
        lbl_productos.setForeground(new java.awt.Color(170, 0, 0));
        lbl_productos.setText("Productos");
        lbl_productos.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        img_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/open-box.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tbl_almacen.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        tbl_almacen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_almacen.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        tbl_almacen.setGridColor(new java.awt.Color(204, 204, 204));
        tbl_almacen.setRowHeight(24);
        tbl_almacen.setSelectionBackground(new java.awt.Color(255, 51, 51));
        tbl_almacen.getTableHeader().setReorderingAllowed(false);
        tbl_almacen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_almacenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_almacen);

        btn_añadir_prod.setBackground(new java.awt.Color(255, 255, 255));
        btn_añadir_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plus.png"))); // NOI18N
        btn_añadir_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_añadir_prodActionPerformed(evt);
            }
        });

        btn_borrar_prod.setBackground(new java.awt.Color(255, 255, 255));
        btn_borrar_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/remove.png"))); // NOI18N
        btn_borrar_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_borrar_prodActionPerformed(evt);
            }
        });

        btn_editar_prod.setBackground(new java.awt.Color(255, 255, 255));
        btn_editar_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btn_editar_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_prodActionPerformed(evt);
            }
        });

        lbl_id_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_id_prod.setText("ID");

        lbl_nom_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_nom_prod.setText("Nombre");

        lbl_pres_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_pres_prod.setText("Presentación");

        lbl_cant_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_cant_prod.setText("Cantidad");

        lbl_puntos_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_puntos_prod.setText("Puntos");

        lbl_precio_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_precio_prod.setText("Precio compra");

        lbl_categoria_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_categoria_prod.setText("Categoría");

        txt_id_prod.setEditable(false);
        txt_id_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_nom_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_pres_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_cantidad_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_precio_venta_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_puntos_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_afiliacion_usuario2.setFont(new java.awt.Font("Corbel Light", 0, 22)); // NOI18N
        lbl_afiliacion_usuario2.setText("Filtrar por:");

        Filtro_prod.setBackground(new java.awt.Color(255, 204, 204));
        Filtro_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        Filtro_prod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Nombre", "Stock" }));

        txt_filtro_prod.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_filtro_prod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_filtro_prodKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_filtro_prodKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_filtro_prodKeyTyped(evt);
            }
        });

        cbb_categoria_producto.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_precio_prod1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_precio_prod1.setText("Precio venta");

        txt_precio_compra.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_nom_prod)
                    .addComponent(lbl_cant_prod)
                    .addComponent(lbl_pres_prod)
                    .addComponent(lbl_id_prod))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_id_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nom_prod)
                    .addComponent(txt_pres_prod)
                    .addComponent(txt_cantidad_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_categoria_prod)
                            .addComponent(lbl_precio_prod)
                            .addComponent(lbl_puntos_prod))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbb_categoria_producto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_precio_compra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_puntos_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lbl_precio_prod1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_precio_venta_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(120, 120, 120))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_añadir_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btn_borrar_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(btn_editar_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(620, 620, 620)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_afiliacion_usuario2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(Filtro_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_filtro_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1361, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbb_categoria_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_categoria_prod))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_precio_prod)
                            .addComponent(txt_precio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_precio_prod1)
                            .addComponent(txt_precio_venta_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_puntos_prod)
                            .addComponent(txt_puntos_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_id_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_id_prod))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nom_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_nom_prod))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_pres_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_pres_prod))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_cantidad_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_cant_prod))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lbl_afiliacion_usuario2)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_filtro_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Filtro_prod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btn_añadir_prod, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                        .addComponent(btn_borrar_prod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_editar_prod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout t_almacenLayout = new javax.swing.GroupLayout(t_almacen);
        t_almacen.setLayout(t_almacenLayout);
        t_almacenLayout.setHorizontalGroup(
            t_almacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_almacenLayout.createSequentialGroup()
                .addGroup(t_almacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(t_almacenLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(lbl_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(t_almacenLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(img_producto)
                .addContainerGap(132, Short.MAX_VALUE))
        );
        t_almacenLayout.setVerticalGroup(
            t_almacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_almacenLayout.createSequentialGroup()
                .addGroup(t_almacenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(t_almacenLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lbl_productos)
                        .addGap(19, 19, 19)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(t_almacenLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(img_producto)))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Almacén", t_almacen);

        t_factura.setBackground(new java.awt.Color(255, 255, 255));
        t_factura.setFont(new java.awt.Font("Tahoma", 2, 20)); // NOI18N

        lbl_factura.setBackground(new java.awt.Color(170, 0, 0));
        lbl_factura.setFont(new java.awt.Font("Corbel Light", 0, 48)); // NOI18N
        lbl_factura.setForeground(new java.awt.Color(170, 0, 0));
        lbl_factura.setText("Venta");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Datos de factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(102, 102, 102))); // NOI18N

        lbl_cif.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_cif.setForeground(new java.awt.Color(51, 51, 51));
        lbl_cif.setText("Fact. No.");

        lbl_rnc.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_rnc.setForeground(new java.awt.Color(51, 51, 51));
        lbl_rnc.setText("NCF");

        txt_ncf_fact.setEditable(false);
        txt_ncf_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_id_factura.setEditable(false);
        txt_id_factura.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(lbl_cif)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lbl_rnc)
                        .addGap(57, 57, 57)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_ncf_fact)
                    .addComponent(txt_id_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_id_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cif, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ncf_fact, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_rnc))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Datos personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(102, 102, 102))); // NOI18N

        txt_nom_user_fact.setEditable(false);
        txt_nom_user_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_nom_user_fact.setText("Buscar cliente");
        txt_nom_user_fact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nom_user_factMouseClicked(evt);
            }
        });

        lbl_user_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_user_fact.setForeground(new java.awt.Color(51, 51, 51));
        lbl_user_fact.setText("Cliente");

        lbl_dni_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_dni_fact.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dni_fact.setText("DNI");

        txt_dni_usu_fact.setEditable(false);
        txt_dni_usu_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        txt_nom_emp_fact.setEditable(false);
        txt_nom_emp_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_emp_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_emp_fact.setForeground(new java.awt.Color(51, 51, 51));
        lbl_emp_fact.setText("Empleado");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_user_fact)
                    .addComponent(lbl_emp_fact)
                    .addComponent(lbl_dni_fact))
                .addGap(25, 25, 25)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_nom_emp_fact)
                    .addComponent(txt_nom_user_fact)
                    .addComponent(txt_dni_usu_fact, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(311, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_emp_fact)
                    .addComponent(txt_nom_emp_fact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nom_user_fact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_user_fact))
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_dni_usu_fact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_dni_fact))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        img_factura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N

        t_venta.setBackground(new java.awt.Color(255, 255, 255));
        t_venta.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Datos del producto\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(102, 102, 102))); // NOI18N

        btn_añadir_prod_fact.setBackground(new java.awt.Color(255, 255, 255));
        btn_añadir_prod_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        btn_añadir_prod_fact.setText("Añadir");
        btn_añadir_prod_fact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_añadir_prod_factActionPerformed(evt);
            }
        });

        lbl_producto_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_producto_fact.setForeground(new java.awt.Color(51, 51, 51));
        lbl_producto_fact.setText("Producto");

        lbl_cant.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_cant.setForeground(new java.awt.Color(51, 51, 51));
        lbl_cant.setText("Cantidad");

        txt_producto_fact.setEditable(false);
        txt_producto_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_precio.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_precio.setForeground(new java.awt.Color(51, 51, 51));
        lbl_precio.setText("Precio");

        txt_precio_prod_fact.setEditable(false);
        txt_precio_prod_fact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txt_precio_prod_fact.setFocusable(false);
        txt_precio_prod_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N

        lbl_id_prod_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        lbl_id_prod_fact.setForeground(new java.awt.Color(51, 51, 51));
        lbl_id_prod_fact.setText("Código");

        txt_id_prod_fact.setEditable(false);
        txt_id_prod_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_id_prod_fact.setText("Buscar producto");
        txt_id_prod_fact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_id_prod_factMouseClicked(evt);
            }
        });

        txt_producto_cant.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        txt_producto_cant.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        btn_eliminar_prod_fact.setBackground(new java.awt.Color(255, 255, 255));
        btn_eliminar_prod_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        btn_eliminar_prod_fact.setText("Eliminar");
        btn_eliminar_prod_fact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_prod_factActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout t_ventaLayout = new javax.swing.GroupLayout(t_venta);
        t_venta.setLayout(t_ventaLayout);
        t_ventaLayout.setHorizontalGroup(
            t_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, t_ventaLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(btn_añadir_prod_fact, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(btn_eliminar_prod_fact, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(t_ventaLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(t_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, t_ventaLayout.createSequentialGroup()
                        .addComponent(lbl_cant)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_producto_cant, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117))
                    .addGroup(t_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(t_ventaLayout.createSequentialGroup()
                            .addComponent(lbl_precio)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_precio_prod_fact, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(t_ventaLayout.createSequentialGroup()
                            .addComponent(lbl_producto_fact)
                            .addGap(43, 43, 43)
                            .addComponent(txt_producto_fact, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(t_ventaLayout.createSequentialGroup()
                            .addComponent(lbl_id_prod_fact)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_id_prod_fact, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        t_ventaLayout.setVerticalGroup(
            t_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_ventaLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(t_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_id_prod_fact)
                    .addComponent(txt_id_prod_fact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(t_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_producto_fact)
                    .addComponent(txt_producto_fact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(t_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_precio)
                    .addComponent(txt_precio_prod_fact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(t_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_producto_cant, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cant))
                .addGap(46, 46, 46)
                .addGroup(t_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_añadir_prod_fact)
                    .addComponent(btn_eliminar_prod_fact))
                .addGap(20, 20, 20))
        );

        tbl_detalle.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbl_detalle.setFont(new java.awt.Font("Corbel Light", 0, 22)); // NOI18N
        tbl_detalle.setForeground(new java.awt.Color(219, 35, 35));
        tbl_detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_detalle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_detalle.setGridColor(new java.awt.Color(255, 204, 204));
        tbl_detalle.setMinimumSize(new java.awt.Dimension(2, 2));
        tbl_detalle.setRowHeight(24);
        tbl_detalle.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jScrollPane2.setViewportView(tbl_detalle);

        btn_fin_fact.setBackground(new java.awt.Color(255, 255, 255));
        btn_fin_fact.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        btn_fin_fact.setText("Finalizar factura");
        btn_fin_fact.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_fin_fact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fin_factActionPerformed(evt);
            }
        });

        lbl_total.setFont(new java.awt.Font("Corbel Light", 1, 36)); // NOI18N
        lbl_total.setForeground(new java.awt.Color(51, 51, 51));
        lbl_total.setText("TOTAL");

        lbl_itbis.setFont(new java.awt.Font("Corbel Light", 0, 30)); // NOI18N
        lbl_itbis.setForeground(new java.awt.Color(51, 51, 51));
        lbl_itbis.setText("$0.00");

        lbl_total2.setFont(new java.awt.Font("Corbel Light", 0, 30)); // NOI18N
        lbl_total2.setForeground(new java.awt.Color(51, 51, 51));
        lbl_total2.setText("ITBIS");

        lbl_total_.setFont(new java.awt.Font("Corbel Light", 1, 36)); // NOI18N
        lbl_total_.setForeground(new java.awt.Color(51, 51, 51));
        lbl_total_.setText("$0.00");

        lbl_total4.setFont(new java.awt.Font("Corbel Light", 0, 30)); // NOI18N
        lbl_total4.setForeground(new java.awt.Color(51, 51, 51));
        lbl_total4.setText("SUBTOTAL");

        lbl_subtotal.setFont(new java.awt.Font("Corbel Light", 0, 30)); // NOI18N
        lbl_subtotal.setForeground(new java.awt.Color(51, 51, 51));
        lbl_subtotal.setText("$0.00");

        btn_reporte_empleado1.setBackground(new java.awt.Color(255, 255, 255));
        btn_reporte_empleado1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        btn_reporte_empleado1.setText("Generar reporte");
        btn_reporte_empleado1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_reporte_empleado1.setPreferredSize(new java.awt.Dimension(173, 39));
        btn_reporte_empleado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporte_empleado1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout t_facturaLayout = new javax.swing.GroupLayout(t_factura);
        t_factura.setLayout(t_facturaLayout);
        t_facturaLayout.setHorizontalGroup(
            t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_facturaLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(lbl_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(img_factura)
                .addGap(58, 58, 58))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, t_facturaLayout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(t_facturaLayout.createSequentialGroup()
                        .addComponent(btn_reporte_empleado1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_fin_fact, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(t_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(t_facturaLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(lbl_total4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_subtotal)
                            .addGap(25, 25, 25))
                        .addGroup(t_facturaLayout.createSequentialGroup()
                            .addComponent(lbl_total)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_total_))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, t_facturaLayout.createSequentialGroup()
                            .addComponent(lbl_total2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_itbis)
                            .addGap(28, 28, 28))))
                .addGap(122, 122, 122))
        );
        t_facturaLayout.setVerticalGroup(
            t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_facturaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(img_factura))
                .addGap(23, 23, 23)
                .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(t_facturaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(t_facturaLayout.createSequentialGroup()
                                .addComponent(lbl_total_, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(t_facturaLayout.createSequentialGroup()
                                .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_total4)
                                    .addComponent(lbl_subtotal))
                                .addGap(4, 4, 4)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_total2)
                                    .addComponent(lbl_itbis))
                                .addGap(7, 7, 7)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33))))
                    .addGroup(t_facturaLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(t_facturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_reporte_empleado1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_fin_fact, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(36, 36, 36))
        );

        jTabbedPane1.addTab("Venta", t_factura);

        t_reporte.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 255));

        tbl_factFiltro.setFont(new java.awt.Font("Corbel Light", 0, 22)); // NOI18N
        tbl_factFiltro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_factFiltro.setToolTipText("");
        tbl_factFiltro.setGridColor(new java.awt.Color(255, 204, 204));
        tbl_factFiltro.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jScrollPane8.setViewportView(tbl_factFiltro);

        rep_fecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("MM-yyyy"))));
        rep_fecha.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        rep_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rep_fechaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Corbel Light", 0, 28)); // NOI18N
        jLabel1.setText("Fecha");

        javax.swing.GroupLayout t_reporteLayout = new javax.swing.GroupLayout(t_reporte);
        t_reporte.setLayout(t_reporteLayout);
        t_reporteLayout.setHorizontalGroup(
            t_reporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_reporteLayout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addGroup(t_reporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(t_reporteLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(29, 29, 29)
                        .addComponent(rep_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1492, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        t_reporteLayout.setVerticalGroup(
            t_reporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(t_reporteLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(t_reporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rep_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(47, 47, 47)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reportes", t_reporte);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jTabbedPane1)
                .addGap(107, 107, 107))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Menu");

        btn_log_out.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/log-out.png"))); // NOI18N
        btn_log_out.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_log_outMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_log_out)
                    .addComponent(img_logo))
                .addGap(41, 41, 41)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(img_logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_log_out)
                .addGap(66, 66, 66))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Este botón elimina el empleado de la tabla de empleados y de la base de datos
    private void btn_borrar_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_borrar_empleadoActionPerformed
        // TODO add your handling code here:        
        String id = txt_id_emp.getText();

        PreparedStatement ps;
        String query = "DELETE FROM `empleado` WHERE id_empleado=?";
        try {
            ps = myConnection.getConnection().prepareStatement(query);

            ps.setString(1, id);

            if (ps.executeUpdate() > 0) {

                JOptionPane.showMessageDialog(null, "Empleado Eliminado");
                LimpiarCamposEmpleado();
                MostrarDatosEmpleado("", "");
            }

        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }

    }//GEN-LAST:event_btn_borrar_empleadoActionPerformed

    // Este boton agrega el empleado a la tabla y base de datos que se ha introducido en los campos
    private void btn_agregar_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_empleadoActionPerformed
        // TODO add your handling code here:
        ResultSet rs;

        String nom_emp = txt_nombre_emp.getText();
        String ape_emp = txt_apellido_emp.getText();
        String dni = txt_dni.getText();
        int cargo = cbb_cargo_empleado.getItemAt(cbb_cargo_empleado.getSelectedIndex()).getId_cargo();
        int id_rol = cbb_rol_empleado.getItemAt(cbb_rol_empleado.getSelectedIndex()).getId_rol();
        String tel = txt_telefono.getText();
        String dir = txt_direccion.getText();
        String clave_emp_txt = new String(txt_clave_emp.getPassword());
        String correo_emp = txt_correo_emp.getText();
        String clave_emp = md_hash.md5(clave_emp_txt);

        if (nom_emp.equals("") || ape_emp.equals("") || dni.equals("") || tel.equals("") || dir.equals("") || clave_emp_txt.equals("") || correo_emp.equals("")) {

            JOptionPane.showMessageDialog(null, "Error, verifique que ningún campo esté vacío.");

        } else {
            if (RevisarEmpleado(nom_emp, 0, ape_emp) == true) {
                JOptionPane.showMessageDialog(null, "El nombre del empleado: " + nom_emp + " Ya está registrado, use otro.");
            } else {

                if (modUsu.esEmail(correo_emp)) {
                    PreparedStatement ps;
                    String query = "INSERT INTO `empleado`(`nombre_empleado`,`apellido_empleado`,`dni`,`telefono`,`direccion_empleado`,`id_cargo`,`id_rol`,`correo_emp`,`clave_emp`) VALUES (?,?,?,?,?,?,?,?,?)";
                    try {

                        ps = myConnection.getConnection().prepareStatement(query);
                        ps.setString(1, nom_emp);
                        ps.setString(2, ape_emp);
                        ps.setString(3, dni);
                        ps.setString(4, tel);
                        ps.setString(5, dir);
                        ps.setInt(6, cargo);
                        ps.setInt(7, id_rol);
                        ps.setString(8, correo_emp);
                        ps.setString(9, clave_emp);
                        if (ps.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(null, "¡Empleado agregado exitosamente!");

                            LimpiarCamposEmpleado();
                            MostrarDatosEmpleado("", "");
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "error " + ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El correo electrónico no es válido.");
                }
            }
        }
    }//GEN-LAST:event_btn_agregar_empleadoActionPerformed

    public float suma() {
        float contar = tbl_detalle.getRowCount();
        float subtotal = 0;
        for (int i = 0; i < contar; i++) {
            subtotal = subtotal + Float.parseFloat(tbl_detalle.getValueAt(i, 3).toString());
        }
        return subtotal;
    }

    public float ITBIS() {
        float contar = tbl_detalle.getRowCount();
        float itbis = 0;
        for (int i = 0; i < contar; i++) {
            itbis = itbis + Float.parseFloat(tbl_detalle.getValueAt(i, 4).toString());
        }
        return itbis;
    }

    private float total() {
        float total = suma() + ITBIS();

        return total;
    }

    void GuardarDetalleFact() {

        for (int i = 0; i < tbl_detalle.getRowCount(); i++) {
            int id_fact = Integer.parseInt(tbl_detalle.getValueAt(i, 0).toString());
            int id_prod = Integer.parseInt(tbl_detalle.getValueAt(i, 1).toString());
            int cant_vendida = Integer.parseInt(tbl_detalle.getValueAt(i, 2).toString());
            float importe = Float.valueOf(tbl_detalle.getValueAt(i, 3).toString());
            float ITBIS = Float.valueOf(tbl_detalle.getValueAt(i, 4).toString());

        }

    }

    private void btn_añadir_prod_factActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_añadir_prod_factActionPerformed
         //Condicion para verificar si se selecciono un producto

        try {
            if (txt_id_prod_fact.getText().equals("Buscar producto")) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un producto.", "Campo de producto vacío", 2);
                //funcion para limpiar los campos de texto una vez ingresado los datos
                LimpiarCamposFactura();

            } else if (txt_nom_emp_fact.getText().equals("Buscar empleado")) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado.", "Campo de empleado vacío", 2);
                LimpiarCamposFactura();
            } else if (txt_nom_user_fact.getText().equals("Buscar cliente")) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un cliente.", "Campo de cliente vacío", 2);
                LimpiarCamposFactura();

            } else if (Integer.parseInt(String.valueOf(txt_producto_cant.getValue())) > 100 || Integer.parseInt(String.valueOf(txt_producto_cant.getValue())) < 1) {

                JOptionPane.showMessageDialog(null, "La cantidad del producto supera el limite establecido por cada venta.", "La cantidad a vender es errónea", 2);

            } else if (RevisarStockDisponible(Integer.parseInt(txt_id_prod_fact.getText()), (int) txt_producto_cant.getValue()) == true) {

                Float importe = Float.parseFloat(txt_precio_prod_fact.getText()) * Float.parseFloat(String.valueOf(txt_producto_cant.getValue()));
                Float ITBIS = 0.18f * importe;
                DecimalFormat formato_decimal = new DecimalFormat("#.00");

                detalle[0] = txt_id_factura.getText();
                detalle[1] = txt_id_prod_fact.getText();
                detalle[2] = txt_producto_cant.getValue();
                detalle[3] = formato_decimal.format(importe);
                detalle[4] = formato_decimal.format(ITBIS);

                //funcion para tomar el array con los datos e insertarlo en la tabla temporal
                Factura.addRow(detalle);

                lbl_subtotal.setText(String.valueOf("$" + suma()));
                lbl_itbis.setText(String.valueOf("$" + ITBIS()));
                lbl_total_.setText(String.valueOf("$" + formato_decimal.format(total())));

                //Si se almaceno todo correctamente, con un ciclo se limpia el array
                for (int i = 0; i < detalle.length; i++) {
                    detalle[i] = "";
                }
                LimpiarDatosProdFact();

            } else {

                JOptionPane.showMessageDialog(null, "La cantidad a facturar supera el stock disponible.", "Agregue más stock al producto #" + txt_id_prod_fact.getText(), 2);

            }

        } catch (HeadlessException | NumberFormatException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_btn_añadir_prod_factActionPerformed
    
    public boolean RevisarStockDisponible(int id_prod, int cantidad) {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkStock = false;
        String query = "SELECT cant_disponible FROM producto WHERE cant_disponible > ? AND id_producto = ?";

        try {
            ps = myConnection.getConnection().prepareStatement(query);
            ps.setInt(1, cantidad);
            ps.setInt(2, id_prod);
            rs = ps.executeQuery();
            if (rs.next()) {
                checkStock = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return checkStock;
    }

    public boolean RevisarUsuario(String usuario, int id_usu) {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM usuario WHERE nombre_usuario = ? AND id_usuario <> ?";

        try {
            ps = myConnection.getConnection().prepareStatement(query);
            ps.setString(1, usuario);
            ps.setInt(2, id_usu);
            rs = ps.executeQuery();
            if (rs.next()) {
                checkUser = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return checkUser;
    }

    public boolean RevisarProveedor(String nombre, String apellido, int id_prov) {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkProv = false;
        String query = "SELECT * FROM proveedor WHERE nombre_proveedor = ? AND apellido_proveedor = ? AND id_proveedor <> ?";

        try {
            ps = myConnection.getConnection().prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, id_prov);
            rs = ps.executeQuery();
            if (rs.next()) {
                checkProv = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return checkProv;
    }
    
    public boolean RevisarEmpleado(String empleado, int id_emp, String apellido) {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkEmpleado = false;
        String query = "SELECT * FROM empleado WHERE nombre_empleado = ? AND id_empleado <> ? AND apellido_empleado = ?";

        try {
            ps = myConnection.getConnection().prepareStatement(query);
            ps.setString(1, empleado);
            ps.setInt(2, id_emp);
            ps.setString(3, apellido);
            rs = ps.executeQuery();
            if (rs.next()) {
                checkEmpleado = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return checkEmpleado;
    }
    
    public boolean RevisarCliente(String cliente, int id_cli, String apellido_cliente) {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkCliente = false;
        String query = "SELECT count(id_cliente) FROM cliente WHERE nombre_cliente = ? AND id_cliente <> ? AND apellido_cliente = ? ";

        try {
            ps = myConnection.getConnection().prepareStatement(query);
            ps.setString(1, cliente);
            ps.setInt(2, id_cli);
            ps.setString(3, apellido_cliente);
            rs = ps.executeQuery();
            if (rs.next()) {
                checkCliente = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return checkCliente;
    }

    public boolean RevisarProducto(String producto, int Id_p) {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkProducto = false;
        String query = "SELECT * FROM producto WHERE nombre_producto = ? AND id_producto <> ?";

        try {
            ps = myConnection.getConnection().prepareStatement(query);
            ps.setString(1, producto);
            ps.setInt(2, Id_p);
            rs = ps.executeQuery();
            if (rs.next()) {
                checkProducto = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return checkProducto;
    }
    
    private void btn_borrar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_borrar_prodActionPerformed
        // TODO add your handling code here:
        String id = txt_id_prod.getText();

        PreparedStatement ps;

        String query = "DELETE FROM `producto` WHERE id_producto=?";
        try {
            ps = myConnection.getConnection().prepareStatement(query);

            ps.setString(1, id);

            if (ps.executeUpdate() > 0) {

                JOptionPane.showMessageDialog(null, "Producto Eliminado");
                LimpiarCamposAlmacen();
                MostrarDatosAlmacen("", "");
            } else {
                JOptionPane.showMessageDialog(null, "Error, por favor verifique que el producto exista o haya digitado un codigo existente");
            }

        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }


    }//GEN-LAST:event_btn_borrar_prodActionPerformed

//Este boton hace un UPDATE en el empleado seleccionado con los cambios hechos
    private void btn_modificar_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificar_empleadoActionPerformed

        String id = txt_id_emp.getText();
        String nom_emp = txt_nombre_emp.getText();
        String ape_emp = txt_apellido_emp.getText();
        String dni = txt_dni.getText();
        int rol = cbb_rol_empleado.getItemAt(cbb_rol_empleado.getSelectedIndex()).getId_rol();
        int id_cargo = cbb_cargo_empleado.getItemAt(cbb_cargo_empleado.getSelectedIndex()).getId_cargo();
        String tel = txt_telefono.getText();
        String dir = txt_direccion.getText();
        String correo_emp = txt_correo_emp.getText();
        String clave_txt = new String(txt_clave_emp.getPassword());
        String clave;
        if (clave_txt.equals("")) {
            clave = Empleado.getValueAt(tbl_empleado.getSelectedRow(), 9).toString();
        } else {
            clave = md_hash.md5(clave_txt);
        }

        if (id.isEmpty() || nom_emp.isEmpty() || ape_emp.isEmpty() || dni.isEmpty() || tel.isEmpty() || dir.isEmpty() || correo_emp.equals("")) {
            JOptionPane.showMessageDialog(null, "Por favor, verifique que no deje nigun dato vacío.");

        } else {
            int id_em = Integer.parseInt(id);
            if (RevisarEmpleado(nom_emp, id_em,ape_emp ) == true) {
                JOptionPane.showMessageDialog(null, "El nombre del empleado: " + nom_emp + " Ya está registrado, use otro.");
            } else {

                if (modUsu.esEmail(correo_emp)) {

                    PreparedStatement ps;
                    String query = "UPDATE empleado SET nombre_empleado=?, apellido_empleado=?, dni=?, telefono=?, direccion_empleado=?, id_cargo=?, id_rol=?, correo_emp=?, clave_emp=? WHERE id_empleado=?";
                    try {
                        ps = myConnection.getConnection().prepareStatement(query);

                        ps.setString(10, id);
                        ps.setString(1, nom_emp);
                        ps.setString(2, ape_emp);
                        ps.setString(3, dni);
                        ps.setString(4, tel);
                        ps.setString(5, dir);
                        ps.setInt(6, id_cargo);
                        ps.setInt(7, rol);
                        ps.setString(8, correo_emp);
                        ps.setString(9, clave);

                        ps.executeUpdate();
                        MostrarDatosEmpleado("", "");
                        LimpiarCamposEmpleado();
                        JOptionPane.showMessageDialog(null, "Datos del empleado: " + nom_emp + " modificado");
                    } catch (SQLException ex) {
                        Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "error " + ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Error, el correo no es válido.");
                }
            }
        }
    }//GEN-LAST:event_btn_modificar_empleadoActionPerformed
//Abre una ventana con el reporte de los empleados
    private void btn_reporte_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporte_empleadoActionPerformed

        Connection cn = myConnection.getConnection();
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(frm_menu.class.getResource("/reports/rep_empleado.jasper"));
            Map parametros = new HashMap<>();
            parametros.put("Titulo", "Reporte Empleados");
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, cn);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_btn_reporte_empleadoActionPerformed
// Obtiene los datos de la columna seleccionada en la tabla de empleados y los coloca en los campos de texto correspondientes
    private void tbl_usuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_usuarioMouseClicked
        // TODO add your handling code here:
        try {
            String id = (String) Usuario.getValueAt(tbl_usuario.getSelectedRow(), 0);
            String nom_com = (String) Usuario.getValueAt(tbl_usuario.getSelectedRow(), 1);
            String usuario = (String) Usuario.getValueAt(tbl_usuario.getSelectedRow(), 2);
            String correo = (String) Usuario.getValueAt(tbl_usuario.getSelectedRow(), 3);

            String afiliacion = (Usuario.getValueAt(tbl_usuario.getSelectedRow(), 5).toString());
            int afiliacion_c = 0;
            switch (afiliacion) {
                case "No especificado":
                    afiliacion_c = 0;
                    break;
                case "Por recomendación":
                    afiliacion_c = 1;
                    break;
                case "Voluntario":
                    afiliacion_c = 2;
                    break;
                default:
                    break;
            }
            int rol = Integer.parseInt(Usuario.getValueAt(tbl_usuario.getSelectedRow(), 6).toString());
            txt_id_usu.setText(id);
            txt_nom_usu.setText(usuario);
            txt_nom_com.setText(nom_com);
            txt_correo.setText(correo);
            cbb_afiliacion.setSelectedIndex(afiliacion_c);
            cbb_rol_cliente.setSelectedItem(new c_rol(rol));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }//GEN-LAST:event_tbl_usuarioMouseClicked

    // Este boton agrega el usuario a la tabla y base de datos que se ha introducido en los campos
    private void btn_añadir_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_añadir_usuarioActionPerformed
        // TODO add your handling code here:
        ResultSet rs;
        int id_usu = Integer.parseInt(txt_id_usu.getText());
        String nom_com = txt_nom_com.getText();
        String nom_usu = txt_nom_usu.getText();
        String correo = txt_correo.getText();
        String clave_txt = String.valueOf(txt_clave.getPassword());
        String clave = md_hash.md5(clave_txt);
        int afiliacion = cbb_afiliacion.getSelectedIndex();
        int rol = cbb_rol_cliente.getItemAt(cbb_rol_cliente.getSelectedIndex()).getId_rol();

        if (nom_com.equals("") || nom_usu.equals("") || correo.equals("") || clave_txt.equals("")) {

            JOptionPane.showMessageDialog(this, "Error, verifique que todos los campos estén llenos");

        } else {
            if (modUsu.esEmail(correo)) {
                if (RevisarUsuario(nom_usu, id_usu) == true) {

                    JOptionPane.showMessageDialog(this, "Error, el usuario " + nom_usu + " está registrado, use otro.");

                } else {
                    String txt_af = "";
                    PreparedStatement ps;
                    String query = "INSERT INTO `usuario`(`nombre_completo`,`nombre_usuario`,`correo_electronico`,`clave`,`afiliacion`,`id_rol`) VALUES (?,?,?,?,?,?)";
                    try {

                        if (afiliacion == 0) {
                            txt_af = "Por recomendación";
                        } else if (afiliacion == 1) {
                            txt_af = "Voluntario";

                        }
                        ps = myConnection.getConnection().prepareStatement(query);

                        ps.setString(1, nom_com);
                        ps.setString(2, nom_usu);
                        ps.setString(3, correo);
                        ps.setString(4, clave);
                        ps.setString(5, txt_af);
                        ps.setInt(6, rol);

                        if (ps.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(this, "Usuario: " + nom_com + ". Registrado exitosamente. ");
                            LimpiarCamposUsuario();
                            MostrarDatosUsuario("", "");
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "error " + ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error, el correo electrónico no es válido");
            }
        }
    }//GEN-LAST:event_btn_añadir_usuarioActionPerformed

    //Este boton hace un UPDATE en el usuario seleccionado con los cambios hechos
    private void btn_editar_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_usuarioActionPerformed
        // TODO add your handling code here:

        String id = txt_id_usu.getText();
        String nom_com = txt_nom_com.getText();
        String nom_usu = txt_nom_usu.getText();
        String clave_txt = String.valueOf(txt_clave.getPassword());
        int rol = cbb_rol_cliente.getItemAt(cbb_rol_cliente.getSelectedIndex()).getId_rol();
        String cla;
        if (clave_txt.equals("")) {
            cla = (String) Usuario.getValueAt(tbl_usuario.getSelectedRow(), 4);
        } else {
            cla = md_hash.md5(clave_txt);
        }
        String correo = txt_correo.getText();
        String afiliacion = (String) cbb_afiliacion.getSelectedItem();

        if (id.isEmpty() || nom_com.isEmpty() || nom_usu.isEmpty() || correo.isEmpty() || afiliacion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error, verifique que ningún campo esté vacío.");
        } else {
            int id_ = Integer.parseInt(id);
            if (RevisarUsuario(nom_usu, id_) == true) {
                JOptionPane.showMessageDialog(null, "El nombre de usuario: " + nom_usu + ". está registrado, use otro diferente.");
            } else {

                PreparedStatement ps;
                String query = "UPDATE usuario SET nombre_completo=?, nombre_usuario=?, correo_electronico=?, clave=?, afiliacion=?, id_rol=? WHERE id_usuario=?";
                try {
                    ps = myConnection.getConnection().prepareStatement(query);

                    ps.setString(7, id);
                    ps.setString(1, nom_com);
                    ps.setString(2, nom_usu);
                    ps.setString(3, correo);
                    ps.setString(4, cla);
                    ps.setString(5, afiliacion);
                    ps.setInt(6, rol);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Usuario: " + nom_com + ". modificado");
                    LimpiarCamposUsuario();
                    MostrarDatosUsuario("", "");

                } catch (SQLException ex) {
                    Logger.getLogger(frm_cliente.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "error " + ex);
                }
            }
        }
    }//GEN-LAST:event_btn_editar_usuarioActionPerformed
    //Este boton selecciona los datos de la columna seleccionada de la tabla y los coloca en sus respectivos campos
    private void tbl_almacenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_almacenMouseClicked
        // TODO add your handling code here:
        try {
            String id = (String) Almacen.getValueAt(tbl_almacen.getSelectedRow(), 0);
            String nom_prod = (String) Almacen.getValueAt(tbl_almacen.getSelectedRow(), 1);
            String pres = (String) Almacen.getValueAt(tbl_almacen.getSelectedRow(), 2);
            String cant = (String) Almacen.getValueAt(tbl_almacen.getSelectedRow(), 3);
            int cat = Integer.parseInt(Almacen.getValueAt(tbl_almacen.getSelectedRow(), 4).toString());
            String pre = (String) Almacen.getValueAt(tbl_almacen.getSelectedRow(), 5);
            String pun = (String) Almacen.getValueAt(tbl_almacen.getSelectedRow(), 6);
            txt_id_prod.setText(id);
            txt_nom_prod.setText(nom_prod);
            txt_pres_prod.setText(pres);
            txt_cantidad_prod.setText(cant);
            cbb_categoria_producto.setSelectedItem(new c_categoria(cat));
            txt_precio_venta_prod.setText(pre);
            txt_puntos_prod.setText(pun);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }//GEN-LAST:event_tbl_almacenMouseClicked

//Este boton selecciona los datos de la columna seleccionada de la tabla y los coloca en sus respectivos campos
    private void tbl_empleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_empleadoMouseClicked
        // TODO add your handling code here:
        try {
            String id = (String) Empleado.getValueAt(tbl_empleado.getSelectedRow(), 0);
            String nom_emp = (String) Empleado.getValueAt(tbl_empleado.getSelectedRow(), 1);
            String ape_emp = (String) Empleado.getValueAt(tbl_empleado.getSelectedRow(), 2);
            String dni = (String) Empleado.getValueAt(tbl_empleado.getSelectedRow(), 3);
            String tel = (String) Empleado.getValueAt(tbl_empleado.getSelectedRow(), 4);
            String dir = (String) Empleado.getValueAt(tbl_empleado.getSelectedRow(), 5);
            int cargo = Integer.parseInt(Empleado.getValueAt(tbl_empleado.getSelectedRow(), 6).toString());
            int rol = Integer.parseInt(Empleado.getValueAt(tbl_empleado.getSelectedRow(), 7).toString());
            String correo_empl = Empleado.getValueAt(tbl_empleado.getSelectedRow(), 8).toString();
            txt_id_emp.setText(id);
            txt_nombre_emp.setText(nom_emp);
            txt_apellido_emp.setText(ape_emp);
            txt_dni.setText(dni);
            txt_telefono.setText(tel);
            txt_direccion.setText(dir);
            cbb_cargo_empleado.setSelectedItem(new c_cargo(cargo));
            cbb_rol_empleado.setSelectedItem(new c_rol(rol));
            txt_correo_emp.setText(correo_empl);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }//GEN-LAST:event_tbl_empleadoMouseClicked

    //Este boton añade el producto a la base de datos y actualiza la tabla
    private void btn_añadir_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_añadir_prodActionPerformed
        // TODO add your handling code here:
        ResultSet rs;
        String id = txt_id_prod.getText();
        String nom = txt_nom_prod.getText();
        String pres = txt_pres_prod.getText();
        String cant = txt_cantidad_prod.getText();
        int cat = cbb_categoria_producto.getItemAt(cbb_categoria_producto.getSelectedIndex()).getId_categoria();
        String pre = txt_precio_venta_prod.getText();
        String punt = txt_puntos_prod.getText();

        if ( nom.isEmpty() || pres.isEmpty() || cant.isEmpty() || pre.isEmpty() || punt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, no deje ningun dato vacío");

        } else {
            if (RevisarProducto(nom, 0) == true) {
                JOptionPane.showMessageDialog(this, "El nombre del producto " + nom + " ya existe. Use otro.");
            } else {
                PreparedStatement ps;
                String query = "INSERT INTO `producto`(nombre_producto, presentacion, cant_disponible, id_categoria, unidad_precio, puntos_producto) VALUES (?,?,?,?,?,?)";
                try {

                    ps = myConnection.getConnection().prepareStatement(query);

                    ps.setString(1, nom);
                    ps.setString(2, pres);
                    ps.setString(3, cant);
                    ps.setInt(4, cat);
                    ps.setString(5, pre);
                    ps.setString(6, punt);

                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(this, "Producto " + nom + " añadido correctamente");
                        LimpiarCamposUsuario();
                        MostrarDatosAlmacen("", "");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "error " + ex);
                }
            }
        }
    }//GEN-LAST:event_btn_añadir_prodActionPerformed
// Este boton hace un UPDATE en el producto seleccionado con los cambios hechos
    private void btn_editar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_prodActionPerformed
        // TODO add your handling code here:
        String id = txt_id_prod.getText();
        String nom = txt_nom_prod.getText();
        String pres = txt_pres_prod.getText();
        String cant = txt_cantidad_prod.getText();
        int categoria = cbb_categoria_producto.getItemAt(cbb_categoria_producto.getSelectedIndex()).getId_categoria();
        String pre = txt_precio_venta_prod.getText();
        String punt = txt_puntos_prod.getText();

        if (id.isEmpty() || nom.isEmpty() || pres.isEmpty() || cant.isEmpty() || pre.isEmpty() || punt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, no deje ningun dato vacío");
        } else {
            int id_p = Integer.parseInt(id);
            if (RevisarProducto(nom, id_p) == true) {
                JOptionPane.showMessageDialog(this, "El nombre del producto " + nom + " ya existe. Use otro.");
                LimpiarCamposAlmacen();
            } else {
                PreparedStatement ps;
                String query = "UPDATE `producto` SET nombre_producto=?, presentacion=?, cant_disponible=?, id_categoria=?, unidad_precio=?, puntos_producto=? WHERE id_producto=?";
                try {
                    ps = myConnection.getConnection().prepareStatement(query);

                    ps.setString(7, id);
                    ps.setString(1, nom);
                    ps.setString(2, pres);
                    ps.setString(3, cant);
                    ps.setInt(4, categoria);
                    ps.setString(5, pre);
                    ps.setString(6, punt);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Producto: " + nom+". Modificado correctamente");
                    LimpiarCamposAlmacen();
                    MostrarDatosAlmacen("", "");

                } catch (SQLException ex) {
                    Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "error " + ex);
                }
            }
        }
    }//GEN-LAST:event_btn_editar_prodActionPerformed

// Cierra el formulario devolviendo a la pantalla de inicio de sesion, a modo de cerrado de cuenta
    private void btn_log_outMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_log_outMouseClicked
        // TODO add your handling code here:
        frm_main nf = new frm_main();
        nf.setVisible(true);
        nf.pack();
        nf.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btn_log_outMouseClicked



    private void txt_nom_user_factMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nom_user_factMouseClicked
       
         if(cli == null){
        cli = new frm_cliente();
        cli.setVisible(true);
        cli.setLocationRelativeTo(null); 
        }else {
          cli.setAutoRequestFocus(true);
          cli.setVisible(true);
          cli.setLocationRelativeTo(null); 
        }
    }//GEN-LAST:event_txt_nom_user_factMouseClicked

    private void txt_id_prod_factMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_id_prod_factMouseClicked
       
        if(prod == null){
        prod = new frm_producto();
        prod.setVisible(true);
        prod.setLocationRelativeTo(null); 
        }else {
          prod.setAutoRequestFocus(true);
          prod.setVisible(true);
          prod.setLocationRelativeTo(null); 
        }
        
    }//GEN-LAST:event_txt_id_prod_factMouseClicked


    private void btn_eliminar_prod_factActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_prod_factActionPerformed
        if (tbl_detalle.getSelectedRow() >= 0) {
            Factura.removeRow(tbl_detalle.getSelectedRow());
            DecimalFormat formato_decimal = new DecimalFormat("#.00");
            lbl_subtotal.setText(String.valueOf("$" + suma()));
            lbl_itbis.setText(String.valueOf("$" + ITBIS()));
            lbl_total_.setText(String.valueOf("$" + total()));
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un registro para borrar.");
        }
    }//GEN-LAST:event_btn_eliminar_prod_factActionPerformed

    private void btn_fin_factActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fin_factActionPerformed

        if (tbl_detalle.getRowCount() <= 0) {

            JOptionPane.showMessageDialog(null, "No hay productos que facturar. Facture más productos.");

        } else {
            guardarFactura();
            guardarDetalleFactura();
            actualizarStock();
            generarNCF_Fact();
            SetNCF_ID();
            generarIdFact();
            SetFacturaID();
            LimpiarCamposFactura();
            RefrescarTablaFactura();
            LimpiarCamposPersonalesFactura();
            for (int i = 0; i < detalle.length; i++) {
                detalle[i] = "";
            }
            MostrarTablaRepFactura("");
            MostrarDatosFactura("");
            
            JOptionPane.showMessageDialog(null, "¡Factura creada con éxito!");
        }

    }//GEN-LAST:event_btn_fin_factActionPerformed


    private void btn_reporte_empleado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporte_empleado1ActionPerformed
        // TODO add your handling code here:
        Connection cn = myConnection.getConnection();
        try {
            int txtFac;
            JasperReport jr = (JasperReport) JRLoader.loadObject(frm_menu.class.getResource("/reports/rep_factura.jasper"));
            Map parametros = new HashMap<>();
            parametros.put("id_fact", txtFac = Integer.parseInt(txt_id_factura.getText()) - 1);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, cn);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_btn_reporte_empleado1ActionPerformed

    private void txt_filtro_usuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_usuKeyPressed
        try {

            String B = txt_filtro_usu.getText();
            int A = Filtro_usu.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosUsuario(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }


    }//GEN-LAST:event_txt_filtro_usuKeyPressed

    private void txt_filtro_usuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_usuKeyReleased
        try {

            String B = txt_filtro_usu.getText();
            int A = Filtro_usu.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosUsuario(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_usuKeyReleased

    private void txt_filtro_usuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_usuKeyTyped
        try {

            String B = txt_filtro_usu.getText();
            int A = Filtro_usu.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosUsuario(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_usuKeyTyped

    private void txt_filtro_prodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_prodKeyPressed
        try {

            String B = txt_filtro_prod.getText();
            int A = Filtro_prod.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosAlmacen(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_prodKeyPressed

    private void txt_filtro_prodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_prodKeyReleased
        try {

            String B = txt_filtro_prod.getText();
            int A = Filtro_prod.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosAlmacen(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_prodKeyReleased

    private void txt_filtro_prodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_prodKeyTyped
        try {

            String B = txt_filtro_prod.getText();
            int A = Filtro_prod.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosAlmacen(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_prodKeyTyped

    private void txt_filtro_empKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_empKeyPressed
        try {

            String B = txt_filtro_emp.getText();
            int A = Filtro_emp.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosEmpleado(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_empKeyPressed

    private void txt_filtro_empKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_empKeyReleased
        try {

            String B = txt_filtro_emp.getText();
            int A = Filtro_emp.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosEmpleado(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_empKeyReleased

    private void txt_filtro_empKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_empKeyTyped
        try {

            String B = txt_filtro_emp.getText();
            int A = Filtro_emp.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosEmpleado(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_empKeyTyped

    private void btn_borrar_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_borrar_usuarioActionPerformed

        String id = txt_id_usu.getText();

        PreparedStatement ps;
        String query = "DELETE FROM `usuario` WHERE id_usuario=?";
        try {
            ps = myConnection.getConnection().prepareStatement(query);

            ps.setString(1, id);

            if (ps.executeUpdate() > 0) {

                JOptionPane.showMessageDialog(null, "Usuario Eliminado");
                LimpiarCamposUsuario();
                MostrarDatosUsuario("", "");
            }

        } catch (SQLException ex) {
            Logger.getLogger(frm_cliente.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }

    }//GEN-LAST:event_btn_borrar_usuarioActionPerformed

    private void tbl_repFacturasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_repFacturasMouseClicked
        // TODO add your handling code here:
        
        try {
        String id = (String) RepFacturas.getValueAt(tbl_repFacturas.getSelectedRow(), 0);
        } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }//GEN-LAST:event_tbl_repFacturasMouseClicked

    private void btn_rep_factsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rep_factsActionPerformed
        // TODO add your handling code here:
        if(tbl_repFacturas.getSelectedRow() >= 0){
        Connection cn=myConnection.getConnection();
        try{
          JasperReport jr = (JasperReport) JRLoader.loadObject(frm_menu.class.getResource("/reports/rep_factura.jasper"));
          Map parametros = new HashMap<>();
          parametros.put("id_fact", Integer.parseInt(tbl_repFacturas.getValueAt(tbl_repFacturas.getSelectedRow(), 0).toString()) );
          JasperPrint jp = JasperFillManager.fillReport(jr, parametros, cn);
          JasperViewer jv = new JasperViewer(jp, false);
          jv.setVisible(true); 
        }
        catch (JRException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }}else{
          JOptionPane.showMessageDialog(null, "Seleccione una factura","No seleccionaste ninguna factura",1);
        }
    }//GEN-LAST:event_btn_rep_factsActionPerformed

    private void tbl_clienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_clienteMouseClicked
        try {
            String id = (String) Cliente.getValueAt(tbl_cliente.getSelectedRow(), 0);
            String nom_cli = (String) Cliente.getValueAt(tbl_cliente.getSelectedRow(), 1);
            String ape_cli = (String) Cliente.getValueAt(tbl_cliente.getSelectedRow(), 2);
            String telefono_cli = (String) Cliente.getValueAt(tbl_cliente.getSelectedRow(), 3);
            String correo_cli = (String) Cliente.getValueAt(tbl_cliente.getSelectedRow(), 4);
            String dni_cli = (String) Cliente.getValueAt(tbl_cliente.getSelectedRow(), 5);
            String dir_cli = (String) Cliente.getValueAt(tbl_cliente.getSelectedRow(), 6);
         
            txt_id_cliente.setText(id);
            txt_nom_cliente.setText(nom_cli);
            txt_apellido_cliente.setText(ape_cli);
            txt_dni_cliente.setText(dni_cli);
            txt_correo_cliente.setText(correo_cli);
            txt_telf_cliente.setText(telefono_cli);
            txt_direccion_cliente.setText(dir_cli);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }//GEN-LAST:event_tbl_clienteMouseClicked

    private void btn_añadir_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_añadir_clienteActionPerformed
 // TODO add your handling code here:
        ResultSet rs;

        String nom_cli = txt_nom_cliente.getText();
        String ape_cli = txt_apellido_cliente.getText();
        String dni_cli = txt_dni_cliente.getText();
        String tel_cli = txt_telf_cliente.getText();
        String dir_cli = txt_direccion_cliente.getText();
        String correo_cli = txt_correo_cliente.getText();
     

        if (nom_cli.equals("") || ape_cli.equals("") || dni_cli.equals("") || tel_cli.equals("") || dir_cli.equals("") || correo_cli.equals("")) {

            JOptionPane.showMessageDialog(null, "Error, verifique que ningún campo esté vacío.");

        } else {
            if (RevisarCliente(nom_cli, 0,ape_cli ) == true) {
                JOptionPane.showMessageDialog(null, "El nombre del cliente: " + nom_cli + " Ya está registrado, use otro.");
            } else {

                if (modUsu.esEmail(correo_cli)) {
                    PreparedStatement ps;
                    String query = "INSERT INTO `cliente`(`nombre_cliente`,`apellido_cliente`,`telefono_cliente`,`correo_cliente`,`dni_cliente`,`direccion_cliente`) VALUES (?,?,?,?,?,?)";
                    try {

                        ps = myConnection.getConnection().prepareStatement(query);
                        ps.setString(1, nom_cli);
                        ps.setString(2, ape_cli);
                        ps.setString(3, tel_cli);
                        ps.setString(4, correo_cli);
                        ps.setString(5, dni_cli);
                        ps.setString(6, dir_cli);
                      
                        if (ps.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(null, "¡Cliente agregado exitosamente!");

                            LimpiarCamposCliente();
                            MostrarDatosCliente("", "");
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "error " + ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El correo electrónico no es válido.");
                }
            }
        }   
        
    }//GEN-LAST:event_btn_añadir_clienteActionPerformed

    private void btn_borrar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_borrar_clienteActionPerformed
   // TODO add your handling code here:        
        String id = txt_id_cliente.getText();
        String nomCli = txt_nom_cliente.getText();
        PreparedStatement ps;
        String query = "DELETE FROM `cliente` WHERE id_cliente=?";
        try {
            ps = myConnection.getConnection().prepareStatement(query);

            ps.setString(1, id);

            if (ps.executeUpdate() > 0) {

                JOptionPane.showMessageDialog(null, "Cliente: "+ nomCli +" eliminado exitosamente");
                LimpiarCamposCliente();
                MostrarDatosCliente("", "");
            }

        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }

    }//GEN-LAST:event_btn_borrar_clienteActionPerformed

    private void btn_editar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_clienteActionPerformed
       // TODO add your handling code here:
        int id_cli = Integer.parseInt(txt_id_cliente.getText());
        String nom_cli = txt_nom_cliente.getText();
        String ape_cli = txt_apellido_cliente.getText();
        String dni_cli = txt_dni_cliente.getText();
        String tel_cli = txt_telf_cliente.getText();
        String dir_cli = txt_direccion_cliente.getText();
        String correo_cli = txt_correo_cliente.getText();

        if (nom_cli.isEmpty() || ape_cli.isEmpty() || dni_cli.isEmpty() || tel_cli.isEmpty() || dir_cli.isEmpty() || correo_cli.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, no deje ningun dato vacío");
        } else {
           
            if (RevisarCliente(nom_cli, id_cli,ape_cli ) == true) {
                JOptionPane.showMessageDialog(this, "El cliente " + nom_cli + " ya existe. Use otro.");
                LimpiarCamposAlmacen();
            } else {
                PreparedStatement ps;
                String query = "UPDATE `cliente` SET nombre_cliente=?, apellido_cliente=?, telefono_cliente=?, correo_cliente=?, dni_cliente=?, direccion_cliente=? WHERE id_cliente=?";
                try {
                    ps = myConnection.getConnection().prepareStatement(query);

                    ps.setInt(7, id_cli);
                    ps.setString(1, nom_cli);
                    ps.setString(2, ape_cli);
                    ps.setString(3, tel_cli);
                    ps.setString(4, correo_cli);
                    ps.setString(5, dni_cli);
                    ps.setString(6, dir_cli);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Cliente: " + nom_cli +". Modificado correctamente");
                    LimpiarCamposCliente();
                    MostrarDatosCliente("", "");

                } catch (SQLException ex) {
                    Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "error " + ex);
                }
            }
        }
                                                   

    }//GEN-LAST:event_btn_editar_clienteActionPerformed

    private void txt_filtro_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_clienteKeyPressed
         try {

            String B = txt_filtro_cliente.getText();
            int A = Filtro_cli.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosCliente(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_clienteKeyPressed

    private void txt_filtro_clienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_clienteKeyReleased
       try {

            String B = txt_filtro_cliente.getText();
            int A = Filtro_cli.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosCliente(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_clienteKeyReleased

    private void txt_filtro_clienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_clienteKeyTyped
       try {

            String B = txt_filtro_cliente.getText();
            int A = Filtro_cli.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosCliente(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_clienteKeyTyped

    private void tbl_proveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_proveedoresMouseClicked
        // TODO add your handling code here:
        try {
            String id = (String) Proveedor.getValueAt(tbl_proveedores.getSelectedRow(), 0);
            String nom_prov = (String) Proveedor.getValueAt(tbl_proveedores.getSelectedRow(), 1);
            String ape_prov = (String) Proveedor.getValueAt(tbl_proveedores.getSelectedRow(), 2);
            String tel_prov = (String) Proveedor.getValueAt(tbl_proveedores.getSelectedRow(), 3);
            String dir_prov = (String) Proveedor.getValueAt(tbl_proveedores.getSelectedRow(), 4);
            txt_id_proveedor.setText(id);
            txt_nom_proveedor.setText(nom_prov);
            txt_apellido_proveedor.setText(ape_prov);
            txt_tel_proveedor.setText(tel_prov);
            txt_direccion_proveedor.setText(dir_prov);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }//GEN-LAST:event_tbl_proveedoresMouseClicked

    private void btn_añadir_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_añadir_proveedorActionPerformed
        // TODO add your handling code here:
        ResultSet rs;
        int id_prov = Integer.parseInt(txt_id_usu.getText());
        String nom_prov = txt_nom_com.getText();
        String ape_prov = txt_nom_usu.getText();
        String tel_prov = txt_correo.getText();
        String dir_prov = String.valueOf(txt_clave.getPassword());

        if (nom_prov.equals("") || ape_prov.equals("") || tel_prov.equals("") || dir_prov.equals("")) {

            JOptionPane.showMessageDialog(this, "Error, verifique que todos los campos estén llenos");

        } else {
            
                    PreparedStatement ps;
                    String query = "INSERT INTO `proveedor` VALUES (0,?,?,?,?)";
                    try {
                        ps = myConnection.getConnection().prepareStatement(query);

                        ps.setString(1, nom_prov);
                        ps.setString(2, ape_prov);
                        ps.setString(3, tel_prov);
                        ps.setString(4, dir_prov);

                        if (ps.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(this, "Proveedor: " + nom_prov + " " + ape_prov + ". Registrado exitosamente. ");
                            LimpiarCamposProveedor();
                            MostrarDatosProveedor("","");
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "error " + ex);
                    }
        }
    }//GEN-LAST:event_btn_añadir_proveedorActionPerformed

    private void btn_borrar_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_borrar_proveedorActionPerformed
        // TODO add your handling code here:
        String id = txt_id_proveedor.getText();
        String nomProv = txt_nom_proveedor.getText();
        PreparedStatement ps;
        String query = "DELETE FROM `proveedor` WHERE id_proveedor=?";
        try {
            ps = myConnection.getConnection().prepareStatement(query);

            ps.setString(1, id);

            if (ps.executeUpdate() > 0) {

                JOptionPane.showMessageDialog(null, "Proveedor: "+ nomProv +" eliminado exitosamente");
                LimpiarCamposProveedor();
                MostrarDatosProveedor("", "");
            }

        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_btn_borrar_proveedorActionPerformed

    private void btn_editar_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_proveedorActionPerformed
        // TODO add your handling code here:
        String id = txt_id_proveedor.getText();
        String nom_prov = txt_nom_proveedor.getText();
        String ape_prov = txt_apellido_proveedor.getText();
        String tel = txt_tel_proveedor.getText();
        String direccion = txt_direccion_proveedor.getText();
        

        if (id.isEmpty() || nom_prov.isEmpty() || ape_prov.isEmpty() || tel.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error, verifique que ningún campo esté vacío.");
        } else {
            int id_ = Integer.parseInt(id);
            if (RevisarProveedor(nom_prov, ape_prov, id_) == true) {
                JOptionPane.showMessageDialog(null, "El nombre de proveedor: " + nom_prov + ". está registrado, use otro diferente.");
            } else if(RevisarProveedor(nom_prov, ape_prov, id_) == true) {
                JOptionPane.showMessageDialog(null, "El apellido de proveedor: " + ape_prov + ". está registrado, use otro diferente.");
            } else{

                PreparedStatement ps;
                String query = "UPDATE proveedor SET nombre_proveedor=?, apellido_proveedor=?, telefono=?, direccion=? WHERE id_proveedor=?";
                try {
                    ps = myConnection.getConnection().prepareStatement(query);

                    ps.setString(5, id);
                    ps.setString(1, nom_prov);
                    ps.setString(2, ape_prov);
                    ps.setString(3, tel);
                    ps.setString(4, direccion);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Proveedor: " + nom_prov + " " + ape_prov + ". modificado");
                    LimpiarCamposProveedor();
                    MostrarDatosProveedor("", "");

                } catch (SQLException ex) {
                    Logger.getLogger(frm_cliente.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "error " + ex);
                }
            }
        }
    }//GEN-LAST:event_btn_editar_proveedorActionPerformed

    private void txt_filtro_proveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_proveedorKeyPressed
        // TODO add your handling code here:
        try {

            String B = txt_filtro_proveedor.getText();
            int A = Filtro_prov.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosProveedor(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_proveedorKeyPressed

    private void txt_filtro_proveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_proveedorKeyReleased
        // TODO add your handling code here:
        try {
            String B = txt_filtro_proveedor.getText();
            int A = Filtro_prov.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosUsuario(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_proveedorKeyReleased

    private void txt_filtro_proveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_proveedorKeyTyped
        // TODO add your handling code here:
        try {

            String B = txt_filtro_proveedor.getText();
            int A = Filtro_prov.getSelectedIndex();
            String A1 = String.valueOf(A);
            MostrarDatosUsuario(B, A1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error " + ex);
        }
    }//GEN-LAST:event_txt_filtro_proveedorKeyTyped

    private void rep_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rep_fechaActionPerformed
        try {                                          
            // TODO add your handling code here:
            String fecha_filtro = rep_fecha.getText();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = formatter.parse(fecha_filtro);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);
            
            try {
                
                int A = month+1;
                String A1 = String.valueOf(A);
                System.out.println(A1);
                MostrarDatosFactFiltro(A1);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "error " + ex);
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE,null, ex);
        }
        
    }//GEN-LAST:event_rep_fechaActionPerformed

    void guardarFactura() {
        int id_emp = id_empleado;
        int id_cli = id_cliente;

        String ncf_fact = txt_ncf_fact.getText().substring(4);
        String comprobante = "B01";
        String ncf_secuencia = comprobante + ncf_fact;
        String DNI = txt_dni_usu_fact.getText();

        float sub_total = Float.valueOf(String.valueOf(lbl_subtotal.getText().substring(1)));
        float itbis = Float.parseFloat(lbl_itbis.getText().substring(1));
        float t_total = Float.parseFloat(lbl_total_.getText().substring(1));

        Factu.setIdEmp(id_emp);
        Factu.setIdCliente(id_cli);
        Factu.setNCF(ncf_secuencia);
        Factu.setDNI(DNI);
        Factu.setSubtotal(sub_total);
        Factu.setItbis(itbis);
        Factu.setTotal(t_total);

        gFactu.GuardarFactura(Factu);

    }

    void guardarDetalleFactura() {

        int id_fact = Integer.parseInt(txt_id_factura.getText());
        for (int i = 0; i < tbl_detalle.getRowCount(); i++) {

            int id_p = Integer.parseInt(tbl_detalle.getValueAt(i, 1).toString());
            int cantidad = Integer.parseInt(tbl_detalle.getValueAt(i, 2).toString());
            float importe = Float.parseFloat(tbl_detalle.getValueAt(i, 3).toString());

            dv.setIdFact(id_fact);
            dv.setIdProd(id_p);
            dv.setCantidad_vendida(cantidad);
            dv.setImporte(importe);

            gFactu.GuardarDetalleVenta(dv);

        }

    }

    void actualizarStock() {
        for (int i = 0; i < tbl_detalle.getRowCount(); i++) {
            c_producto pr = new c_producto();
            int id_p = Integer.parseInt(tbl_detalle.getValueAt(i, 1).toString());
            int cant = Integer.parseInt(tbl_detalle.getValueAt(i, 2).toString());

            pr = modpro.ListarId(id_p);
            int sa = pr.getCant_disponible() - cant;
        }

    }

    final void SetFacturaID() {
        String old_id_fact = txt_id_factura.getText();
        int sum_id_fact = Integer.parseInt(old_id_fact) + 1;
        String sum_string = String.valueOf(sum_id_fact);
        txt_id_factura.setText(sum_string);
    }

    final void generarIdFact() {
        String serie = SetIdFact();
        if (serie == null) {

            txt_id_factura.setText("0");

        } else {
            int incrementar = Integer.parseInt(serie);

            txt_id_factura.setText(String.valueOf(incrementar));
        }

    }

    public String SetIdFact() {
        String serie = "";
        String sql = "SELECT MAX(id_factura) FROM factura";

        try {

            myConnection cc = new myConnection();
            Connection con = myConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                serie = rs.getString(1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        return serie;
    }

    // GENERAR EL NCF DE LA FACTURA
    public String SetNCF_Factura() {
        String NCF = "";
        String sql = "SELECT MAX(NCF) FROM factura";

        try {
            myConnection cc = new myConnection();
            Connection con = myConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                NCF = rs.getString(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(frm_menu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return NCF;
    }

    final void generarNCF_Fact() {
        String NCF = SetNCF_Factura();
        if (NCF == null) {

            String serie_comprobante = "B01";
            int secuencia = 00000000;
            txt_ncf_fact.setText(serie_comprobante + secuencia);

        } else {
            txt_ncf_fact.setText(String.valueOf(NCF));
        }

    }

    final void SetNCF_ID() {
        String old_ncf_fact = txt_ncf_fact.getText().substring(3);
        int sum_ncf_fact = Integer.parseInt(old_ncf_fact) + 1;
        String sum_string = String.valueOf(sum_ncf_fact);
        String secuencia = "00000000";
        int sum_uno = sum_string.length();
        String ncf_set = secuencia.substring(sum_uno);
        String serie_comprobante = "B01-";

        txt_ncf_fact.setText(serie_comprobante + ncf_set + sum_string);
    }
    


    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new frm_menu().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Filtro_cli;
    private javax.swing.JComboBox<String> Filtro_emp;
    private javax.swing.JComboBox<String> Filtro_prod;
    private javax.swing.JComboBox<String> Filtro_prov;
    private javax.swing.JComboBox<String> Filtro_usu;
    private javax.swing.JButton btn_agregar_empleado;
    private javax.swing.JButton btn_añadir_cliente;
    private javax.swing.JButton btn_añadir_prod;
    private javax.swing.JButton btn_añadir_prod_fact;
    private javax.swing.JButton btn_añadir_proveedor;
    private javax.swing.JButton btn_añadir_usuario;
    private javax.swing.JButton btn_borrar_cliente;
    private javax.swing.JButton btn_borrar_empleado;
    private javax.swing.JButton btn_borrar_prod;
    private javax.swing.JButton btn_borrar_proveedor;
    private javax.swing.JButton btn_borrar_usuario;
    private javax.swing.JButton btn_editar_cliente;
    private javax.swing.JButton btn_editar_prod;
    private javax.swing.JButton btn_editar_proveedor;
    private javax.swing.JButton btn_editar_usuario;
    private javax.swing.JButton btn_eliminar_prod_fact;
    private javax.swing.JButton btn_fin_fact;
    private javax.swing.JLabel btn_log_out;
    private javax.swing.JButton btn_modificar_empleado;
    private javax.swing.JButton btn_rep_facts;
    private javax.swing.JButton btn_reporte_empleado;
    private javax.swing.JButton btn_reporte_empleado1;
    public static javax.swing.JComboBox<String> cbb_afiliacion;
    private static javax.swing.JComboBox<c_cargo> cbb_cargo_empleado;
    private static javax.swing.JComboBox<c_categoria> cbb_categoria_producto;
    public static javax.swing.JComboBox<c_rol> cbb_rol_cliente;
    public static javax.swing.JComboBox<c_rol> cbb_rol_empleado;
    private javax.swing.JLabel img_cliente;
    private javax.swing.JLabel img_empleado;
    private javax.swing.JLabel img_factura;
    private javax.swing.JLabel img_factura1;
    private javax.swing.JLabel img_logo;
    private javax.swing.JLabel img_producto;
    private javax.swing.JLabel img_proveedor;
    private javax.swing.JLabel img_usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_afiliacion_client;
    private javax.swing.JLabel lbl_afiliacion_usuario;
    private javax.swing.JLabel lbl_afiliacion_usuario1;
    private javax.swing.JLabel lbl_afiliacion_usuario2;
    private javax.swing.JLabel lbl_afiliacion_usuario3;
    private javax.swing.JLabel lbl_apellido;
    private javax.swing.JLabel lbl_apellido_cliente;
    private javax.swing.JLabel lbl_apellido_cliente1;
    private javax.swing.JLabel lbl_apellido_proveedor;
    private javax.swing.JLabel lbl_cant;
    private javax.swing.JLabel lbl_cant_prod;
    private javax.swing.JLabel lbl_cargo;
    private javax.swing.JLabel lbl_cargo1;
    private javax.swing.JLabel lbl_categoria_prod;
    private javax.swing.JLabel lbl_cif;
    private javax.swing.JLabel lbl_clave;
    private javax.swing.JLabel lbl_clave_usuario;
    private javax.swing.JLabel lbl_correo_usuario;
    private javax.swing.JLabel lbl_correo_usuario2;
    private javax.swing.JLabel lbl_correo_usuario3;
    private javax.swing.JLabel lbl_direccion;
    private javax.swing.JLabel lbl_direccion_proveedor;
    private javax.swing.JLabel lbl_dni;
    private javax.swing.JLabel lbl_dni_fact;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_emp_fact;
    private javax.swing.JLabel lbl_empleado;
    private javax.swing.JLabel lbl_factura;
    private javax.swing.JLabel lbl_factura1;
    private javax.swing.JLabel lbl_filtro_prov;
    private javax.swing.JLabel lbl_id_emp;
    private javax.swing.JLabel lbl_id_prod;
    private javax.swing.JLabel lbl_id_prod_fact;
    private javax.swing.JLabel lbl_id_proveedor;
    private javax.swing.JLabel lbl_id_usuario;
    private javax.swing.JLabel lbl_id_usuario1;
    private javax.swing.JLabel lbl_itbis;
    private javax.swing.JLabel lbl_nom_com;
    private javax.swing.JLabel lbl_nom_com1;
    private javax.swing.JLabel lbl_nom_prod;
    private javax.swing.JLabel lbl_nom_proveedor;
    private javax.swing.JLabel lbl_nom_usu;
    private javax.swing.JLabel lbl_nom_usu1;
    private javax.swing.JLabel lbl_nombre1;
    private javax.swing.JLabel lbl_precio;
    private javax.swing.JLabel lbl_precio_prod;
    private javax.swing.JLabel lbl_precio_prod1;
    private javax.swing.JLabel lbl_pres_prod;
    private javax.swing.JLabel lbl_producto_fact;
    private javax.swing.JLabel lbl_productos;
    private javax.swing.JLabel lbl_proveedor_titulo;
    private javax.swing.JLabel lbl_puntos_prod;
    private javax.swing.JLabel lbl_rnc;
    private javax.swing.JLabel lbl_rol_usuario;
    private javax.swing.JLabel lbl_subtotal;
    private javax.swing.JLabel lbl_telefono1;
    private javax.swing.JLabel lbl_telefono_proveedor;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_total2;
    private javax.swing.JLabel lbl_total4;
    private javax.swing.JLabel lbl_total_;
    private javax.swing.JLabel lbl_user_fact;
    private javax.swing.JLabel lbl_usuario_titulo;
    private javax.swing.JLabel lbl_usuario_titulo1;
    private javax.swing.JPanel pnl_empleado;
    private javax.swing.JPanel pnl_proveedor;
    private javax.swing.JPanel pnl_usuario;
    private javax.swing.JPanel pnl_usuario1;
    private javax.swing.JFormattedTextField rep_fecha;
    private javax.swing.JPanel t_almacen;
    private javax.swing.JPanel t_cliente;
    private javax.swing.JPanel t_empleado;
    private javax.swing.JPanel t_factura;
    private javax.swing.JPanel t_proveedor;
    private javax.swing.JPanel t_rep_facturas;
    private javax.swing.JPanel t_reporte;
    private javax.swing.JPanel t_usuario;
    private javax.swing.JPanel t_venta;
    private javax.swing.JTable tbl_almacen;
    private javax.swing.JTable tbl_cliente;
    private javax.swing.JTable tbl_detalle;
    private javax.swing.JTable tbl_empleado;
    private javax.swing.JTable tbl_factFiltro;
    private javax.swing.JTable tbl_proveedores;
    private javax.swing.JTable tbl_repFacturas;
    private javax.swing.JTable tbl_usuario;
    private javax.swing.JTextField txt_apellido_cliente;
    private javax.swing.JTextField txt_apellido_emp;
    private javax.swing.JTextField txt_apellido_proveedor;
    private javax.swing.JTextField txt_cantidad_prod;
    private javax.swing.JPasswordField txt_clave;
    private javax.swing.JPasswordField txt_clave_emp;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_correo_cliente;
    private javax.swing.JTextField txt_correo_emp;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_direccion_cliente;
    private javax.swing.JTextField txt_direccion_proveedor;
    private javax.swing.JTextField txt_dni;
    private javax.swing.JTextField txt_dni_cliente;
    public static javax.swing.JTextField txt_dni_usu_fact;
    private javax.swing.JTextField txt_filtro_cliente;
    private javax.swing.JTextField txt_filtro_emp;
    private javax.swing.JTextField txt_filtro_prod;
    private javax.swing.JTextField txt_filtro_proveedor;
    private javax.swing.JTextField txt_filtro_usu;
    private javax.swing.JTextField txt_id_cliente;
    private javax.swing.JTextField txt_id_emp;
    private javax.swing.JTextField txt_id_factura;
    private javax.swing.JTextField txt_id_prod;
    public static javax.swing.JTextField txt_id_prod_fact;
    private javax.swing.JTextField txt_id_proveedor;
    private javax.swing.JTextField txt_id_usu;
    private javax.swing.JTextField txt_ncf_fact;
    private javax.swing.JTextField txt_nom_cliente;
    private javax.swing.JTextField txt_nom_com;
    public static javax.swing.JTextField txt_nom_emp_fact;
    private javax.swing.JTextField txt_nom_prod;
    private javax.swing.JTextField txt_nom_proveedor;
    public static javax.swing.JTextField txt_nom_user_fact;
    private javax.swing.JTextField txt_nom_usu;
    private javax.swing.JTextField txt_nombre_emp;
    private javax.swing.JTextField txt_precio_compra;
    public static javax.swing.JFormattedTextField txt_precio_prod_fact;
    private javax.swing.JTextField txt_precio_venta_prod;
    private javax.swing.JTextField txt_pres_prod;
    private javax.swing.JSpinner txt_producto_cant;
    public static javax.swing.JTextField txt_producto_fact;
    private javax.swing.JTextField txt_puntos_prod;
    private javax.swing.JTextField txt_tel_proveedor;
    private javax.swing.JTextField txt_telefono;
    private javax.swing.JTextField txt_telf_cliente;
    // End of variables declaration//GEN-END:variables

}
