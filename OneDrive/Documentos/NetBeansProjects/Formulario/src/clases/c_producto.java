/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author 18432
 */
public class c_producto {
    int id_p;
    String nombre_p;
    String presentacion;
    int cant_disponible;
    int id_categoira;
    float unidad_precio;
    int puntos_producto;
    int descuento;
    int estado;

    public c_producto() {
    }

    public c_producto(int id_p, String nombre_p, String presentacion, int cant_disponible, int id_categoira, float unidad_precio, int puntos_producto, int descuento, int estado) {
        this.id_p = id_p;
        this.nombre_p = nombre_p;
        this.presentacion = presentacion;
        this.cant_disponible = cant_disponible;
        this.id_categoira = id_categoira;
        this.unidad_precio = unidad_precio;
        this.puntos_producto = puntos_producto;
        this.descuento = descuento;
        this.estado = estado;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }

    public String getNombre_p() {
        return nombre_p;
    }

    public void setNombre_p(String nombre_p) {
        this.nombre_p = nombre_p;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public int getCant_disponible() {
        return cant_disponible;
    }

    public void setCant_disponible(int cant_disponible) {
        this.cant_disponible = cant_disponible;
    }

    public int getId_categoira() {
        return id_categoira;
    }

    public void setId_categoira(int id_categoira) {
        this.id_categoira = id_categoira;
    }

    public float getUnidad_precio() {
        return unidad_precio;
    }

    public void setUnidad_precio(float unidad_precio) {
        this.unidad_precio = unidad_precio;
    }

    public int getPuntos_producto() {
        return puntos_producto;
    }

    public void setPuntos_producto(int puntos_producto) {
        this.puntos_producto = puntos_producto;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}
