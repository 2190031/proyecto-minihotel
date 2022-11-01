/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;


public class c_usuario {
    int id_e;
    String nombre_e;
    String apellido_e;
    int DNI_e;
    String telefono;
    String direccion_e;
    int id_cargo;
    int id_rol;
    String correo_e;
    String clave_e;
    String last_session;
    String nombre_rol;


    public c_usuario(int id_e, String nombre_e, String apellido_e, int DNI_e, String telefono, String direccion_e, int id_cargo, int id_rol, String correo_e, String clave_e, String last_session) {
        this.id_e = id_e;
        this.nombre_e = nombre_e;
        this.apellido_e = apellido_e;
        this.DNI_e = DNI_e;
        this.telefono = telefono;
        this.direccion_e = direccion_e;
        this.id_cargo = id_cargo;
        this.id_rol = id_rol;
        this.correo_e = correo_e;
        this.clave_e = clave_e;
        this.last_session = last_session;
    }

    public c_usuario() {
       
    }

    public int getId_e() {
        return id_e;
    }

    public void setId_e(int id_e) {
        this.id_e = id_e;
    }

    public String getNombre_e() {
        return nombre_e;
    }

    public void setNombre_e(String nombre_e) {
        this.nombre_e = nombre_e;
    }

    public String getApellido_e() {
        return apellido_e;
    }

    public void setApellido_e(String apellido_e) {
        this.apellido_e = apellido_e;
    }

    public int getDNI_e() {
        return DNI_e;
    }

    public void setDNI_e(int DNI_e) {
        this.DNI_e = DNI_e;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion_e() {
        return direccion_e;
    }

    public void setDireccion_e(String direccion_e) {
        this.direccion_e = direccion_e;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getCorreo_e() {
        return correo_e;
    }

    public void setCorreo_e(String correo_e) {
        this.correo_e = correo_e;
    }

    public String getClave_e() {
        return clave_e;
    }

    public void setClave_e(String clave_e) {
        this.clave_e = clave_e;
    }

    public String getLast_session() {
        return last_session;
    }

    public void setLast_session(String last_session) {
        this.last_session = last_session;
    }
    
        public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }

    
}
