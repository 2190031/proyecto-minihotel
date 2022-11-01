package modelo;

import Connection.myConnection;
import clases.c_usuario;
import forms.frm_menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class md_usuario {

    public boolean login(c_usuario usr) {

        try {

            PreparedStatement ps;
            ResultSet rs;
            Connection con = myConnection.getConnection();

            String sql = "SELECT E.Id_empleado, E.nombre_empleado, E.apellido_empleado, E.dni, E.telefono, E.direccion_empleado, E.id_cargo, E.id_rol, E.correo_emp, E.clave_emp, E.last_session, R.descripcion FROM empleado as E INNER JOIN rol AS r ON E.id_rol = R.id_rol WHERE correo_emp = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getCorreo_e());
            rs = ps.executeQuery();

            if (rs.next()) {
                if (usr.getClave_e().equals(rs.getString(10))) {

                    String SQL = "UPDATE empleado SET last_session = ? WHERE Id_empleado = ?";
                    PreparedStatement Ps;
                    Ps = myConnection.getConnection().prepareStatement(SQL);
                    Ps.setString(1, usr.getLast_session());
                    Ps.setInt(2, rs.getInt(1));
                    Ps.execute();

                    usr.setId_e(rs.getInt(1));
                    usr.setNombre_e(rs.getString(2));
                    usr.setApellido_e(rs.getString(3));
                    usr.setId_rol(rs.getInt(8));
                    usr.setNombre_rol(rs.getString(12));
                 
                   frm_menu.id_empleado = usr.getId_e();
                    
                    

                    return true;

                } else {
                    return false;
                }
            }
            return false;

        } catch (SQLException ex) {
            Logger.getLogger(md_usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean esEmail(String correo) {

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+´)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correo);
        return mather.find();

    }

}
